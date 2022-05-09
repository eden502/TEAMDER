package iob.service.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.data.ActivityEntity;
import iob.data.ActivityType;
import iob.data.InstanceEntity;
import iob.data.InstanceType;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.exceptions.NoPermissionException;
import iob.exceptions.NotFoundException;
import iob.logic.ActivitiesService;
import iob.logic.ActivitiesServiceEnhanced;
import iob.logic.ActivityConverter;
import iob.logic.IdConverter;
import iob.logic.UserConverter;

@Service
public class ActivitiesServiceJpa implements ActivitiesServiceEnhanced {

	private ActivityDao activityDao;
	private UserDao userDao;
	private InstanceDao instanceDao;
	private ActivityConverter activityConverter;
	private IdConverter idConverter;
	private String domain;

	@Autowired
	public ActivitiesServiceJpa(ActivityConverter activityConverter, ActivityDao activityDao, UserDao userDao,
			InstanceDao instanceDao, IdConverter idConverter) {
		this.activityConverter = activityConverter;
		this.activityDao = activityDao;
		this.userDao = userDao;
		this.instanceDao = instanceDao;
		this.idConverter = idConverter;

	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	@Transactional
	public Object invokeActivity(ActivityBoundary activity) {
		validateActivityBoundary(activity);

		UserEntity userEntity = getUserEntity(activity.getInvokedBy().getUserId().getDomain(),
				activity.getInvokedBy().getUserId().getEmail());

		InstanceEntity instanceEntity = getInstanceEntity(activity.getInstance().getInstanceId().getDomain(),
				activity.getInstance().getInstanceId().getId());

		validateUserAndInstance(userEntity, instanceEntity);

		//executeActivity(activity, userEntity, instanceEntity);

		GeneralId activityId = new GeneralId();
		activityId.setDomain(this.domain);
		activityId.setId(UUID.randomUUID().toString());

		activity.setActivityId(activityId);
		activity.setCreatedTimestamp(new Date());

		// convert to entity
		ActivityEntity entity = this.activityConverter.toEntity(activity);

		// save
		entity = this.activityDao.save(entity);

		// return boundary
		return this.activityConverter.toBoundary(entity);
	}

	// Only Player user can invoke activity on an active instance
	private void validateUserAndInstance(UserEntity userEntity, InstanceEntity instanceEntity) {
		if (userEntity.getRole() != UserRole.PLAYER)
			throw new NoPermissionException();

		if (!instanceEntity.getActive())
			throw new NotFoundException();
	}

	private void executeActivity(ActivityBoundary activity, UserEntity userEntity, InstanceEntity instanceEntity) {

		ActivityType activityType = ActivityType.valueOf(activity.getType().toUpperCase());
		switch (activityType) {
		case JOIN_GROUP:
			// TODO implement
			break;

		case DELETE_GROUP:
			// TODO implement
			break;

		case ACCEPT_NEW_MEMBER:
			executeAcceptNewMember(activity, instanceEntity);
			break;

		case DELETE_MEMBER:
			// TODO implement
			break;

		case EXIT_GROUP:
			// TODO implement
			break;

		}

	}

	private void executeAcceptNewMember(ActivityBoundary acceptNewMemberActivity, InstanceEntity groupInstance) {

		if (!isGroupInstance(groupInstance)) {
			throw new RuntimeException("Invoked Instance is not GROUP type");
		}
		
		String pendingUserId = (String) acceptNewMemberActivity.getActivityAttributes().get("User");
		InstanceEntity pendingMember = getUserInstanceFromUserId(pendingUserId);
		
		Map<String, Object> groupAttributes = groupInstance.getInstanceAttributes();
		Map<String, Object> memberAttributes = pendingMember.getInstanceAttributes();
		
		
		Set<String> members = (Set<String>) groupAttributes.get("Members");
		Set<String> pendingMembers = (Set<String>) groupAttributes.get("PendingMembers");
		Set<String>	groups = (Set<String>) memberAttributes.get("Groups");
		
		pendingMembers.remove(pendingUserId);
		members.add(pendingUserId);
		groups.add(groupInstance.getId());	

		instanceDao.save(pendingMember);
		instanceDao.save(groupInstance);

	}



	private InstanceEntity getUserInstanceFromUserId(String pendingUserId) {
		// TODO Implement this method to return the instance connected to this user id
		return null;
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
	@Transactional(readOnly = true)
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

		if (!activity.getType().equalsIgnoreCase(ActivityType.ACCEPT_NEW_MEMBER.toString())
				&& !activity.getType().equalsIgnoreCase(ActivityType.DELETE_GROUP.toString())
				&& !activity.getType().equalsIgnoreCase(ActivityType.DELETE_MEMBER.toString())
				&& !activity.getType().equalsIgnoreCase(ActivityType.EXIT_GROUP.toString())
				&& !activity.getType().equalsIgnoreCase(ActivityType.JOIN_GROUP.toString()))
			throw new RuntimeException("ActivityBoundary - unreconized ActivityType");
	}

}
