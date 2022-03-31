package iob.service.mockups;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.data.InstanceEntity;
import iob.logic.InstanceConverter;
import iob.logic.InstancesService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class InstancesServiceMockup implements InstancesService {
	
	Vector<InstanceEntity> instanceEntityVector = new Vector<InstanceEntity>();
	private InstanceConverter instanceConverter;
	private AtomicLong idGenerator; 

	@Autowired
	public InstancesServiceMockup(InstanceConverter instanceConverter) {
		this.instanceConverter = instanceConverter;
	}
	
	@Override
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		validateInstanceBoundary(instance);
		GeneralId instanceId = new GeneralId();
		instanceId.setDomain("2022b.diana.ukrainsky");
		instanceId.setId("" + idGenerator.incrementAndGet());

		instance.setInstanceId(instanceId);
		instance.setCreatedTimestamp(java.time.LocalDateTime.now().toString());

		instanceEntityVector.add(instanceConverter.toEntity(instance));

		return instance;
	}

	private void validateInstanceBoundary(InstanceBoundary instance) {
//		if(instance.getType()==null)
//			throw new RuntimeException("InstanceBoundary must have a valid type");
//		
//		if(instance.getActive()==null)
//			throw new RuntimeException("InstanceBoundary must have a active value");
		
//		if(instance.getCreatedTimestamp()==null)
//			throw new RuntimeException("InstanceBoundary must have a valid timestamp");
//	
		if(instance.getCreatedBy().getUserId().getDomain()==null ||
			instance.getCreatedBy().getUserId().getDomain().trim().isEmpty()) 
			throw new RuntimeException("InstanceBoundary's UserId must have a valid domain");

		if(instance.getCreatedBy().getUserId().getEmail()==null ||
			instance.getCreatedBy().getUserId().getEmail().trim().isEmpty()) 
			throw new RuntimeException("InstanceBoundary's UserId must have a valid email");
	}

	@Override
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		InstanceEntity instanceForUpdate = this.getSpecificEntityInstance(instanceDomain, instanceId);
		
		if(update.getType()!=null)
			instanceForUpdate.setType(update.getType());
		
		if(update.getActive()!=null)
			instanceForUpdate.setActive(update.getActive());
		
		if(update.getLocation()!=null)
		{
			instanceForUpdate.setLocationLat(update.getLocation().getLat());
			instanceForUpdate.setLocationLng(update.getLocation().getLng());
		}
		if(update.getInstanceAttributes()!=null)
			instanceForUpdate.setInstanceAttributes(update.getInstanceAttributes());
		
		return this.instanceConverter.toBoundary(instanceForUpdate);
	}

	private InstanceEntity getSpecificEntityInstance(String instanceDomain, String instanceId) {
		for(int i =0 ; i< instanceEntityVector.size() ;i++) {
			String currdomain = instanceEntityVector.get(i).getInstanceDomain();
			String currId = instanceEntityVector.get(i).getInstanceId();
			if(currdomain.equals(instanceDomain) && currId.equals(currId)) {
				return instanceEntityVector.get(i);
			}
		}
		throw new RuntimeException("Could not found the specific instance.");
	}

	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
		return instanceConverter.toBoundary(getSpecificEntityInstance(instanceDomain, instanceId));
	}

	@Override
	public List<InstanceBoundary> getAllInstances() {
		return instanceEntityVector 
				.stream()
				.map(instanceConverter::toBoundary) 
				.collect(Collectors.toList()); 
	}

	@Override
	public void deleteAllInstances() {
		instanceEntityVector.clear();

	}



}
