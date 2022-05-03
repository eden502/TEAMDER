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
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.exceptions.NoPermissionException;
import iob.exceptions.NotFoundException;
import iob.logic.IdConverter;
import iob.logic.InstanceConverter;
import iob.logic.InstanceServiceEnhanced;
import iob.logic.InstancesService;

@Service
public class InstancesServiceJpa implements InstanceServiceEnhanced {

	private UserDao userDao;
	private InstanceDao instanceDao;
	private InstanceConverter instanceConverter;
	private String domain;

	@Autowired
	public InstancesServiceJpa(InstanceConverter instanceConverter, InstanceDao instanceDao, UserDao userDao) {
		this.instanceConverter = instanceConverter;
		this.instanceDao = instanceDao;
		this.userDao = userDao;
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		// validate crucial fields
		validateInstanceBoundary(instance);
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
			// double [] location =
			// {update.getLocation().getLat(),update.getLocation().getLng()};
			instanceForUpdate
					.setLocation(new GeoJsonPoint(update.getLocation().getLat(), update.getLocation().getLat()));
		}
		if (update.getInstanceAttributes() != null)
			instanceForUpdate.setInstanceAttributes(update.getInstanceAttributes());

		instanceForUpdate = this.instanceDao.save(instanceForUpdate);

		return this.instanceConverter.toBoundary(instanceForUpdate);
	}

	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {

		return instanceConverter.toBoundary(getSpecificEntityInstance(instanceDomain, instanceId));
	}

	@Override
	public List<InstanceBoundary> getAllInstances() {
		Iterable<InstanceEntity> iterable = this.instanceDao.findAll();
		Stream<InstanceEntity> stream = StreamSupport.stream(iterable.spliterator(), false);
		return stream.map(instanceConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Deprecated
	public void deleteAllInstances() {
		this.instanceDao.deleteAll();

	}
	@Override
	@Transactional
	public void deleteAllInstances(String domain, String email) {
		Optional<UserEntity> optional = userDao.findById(domain + "@@" + email);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			if (userEntity.getRole().name().equalsIgnoreCase("ADMIN")) {
				this.instanceDao.deleteAll();
				return;
			}
			
		}
		throw new NoPermissionException("User "+email+" does not have ADMIN permission");
	}

	private InstanceEntity getSpecificEntityInstance(String domain, String instanceId) {
		Optional<InstanceEntity> optional = this.instanceDao
				.findById(instanceConverter.getEntityGeneralIdFromDomainAndId(domain, instanceId));

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

		Optional<UserEntity> optional = userDao.findById(userDomain + "@@" + userEmail);

		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			if (userEntity.getRole().name().equalsIgnoreCase("PLAYER")) {
				InstanceEntity instanceForUpdate = this.getSpecificEntityInstance(instanceDomain, instanceId);

				if (update.getType() != null)
					instanceForUpdate.setType(update.getType());

				if (update.getName() != null)
					instanceForUpdate.setName(update.getName());

				if (update.getActive() != null)
					instanceForUpdate.setActive(update.getActive());

				if (update.getLocation() != null) {
					// double [] location =
					// {update.getLocation().getLat(),update.getLocation().getLat()};
					instanceForUpdate.setLocation(
							new GeoJsonPoint(update.getLocation().getLat(), update.getLocation().getLat()));
				}
				if (update.getInstanceAttributes() != null)
					instanceForUpdate.setInstanceAttributes(update.getInstanceAttributes());

				instanceForUpdate = this.instanceDao.save(instanceForUpdate);

				return this.instanceConverter.toBoundary(instanceForUpdate);
			} else {
				throw new NoPermissionException("No permissions to perform update instance operation.");
			}
		} else {
			throw new NotFoundException("Cannot find user with id: " + instanceId);
		}

	}

	// Get all instances that inside the circle, also check that these instances are
	// active.
	@Override
	public List<InstanceBoundary> getInstancesNear(String userDomain, String userEmail, String page, String size,
			double lat, double lng, double distance) {

		Optional<UserEntity> optional = userDao.findById(userDomain + "@@" + userEmail);

		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			if (userEntity.getRole().name().equalsIgnoreCase("PLAYER")) {

				ArrayList<InstanceEntity> nearEntities = new ArrayList<>();
				for (InstanceEntity instance : instanceDao.findByLocationNear(new Point(lat, lng),
						new Distance(distance, Metrics.KILOMETERS))) {
					if (instance.getActive()) {
						nearEntities.add(instance);
					}
				}
				if (nearEntities.size() == 0)
					throw new NotFoundException("No near active instances");

				Stream<InstanceEntity> stream = StreamSupport.stream(nearEntities.spliterator(), false);
				return stream.map(instanceConverter::toBoundary).collect(Collectors.toList());
			} else {
				throw new NoPermissionException("No permissions to perform update instance operation.");
			}
		} else {
			throw new NotFoundException("Cannot find user with id: " + userDomain + "@@" + userEmail);
		}

	}

}
