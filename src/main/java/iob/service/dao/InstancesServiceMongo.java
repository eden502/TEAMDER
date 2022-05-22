package iob.service.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import MongoDBConfig.MongoDBConfig;
import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.exceptions.NoPermissionException;
import iob.exceptions.NotFoundException;
import iob.logic.IdConverter;
import iob.logic.InstanceConverter;
import iob.logic.InstanceServiceEnhanced;


@Service
public class InstancesServiceMongo implements InstanceServiceEnhanced {

	private UserDao userDao;
	private InstanceDao instanceDao;
	private InstanceConverter instanceConverter;
	private IdConverter idConverter;
	private String domain;

	@Autowired
	public InstancesServiceMongo(InstanceConverter instanceConverter, IdConverter idConverter, InstanceDao instanceDao,
			UserDao userDao) {
		this.instanceConverter = instanceConverter;
		this.instanceDao = instanceDao;
		this.userDao = userDao;
		this.idConverter = idConverter;

	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		// validate crucial fields
		validateInstanceBoundary(instance);

		if (!userIsManager(instance.getCreatedBy().getUserId().getDomain(),
				instance.getCreatedBy().getUserId().getEmail()))
			throw new NoPermissionException();
		// create new Id
		GeneralId instanceId = new GeneralId();
		instanceId.setDomain(this.domain);
		instanceId.setId(UUID.randomUUID().toString());
		instance.setInstanceId(instanceId);

		// time stamp
		instance.setCreatedTimestamp(new Date());
		// convert to entity
		InstanceEntity entity = this.instanceConverter.toEntity(instance);

		// save
		entity = this.instanceDao.save(entity);

		// return boundary
		return this.instanceConverter.toBoundary(entity);
	}

	private boolean userIsManager(String domain, String email) {
		UserEntity userEntity = getUserEntityById(email, domain);
		return userEntity.getRole() == UserRole.MANAGER;
	}

	private UserEntity getUserEntityById(String userEmail, String userDomain) {
		String id = this.idConverter.getUserEntityIdFromDomainAndEmail(userDomain, userEmail);
		Optional<UserEntity> optional = this.userDao.findById(id);

		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			return userEntity;
		} else {
			throw new NotFoundException("Cannot find user with id: " + id);
		}
	}

	@Override
	@Deprecated
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		InstanceEntity instanceForUpdate = this.getSpecificEntityInstance(instanceDomain, instanceId);

		if (update.getType() != null)
			instanceForUpdate.setType(update.getType());

		if (update.getName() != null)
			instanceForUpdate.setName(update.getName());

		if (update.getActive() != null)
			instanceForUpdate.setActive(update.getActive());

		if (update.getLocation() != null) {

			instanceForUpdate
					.setLocation(new GeoJsonPoint(update.getLocation().getLat(), update.getLocation().getLat()));
		}
		if (update.getInstanceAttributes() != null)
			instanceForUpdate.setInstanceAttributes(update.getInstanceAttributes());

		instanceForUpdate = this.instanceDao.save(instanceForUpdate);

		return this.instanceConverter.toBoundary(instanceForUpdate);
	}

	@Override
	@Deprecated
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {

		return instanceConverter.toBoundary(getSpecificEntityInstance(instanceDomain, instanceId));
	}

	/**
	 * New method for get Specific instance- checking permissions.
	 * 
	 */
	@Override
	public InstanceBoundary getSpecificInstance(String userDomain, String userEmail, String instanceDomain,
			String instanceId) {
		UserEntity userEntity = getUserEntityById(userEmail, userDomain);

		if (userEntity.getRole() == UserRole.PLAYER || userEntity.getRole() == UserRole.MANAGER) {
			InstanceBoundary instance = instanceConverter
					.toBoundary(getSpecificEntityInstance(instanceDomain, instanceId));
			if (userEntity.getRole() == UserRole.PLAYER && !instance.getActive()) {
				throw new NotFoundException();
			}
			return instance;
		} else {
			throw new NoPermissionException();
		}
	}

	@Override
	@Deprecated
	public List<InstanceBoundary> getAllInstances() {
		Iterable<InstanceEntity> iterable = this.instanceDao.findAll();
		Stream<InstanceEntity> stream = StreamSupport.stream(iterable.spliterator(), false);
		return stream.map(instanceConverter::toBoundary).collect(Collectors.toList());
	}

	/**
	 * New method for get all instances- checking permissions.
	 * 
	 */
	@Override
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail, int size, int page) {
		UserEntity userEntity = getUserEntityById(userEmail, userDomain);

		if (userEntity.getRole() == UserRole.PLAYER || userEntity.getRole() == UserRole.MANAGER) {
			
			Pageable pageable = PageRequest.of(page, size,Direction.DESC,"createdTimestamp", "name");

			if (userEntity.getRole() == UserRole.PLAYER) {
				AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
				ctx.register(MongoDBConfig.class);
				ctx.refresh();
				MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);
				
				List<Criteria> criteria = new ArrayList<>();
				Query query = new Query().with(pageable);
				criteria.add(Criteria.where("active").is(true));
				query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
				return mongoOps.find(query, InstanceEntity.class).stream().filter(instance -> instance.getActive())
						.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
			}
				
					
			return this.instanceDao.findAll(PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "id"))
					.getContent().stream().map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NoPermissionException();
		}

	}

	@Override
	@Deprecated
	public void deleteAllInstances() {
		this.instanceDao.deleteAll();

	}

	@Override
	public void deleteAllInstances(String domain, String email) {
		Optional<UserEntity> optional = userDao
				.findById(this.idConverter.getUserEntityIdFromDomainAndEmail(domain, email));
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			if (userEntity.getRole().name().equalsIgnoreCase("ADMIN")) {
				this.instanceDao.deleteAll();
				return;
			}

		}
		throw new NoPermissionException("User " + email + " does not have ADMIN permission");
	}

	private InstanceEntity getSpecificEntityInstance(String domain, String instanceId) {
		Optional<InstanceEntity> optional = this.instanceDao
				.findById(this.idConverter.getEntityGeneralIdFromDomainAndId(domain, instanceId));

		if (optional.isPresent()) {
			return optional.get();
		}

		throw new RuntimeException("Could not find Instance by id: " + instanceId + " and domain:" + domain);

	}

	private void validateInstanceBoundary(InstanceBoundary instance) {
		if (instance.getType() == null || instance.getType().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary must have a valid type");

		if (instance.getName() == null || instance.getName().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary must have a valid name");

		if (instance.getActive() == null)
			throw new RuntimeException("InstanceBoundary must have a active value");
		if (instance.getCreatedBy() == null)
			throw new RuntimeException("InstanceBoundary's created by can`t be null");
		if (instance.getCreatedBy().getUserId() == null)
			throw new RuntimeException("InstanceBoundary's created by user id can`t be null");

		if (instance.getCreatedBy().getUserId().getDomain() == null
				|| instance.getCreatedBy().getUserId().getDomain().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary's UserId must have a valid domain");

		if (instance.getCreatedBy().getUserId().getEmail() == null
				|| instance.getCreatedBy().getUserId().getEmail().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary's UserId must have a valid email");
	}

	/**
	 * New method for updating instance- checking permissions.
	 * 
	 */
	@Override
	public InstanceBoundary updateInstance(String userDomain, String userEmail, String instanceDomain,
			String instanceId, InstanceBoundary update) {

		UserEntity userEntity = getUserEntityById(userEmail, userDomain);

		if (userEntity.getRole() == UserRole.MANAGER) {
			InstanceEntity instanceForUpdate = this.getSpecificEntityInstance(instanceDomain, instanceId);

			if (update.getType() != null)
				instanceForUpdate.setType(update.getType());

			if (update.getName() != null)
				instanceForUpdate.setName(update.getName());

			if (update.getActive() != null)
				instanceForUpdate.setActive(update.getActive());

			if (update.getLocation() != null) {

				instanceForUpdate
						.setLocation(new GeoJsonPoint(update.getLocation().getLat(), update.getLocation().getLng()));
			}
			if (update.getInstanceAttributes() != null)
				instanceForUpdate.setInstanceAttributes(update.getInstanceAttributes());

			instanceForUpdate = this.instanceDao.save(instanceForUpdate);

			return this.instanceConverter.toBoundary(instanceForUpdate);
		} else {
			throw new NoPermissionException("No permissions to perform update instance operation.");
		}

	}

	// Get all instances that inside the circle, also check that these instances are
	// active.
	@Override
	public List<InstanceBoundary> getInstancesNear(String userDomain, String userEmail, int page, int size, double lat,
			double lng, double distance) {

		UserEntity userEntity = getUserEntityById(userEmail, userDomain);
		if (userEntity.getRole() == UserRole.PLAYER || userEntity.getRole() == UserRole.MANAGER) {

			List<InstanceEntity> nearEntities = new ArrayList<>();

			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(MongoDBConfig.class);
			ctx.refresh();
			MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);
			Circle circle = new Circle(lat, lng, distance);

			Pageable pageable = PageRequest.of(page, size,Direction.DESC,"createdTimestamp","name");
			
			List<Criteria> criteria = new ArrayList<>();
			
			Query query = new Query().with(pageable);
			criteria.add(Criteria.where("location").within(circle));
			
			if (userEntity.getRole() == UserRole.PLAYER)
				criteria.add(Criteria.where("active").is(true));
			
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			
			nearEntities = mongoOps.find(query, InstanceEntity.class);
			
			Stream<InstanceEntity> stream = StreamSupport.stream(nearEntities.spliterator(), false);
			return stream.map(instanceConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NoPermissionException("No permissions to perform update instance operation.");
		}
	}

	@Override
	public List<InstanceBoundary> getInstancesName(String userDomain, String userEmail, int page, int size,
			String name) {
		UserEntity userEntity = getUserEntityById(userEmail, userDomain);
		if (userEntity.getRole() == UserRole.PLAYER || userEntity.getRole() == UserRole.MANAGER) {

			List<InstanceEntity> nameEntities = new ArrayList<>();

			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(MongoDBConfig.class);
			ctx.refresh();
			MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);
			
			Pageable pageable = PageRequest.of(page, size,Direction.DESC,"createdTimestamp","name");
			
			List<Criteria> criteria = new ArrayList<>();
			
			Query query = new Query().with(pageable);
			criteria.add(Criteria.where("name").is(name));
			
			if (userEntity.getRole() == UserRole.PLAYER)
				criteria.add(Criteria.where("active").is(true));
			
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			
			nameEntities = mongoOps.find(query, InstanceEntity.class);

			Stream<InstanceEntity> stream = StreamSupport.stream(nameEntities.spliterator(), false);
			return stream.map(instanceConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NoPermissionException("No permissions to perform get instance operation.");
		}
	}

	@Override
	public List<InstanceBoundary> getInstancesType(String userDomain, String userEmail, int page, int size,
			String type) {
		UserEntity userEntity = getUserEntityById(userEmail, userDomain);
		if (userEntity.getRole() == UserRole.PLAYER || userEntity.getRole() == UserRole.MANAGER) {

			List<InstanceEntity> typeEntities = new ArrayList<>();

			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(MongoDBConfig.class);
			ctx.refresh();
			MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);

			
			Pageable pageable = PageRequest.of(page, size,Direction.DESC,"createdTimestamp","name");
			
			List<Criteria> criteria = new ArrayList<>();
			
			Query query = new Query().with(pageable);
			criteria.add(Criteria.where("type").is(type));
			
			if (userEntity.getRole() == UserRole.PLAYER)
				criteria.add(Criteria.where("active").is(true));
			
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			
			typeEntities = mongoOps.find(query, InstanceEntity.class);

			Stream<InstanceEntity> stream = StreamSupport.stream(typeEntities.spliterator(), false);
			return stream.map(instanceConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new NoPermissionException("No permissions to perform get instance operation.");
		}
	}

}
