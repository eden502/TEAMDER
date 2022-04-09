package iob.service.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.data.InstanceEntity;
import iob.logic.InstanceConverter;
import iob.logic.InstancesService;
@Service
public class InstanceServiceJpa implements InstancesService{

	private InstanceDao instanceDao;
	private InstanceConverter instanceConverter;
	private AtomicLong idGenerator;
	private String domain;

	@Autowired
	public InstanceServiceJpa(InstanceConverter instanceConverter,InstanceDao instanceDao) {
		this.instanceConverter = instanceConverter;
		this.instanceDao = instanceDao;
		this.idGenerator = new AtomicLong();
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}


	@Override
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		//validate crucial fields
		validateInstanceBoundary(instance);
		//create new Id 
		GeneralId instanceId = new GeneralId();
		instanceId.setDomain(this.domain);
		instanceId.setId("" + idGenerator.incrementAndGet());
		instance.setInstanceId(instanceId);
		
		//time stamp
		instance.setCreatedTimestamp(new Date());
		// convert to entity
		InstanceEntity entity = this.instanceConverter.toEntity(instance);
		
		//save
		entity = this.instanceDao.save(entity);
		
		//return boundary
		return this.instanceConverter.toBoundary(entity);
	}

	

	@Override
	@Transactional
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		InstanceEntity instanceForUpdate = this.getSpecificEntityInstance(instanceId);

		if (update.getType() != null)
			instanceForUpdate.setType(update.getType());

		if (update.getName() != null)
			instanceForUpdate.setName(update.getName());

		if (update.getActive() != null)
			instanceForUpdate.setActive(update.getActive());

		if (update.getLocation() != null) {
			instanceForUpdate.setLocationLat(update.getLocation().getLat());
			instanceForUpdate.setLocationLng(update.getLocation().getLng());
		}
		if (update.getInstanceAttributes() != null)
			instanceForUpdate.setInstanceAttributes(update.getInstanceAttributes());
		
		instanceForUpdate = this.instanceDao.save(instanceForUpdate);
		
		return this.instanceConverter.toBoundary(instanceForUpdate);
	}



	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {

		return instanceConverter.toBoundary(getSpecificEntityInstance(instanceId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getAllInstances() {
		Iterable<InstanceEntity> iterable = this.instanceDao
				.findAll();
		Stream<InstanceEntity> stream = StreamSupport.stream(iterable.spliterator(), false);
		return stream.map(instanceConverter::toBoundary).collect(Collectors.toList()) ;
	}

	@Override
	@Transactional
	public void deleteAllInstances() {
		this.instanceDao.deleteAll();

	}
	private InstanceEntity getSpecificEntityInstance(String instanceId) {
		Optional<InstanceEntity> optional = this.instanceDao
				.findById(instanceId);
			
			if (optional.isPresent()) {
				return optional.get();
			}
			
			throw new RuntimeException("Could not find Instance by id: " + instanceId);
			
	}
	
	private void validateInstanceBoundary(InstanceBoundary instance) {
		if(instance.getType()==null|| instance.getType().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary must have a valid type");
		
		if(instance.getName()==null|| instance.getName().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary must have a valid name");
		
		if(instance.getActive()==null)
			throw new RuntimeException("InstanceBoundary must have a active value");

		if (instance.getCreatedBy().getUserId().getDomain() == null
				|| instance.getCreatedBy().getUserId().getDomain().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary's UserId must have a valid domain");

		if (instance.getCreatedBy().getUserId().getEmail() == null
				|| instance.getCreatedBy().getUserId().getEmail().trim().isEmpty())
			throw new RuntimeException("InstanceBoundary's UserId must have a valid email");
	}

}
