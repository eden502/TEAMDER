package iob.service.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.exceptions.NoPermissionException;
import iob.exceptions.NotFoundException;
import iob.logic.ActivitiesService;
import iob.logic.ActivitiesServiceEnhanced;
import iob.logic.ActivityConverter;
import iob.logic.UserConverter;

@Service
public class ActivitiesServiceJpa implements ActivitiesServiceEnhanced {

	
	private ActivityDao activityDao;
	private UserDao userDao;
	private ActivityConverter activityConverter;
	private UserConverter userConverter;
	private String domain;

	@Autowired
	public ActivitiesServiceJpa(ActivityConverter activityConverter,ActivityDao activityDao,UserDao userDao,UserConverter userConverter) {
		this.activityConverter = activityConverter;
		this.activityDao = activityDao;
		this.userDao = userDao;
		this.userConverter = userConverter;
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}


	@Override
	@Transactional
	public Object invokeActivity(ActivityBoundary activity) {
		validateActivityBoundary(activity);

		GeneralId activityId = new GeneralId();
		activityId.setDomain(this.domain);
		activityId.setId(UUID.randomUUID().toString());

		activity.setActivityId(activityId);
		activity.setCreatedTimestamp(new Date());
		
		// convert to entity
		ActivityEntity entity = this.activityConverter.toEntity(activity);
		
		//save
		entity = this.activityDao.save(entity);
		
		//return boundary
		return this.activityConverter.toBoundary(entity);
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
		
		if(userEntity.getRole() != UserRole.ADMIN)
			throw new NoPermissionException();
	
		return this.activityDao.findAll(PageRequest.of(page, size, Direction.DESC,"role"))
				.getContent()
				.stream()
				.map(this.activityConverter::toBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Deprecated
	public void deleteAllAcitivities() {
		//this.activityDao.deleteAll();
		throw new RuntimeException("deprecated method");
	}
	
	@Override
	public void deleteAllAcitivities(String domain, String email) {
		UserEntity userEntity = getUserEntity(domain, email);
		
		if(userIsAdmin(userEntity)) {
			this.activityDao.deleteAll();
		}else {
			throw new NoPermissionException("user with id : " + userEntity.getId() + " has No permission to delete all activities");
		}
		
	}
	
	
	private boolean userIsAdmin(UserEntity userEntity) {
		
		return userEntity.getRole() == UserRole.ADMIN;
	}



	private UserEntity getUserEntity(String userDomain, String email) {
		String id = userConverter.getUserEntityIdFromDomainAndEmail(userDomain, email);
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
		
		if(activity.getType()==null|| activity.getType().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary must have a valid type");
	}




}
