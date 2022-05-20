package iob.service.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.MongoClients;

import MongoDBConfig.MongoDBConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.bounderies.UserBoundary;
import iob.data.ActivityEntity;
import iob.data.ActivityType;
import iob.data.InstanceEntity;
import iob.data.InstanceType;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.exceptions.NoPermissionException;
import iob.exceptions.NotFoundException;
import iob.logic.ActivitiesServiceEnhanced;
import iob.logic.ActivityConverter;
import iob.logic.IdConverter;
import iob.logic.InstanceConverter;
import iob.logic.UserConverter;

@Service
public class ActivitiesServiceMongo implements ActivitiesServiceEnhanced {

	private final String ACTIVITY_PENDING_USER_MAP_KEY = "PendingUser";
	private final String ACTIVITY_USER_TO_DELETE_MAP_KEY = "UserToDelete";
	private final String INSTANCE_MEMBERS_MAP_KEY = "Members";
	private final String INSTANCE_PENDING_MEMBERS_MAP_KEY = "PendingMembers";
	private final String INSTANCE_GROUPS_MAP_KEY = "Groups";

	private ActivityDao activityDao;
	private UserDao userDao;
	private InstanceDao instanceDao;
	private ActivityConverter activityConverter;
	private InstanceConverter instanceConverter;
	private UserConverter userConverter;
	private IdConverter idConverter;
	private String domain;

	@Autowired
	public ActivitiesServiceMongo(ActivityConverter activityConverter, ActivityDao activityDao, UserDao userDao,
			InstanceDao instanceDao, IdConverter idConverter,InstanceConverter instanceConverter,UserConverter userConverter) {
		this.activityConverter = activityConverter;
		this.userConverter = userConverter;
		this.activityDao = activityDao;
		this.userDao = userDao;
		this.instanceDao = instanceDao;
		this.idConverter = idConverter;
		this.instanceConverter = instanceConverter;
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public Object invokeActivity(ActivityBoundary activity) {
		validateActivityBoundary(activity);

		UserEntity userEntity = getUserEntity(activity.getInvokedBy().getUserId().getDomain(),
				activity.getInvokedBy().getUserId().getEmail());

		InstanceEntity instanceEntity = getInstanceEntity(activity.getInstance().getInstanceId().getDomain(),
				activity.getInstance().getInstanceId().getId());

		validateUserAndInstance(userEntity, instanceEntity);

		Object rv = executeActivity(activity, userEntity, instanceEntity);

		GeneralId activityId = new GeneralId();
		activityId.setDomain(this.domain);
		activityId.setId(UUID.randomUUID().toString());

		activity.setActivityId(activityId);
		activity.setCreatedTimestamp(new Date());

		// convert to entity
		ActivityEntity entity = this.activityConverter.toEntity(activity);

		// save
		activityDao.save(entity);

		// return boundary
		return rv;
	}

	// Only Player user can invoke activity on an active instance
	private void validateUserAndInstance(UserEntity userEntity, InstanceEntity instanceEntity) {
		if (userEntity.getRole() != UserRole.PLAYER)
			throw new NoPermissionException();

		if (!instanceEntity.getActive())
			throw new NotFoundException("instance with id " + instanceEntity.getId() + " not found");
	}

	private Object executeActivity(ActivityBoundary activity, UserEntity userEntity, InstanceEntity instanceEntity) {

		ActivityType activityType = ActivityType.getType(activity.getType());
		switch (activityType) {
		case JOIN_GROUP:
			InstanceEntity joiningMember = executeJoinGroup(activity, instanceEntity);
			return instanceConverter.toBoundary(joiningMember);

		case DELETE_GROUP:
			InstanceEntity deletedGroup = executeDeleteGroup(activity, instanceEntity, userEntity);
			return instanceConverter.toBoundary(deletedGroup);

		case ACCEPT_NEW_MEMBER:
			InstanceEntity pendingMember = executeAcceptNewMember(activity, instanceEntity, userEntity);
			return instanceConverter.toBoundary(pendingMember);

		case DELETE_MEMBER:
			InstanceEntity deletedMember =  executeDeleteMember(activity, instanceEntity, userEntity);
			return instanceConverter.toBoundary(deletedMember);

		case EXIT_GROUP:
			InstanceEntity leavingMember = executeExitGroup(activity, instanceEntity);
			return instanceConverter.toBoundary(leavingMember);
			
		case GET_GROUPS_OF_USER:
			return executeGetGroupsOfUser(activity, instanceEntity, userEntity);
			
		
		case GET_USERS_IN_GROUP:
			return executeGetUsersInGroup(activity, instanceEntity, userEntity);
			
			
		case OTHER:
			// No implementation for OTHER type for now, just save the activity to data.
			return activity;
		
		}
		return null;

	}

	private List<InstanceBoundary> executeGetUsersInGroup(ActivityBoundary activity, InstanceEntity instanceEntity, UserEntity userEntity) {
		Optional<InstanceEntity> optional = instanceDao.findById(instanceEntity.getId());
		if (optional.isPresent()) {
			InstanceEntity userInstance = optional.get();
			Map<String, Object> attr =  userInstance.getInstanceAttributes();

			ArrayList<String> users = (ArrayList<String>) attr.get(INSTANCE_MEMBERS_MAP_KEY);
			if(users == null ) return new ArrayList<InstanceBoundary>();
			
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(MongoDBConfig.class);
			ctx.refresh();
			MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);
			
			
			List<Criteria> criteria = new ArrayList<>();
			Query query = new Query();
			criteria.add(Criteria.where("createdByUserId").in(users));
			criteria.add(Criteria.where("type").is("User"));
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			ArrayList<InstanceEntity> allUsersInGroup = (ArrayList<InstanceEntity>) mongoOps.find(query, InstanceEntity.class);
			
			Stream<InstanceEntity> stream = StreamSupport.stream(allUsersInGroup.spliterator(), false);
			return stream.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
			
		} else {
			throw new NotFoundException("Cannot find user instance with id: " + userEntity.getId());
		}
		
	}

	private List<InstanceBoundary> executeGetGroupsOfUser(ActivityBoundary activity, InstanceEntity instanceEntity, UserEntity userEntity) {
		
		Optional<InstanceEntity> optional = instanceDao.findById(instanceEntity.getId());
		if (optional.isPresent()) {
			InstanceEntity userInstance = optional.get();
			Map<String, Object> attr =  userInstance.getInstanceAttributes();
			
			ArrayList<String> groups = (ArrayList<String>) attr.get(INSTANCE_GROUPS_MAP_KEY);
			
			if(groups == null ) return new ArrayList<InstanceBoundary>();
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(MongoDBConfig.class);
			ctx.refresh();
			MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);
			List<Criteria> criteria = new ArrayList<>();
			Query query = new Query();
			criteria.add(Criteria.where("id").in(groups));
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			ArrayList<InstanceEntity> allGroups = (ArrayList<InstanceEntity>) mongoOps.find(query, InstanceEntity.class);
			Stream<InstanceEntity> stream = StreamSupport.stream(allGroups.spliterator(), false);
			return stream.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
			
		} else {
			throw new NotFoundException("Cannot find user instance with id: " + userEntity.getId());
		}
		
		
		
		
	}

	private InstanceEntity executeDeleteGroup(ActivityBoundary deleteGroupActivity, InstanceEntity groupInstance,
			UserEntity groupManagerUser) {

		// check if the invoked instance is from GROUP type
		if (!isGroupInstance(groupInstance))
			throw new RuntimeException("Invoked Instance is not GROUP type");

		if (!isCreatorOfTheGroup(groupInstance, groupManagerUser))
			throw new NoPermissionException();

		// get the group instance attributes
		Map<String, Object> groupAttributes = groupInstance.getInstanceAttributes();

		// get the members of the group
		List<String> members = (List<String>) groupAttributes.get(INSTANCE_MEMBERS_MAP_KEY);
		for (String member : members) {
			deleteGroupFromMemberGroupsList(member, groupInstance.getId());
		}
		
		groupInstance.setActive(false);
		instanceDao.save(groupInstance);
		return groupInstance;
	}

	private void deleteGroupFromMemberGroupsList(String userId, String groupId) {
		InstanceEntity member = getUserInstanceFromUserId(userId);
		if (member == null)
			throw new NotFoundException("instance connected to user id " + userId + " was not found");

		// get the instance attributes of the the user to the group for him
		Map<String, Object> memberAttributes = member.getInstanceAttributes();
		// get the groups of the member
		List<String> groups = (List<String>) memberAttributes.get(INSTANCE_GROUPS_MAP_KEY);
		
		groups.remove(groupId);
		instanceDao.save(member);
		
	}

	private InstanceEntity executeJoinGroup(ActivityBoundary joinGroupActivity, InstanceEntity groupInstance) {
		// check if the invoked instance is from GROUP type
		if (!isGroupInstance(groupInstance)) {
			throw new RuntimeException("Invoked Instance is not GROUP type");
		}

		// get the id of the user that wants to join group
		String joiningUserId = idConverter.getUserEntityIdFromDomainAndEmail(
				joinGroupActivity.getInvokedBy().getUserId().getDomain(),
				joinGroupActivity.getInvokedBy().getUserId().getEmail());

		// get the instance entity that connected to that user
		InstanceEntity memberToAdd = getUserInstanceFromUserId(joiningUserId);
		if (memberToAdd == null)
			throw new NotFoundException("instance connected to user id " + joiningUserId + " was not found");

		// get the group instance attributes
		Map<String, Object> groupAttributes = groupInstance.getInstanceAttributes();

		// get the pending members of the group
		List<String> pendingMembers = (List<String>) groupAttributes.get(INSTANCE_PENDING_MEMBERS_MAP_KEY);

		// add the user to the pending members list
		pendingMembers.add(joiningUserId);

		// update the group in the database
		instanceDao.save(groupInstance);
		return memberToAdd;	
	}

	private InstanceEntity executeExitGroup(ActivityBoundary exitGroupActivity, InstanceEntity groupInstance) {
		// check if the invoked instance is from GROUP type
		if (!isGroupInstance(groupInstance)) {
			throw new RuntimeException("Invoked Instance is not GROUP type");
		}

		// get the id of the user that wants to exit group
		String exitingUserId = idConverter.getUserEntityIdFromDomainAndEmail(
				exitGroupActivity.getInvokedBy().getUserId().getDomain(),
				exitGroupActivity.getInvokedBy().getUserId().getEmail());

		// get the instance entity that connected to that user
		InstanceEntity memberToDelete = getUserInstanceFromUserId(exitingUserId);
		if (memberToDelete == null)
			throw new NotFoundException("instance connected to user id " + exitingUserId + " was not found");

		// get the group instance attributes
		Map<String, Object> groupAttributes = groupInstance.getInstanceAttributes();
		// get the instance attributes of the the user to delete from group
		Map<String, Object> memberAttributes = memberToDelete.getInstanceAttributes();

		// get the members of the group
		List<String> members = (List<String>) groupAttributes.get(INSTANCE_MEMBERS_MAP_KEY);
		// get the groups of the member thats wants to leave the group
		List<String> groups = (List<String>) memberAttributes.get(INSTANCE_GROUPS_MAP_KEY);

		// remove the user from the members list
		members.remove(exitingUserId);

		// remove the group from the list of groups that the user have
		groups.remove(groupInstance.getId());

		// update the group and the user in the database
		instanceDao.save(memberToDelete);
		instanceDao.save(groupInstance);
		return groupInstance;

	}

	private InstanceEntity executeDeleteMember(ActivityBoundary deleteMemberActivity, InstanceEntity groupInstance,
			UserEntity groupManagerUser) {

		// check if the invoked instance is from GROUP type
		if (!isGroupInstance(groupInstance))
			throw new RuntimeException("Invoked Instance is not GROUP type");

		if (!isCreatorOfTheGroup(groupInstance, groupManagerUser))
			throw new NoPermissionException();

		// get the id of the user that need to be deleted
		String userIdToDelete = (String) deleteMemberActivity.getActivityAttributes()
				.get(ACTIVITY_USER_TO_DELETE_MAP_KEY);

		// get the instance entity that connected to that user
		InstanceEntity memberToDelete = getUserInstanceFromUserId(userIdToDelete);
		if (memberToDelete == null)
			throw new NotFoundException("instance connected to user id " + userIdToDelete + " was not found");

		// get the group instance attributes
		Map<String, Object> groupAttributes = groupInstance.getInstanceAttributes();
		// get the instance attributes of the the user to delete from group
		Map<String, Object> memberAttributes = memberToDelete.getInstanceAttributes();

		// get the members of the group
		List<String> members = (List<String>) groupAttributes.get(INSTANCE_MEMBERS_MAP_KEY);
		// get the groups of the member thats deleted from the group
		List<String> groups = (List<String>) memberAttributes.get(INSTANCE_GROUPS_MAP_KEY);

		// remove the user from the members list
		members.remove(userIdToDelete);

		// remove the group from the list of groups that the user have
		groups.remove(groupInstance.getId());

		// update the group and the user in the database
		instanceDao.save(memberToDelete);
		instanceDao.save(groupInstance);
		return memberToDelete;
	}

	private boolean isCreatorOfTheGroup(InstanceEntity groupInstance, UserEntity groupManagerUser) {

		return groupInstance.getCreatedByUserId().equals(groupManagerUser.getId());
	}

	private InstanceEntity executeAcceptNewMember(ActivityBoundary acceptNewMemberActivity, InstanceEntity groupInstance,
			UserEntity groupManagerUser) {

		// check if the invoked instance is from GROUP type
		if (!isGroupInstance(groupInstance)) {
			throw new RuntimeException("Invoked Instance is not GROUP type");
		}

		if (!isCreatorOfTheGroup(groupInstance, groupManagerUser))
			throw new NoPermissionException();

		// get the id of the user that wants to join the group
		String pendingUserId = (String) acceptNewMemberActivity.getActivityAttributes()
				.get(ACTIVITY_PENDING_USER_MAP_KEY);

		// get the instance entity that connected to that user
		InstanceEntity pendingMember = getUserInstanceFromUserId(pendingUserId);
		if (pendingMember == null)
			throw new NotFoundException("instance connected to user id " + pendingUserId + " was not found");

		// get the group instance attributes
		Map<String, Object> groupAttributes = groupInstance.getInstanceAttributes();
		// get the instance attributes of the the user instance that wants to join the
		// group
		Map<String, Object> memberAttributes = pendingMember.getInstanceAttributes();

		// get the members of the group
		List<String> members = (List<String>) groupAttributes.get(INSTANCE_MEMBERS_MAP_KEY);
		// get the pending members of the group
		List<String> pendingMembers = (List<String>) groupAttributes.get(INSTANCE_PENDING_MEMBERS_MAP_KEY);
		// get the groups of the member thats wants to join the group
		List<String> groups = (List<String>) memberAttributes.get(INSTANCE_GROUPS_MAP_KEY);

		// remove the user from the pending members list
		pendingMembers.remove(pendingUserId);
		// add the user to the current members list
		members.add(pendingUserId);
		// add the group to the list of groups that the user have
		groups.add(groupInstance.getId());

		// update the group and the user in the database
		instanceDao.save(pendingMember);
		instanceDao.save(groupInstance);
		return pendingMember;

	}

	private InstanceEntity getUserInstanceFromUserId(String userId) {
		List<InstanceEntity> instances = getInstancesByCreatedUserId(userId);

		instances = instances.stream()
				.filter(instance -> instance.getType().equalsIgnoreCase(InstanceType.USER.toString()))
				.collect(Collectors.toList());
		return instances.size() == 0 ? null : instances.get(0);
	}

	private boolean isGroupInstance(InstanceEntity instanceEntity) {

		return instanceEntity.getType().equalsIgnoreCase(InstanceType.GROUP.toString());
	}

	private InstanceEntity getInstanceEntity(String instanceDomain, String instanceId) {
		String id = idConverter.getEntityGeneralIdFromDomainAndId(instanceDomain, instanceId);
		Optional<InstanceEntity> optional = instanceDao.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new NotFoundException("Cannot find instance with id: " + id);
		}
	}

	@Override
	@Deprecated
	public List<ActivityBoundary> getAllActivities() {
//		Iterable<ActivityEntity> iterable = this.activityDao
//				.findAll();
//		Stream<ActivityEntity> stream = StreamSupport.stream(iterable.spliterator(), false);
//		return stream.map(activityConverter::toBoundary).collect(Collectors.toList());
		throw new RuntimeException("Deprecated method");
	}

	@Override
	public List<ActivityBoundary> getAllActivities(String userDomain, String userEmail, int size, int page) {

		UserEntity userEntity = getUserEntity(userDomain, userEmail);

		if (userEntity.getRole() != UserRole.ADMIN)
			throw new NoPermissionException();

		return this.activityDao.findAll(PageRequest.of(page, size, Direction.DESC, "role")).getContent().stream()
				.map(this.activityConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Deprecated
	public void deleteAllAcitivities() {
		// this.activityDao.deleteAll();
		throw new RuntimeException("deprecated method");
	}

	@Override
	public void deleteAllAcitivities(String domain, String email) {
		UserEntity userEntity = getUserEntity(domain, email);

		if (userIsAdmin(userEntity)) {
			this.activityDao.deleteAll();
		} else {
			throw new NoPermissionException(
					"user with id : " + userEntity.getId() + " has No permission to delete all activities");
		}

	}

	private boolean userIsAdmin(UserEntity userEntity) {

		return userEntity.getRole() == UserRole.ADMIN;
	}

	private UserEntity getUserEntity(String userDomain, String email) {
		String id = idConverter.getUserEntityIdFromDomainAndEmail(userDomain, email);
		Optional<UserEntity> optional = userDao.findById(id);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			return userEntity;
		} else {
			throw new NotFoundException("Cannot find user with id: " + id);
		}
	}

	private void validateActivityBoundary(ActivityBoundary activity) {
		if (activity.getActivityId() != null)
			throw new RuntimeException("ActivityBoundary must have null ActivityId");

		if (activity.getInstance() == null)
			throw new RuntimeException("ActivityBoundary must have a valid Instance");

		if (activity.getInstance().getInstanceId() == null)
			throw new RuntimeException("ActivityBoundary`s Instance must have a valid InstanceId");

		if (activity.getInstance().getInstanceId().getDomain() == null
				|| activity.getInstance().getInstanceId().getDomain().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InstanceId must have a valid domain");

		if (!activity.getInstance().getInstanceId().getDomain().equals(this.domain))
			throw new RuntimeException("ActivityBoundary`s InstanceId Wrong instance domain");

		if (activity.getInstance().getInstanceId().getId() == null
				|| activity.getInstance().getInstanceId().getId().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InstanceId must have a valid id");

		if (activity.getInvokedBy() == null)
			throw new RuntimeException("ActivityBoundary must have a valid InvokedBy");

		if (activity.getInvokedBy().getUserId() == null)
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid UserId");

		if (activity.getInvokedBy().getUserId().getDomain() == null
				|| activity.getInvokedBy().getUserId().getDomain().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid domain");

		if (!activity.getInvokedBy().getUserId().getDomain().equals(this.domain))
			throw new RuntimeException("ActivityBoundary`s InvokedBy Wrong user domain");

		if (activity.getInvokedBy().getUserId().getEmail() == null
				|| activity.getInvokedBy().getUserId().getEmail().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid id");

		if (activity.getType() == null || activity.getType().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary must have a valid type");

	}

	public List<InstanceEntity> getInstancesByCreatedUserId(String createdByUserId) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(MongoDBConfig.class);
		ctx.refresh();
		MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);

		Query query = new Query(Criteria.where("createdByUserId").is(createdByUserId));
		List<InstanceEntity> res = mongoOps.find(query, InstanceEntity.class);
		if (res.size() == 0)
			throw new NotFoundException("There is no active instance with createdByUserId " + createdByUserId);

		return res;

	}

}
