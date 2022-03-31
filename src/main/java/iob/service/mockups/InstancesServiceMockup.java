package iob.service.mockups;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import bounderies.InstanceBoundary;
import bounderies.InstanceId;
import iob.Application;
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
		InstanceId instanceId = new InstanceId()
				.setDomain("2022b.diana.ukrainsky")
				.setId(""+idGenerator.incrementAndGet());
		
		InstanceBoundary instanceBoundary = new InstanceBoundary()
				.setInstanceId(instanceId)
				.setType(instance.getType())
				.setName(instance.getName())
				.setActive(instance.getActive())
				.setCreatedTimestamp(instance.getCreatedTimestamp())
				.setCreatedBy(instance.getCreatedBy())
				.setLocation(instance.getLocation())
				.setInstanceAttributes(instance.getInstanceAttributes());
		instanceEntityVector.add(instanceConverter.toEntity(instance));

		return instanceBoundary;
	}

	private void validateInstanceBoundary(InstanceBoundary instance) {
		if(instance.getType()==null)
			throw new RuntimeException("InstanceBoundary must have a valid type");
		
		if(instance.getActive()==null)
			throw new RuntimeException("InstanceBoundary must have a active value");
		
		if(instance.getCreatedTimestamp()==null)
			throw new RuntimeException("InstanceBoundary must have a valid timestamp");
	
		if(instance.getCreatedBy().getUserId().getDomain()==null ||
			instance.getCreatedBy().getUserId().getDomain().trim().isEmpty()) 
			throw new RuntimeException("InstanceBoundary's UserId must have a valid domain");

			
		if(instance.getCreatedBy().getUserId().getEmail()==null ||
			instance.getCreatedBy().getUserId().getEmail().trim().isEmpty()) 
			throw new RuntimeException("InstanceBoundary's UserId must have a valid email");
		
		
		if(instance.getLocation()==null)
			throw new RuntimeException("InstanceBoundary must have a valid location");
		
		if(instance.getInstanceAttributes().get(instance)==null)
			throw new RuntimeException("InstanceBoundary must have valid attributes");
	}

	@Override
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		InstanceBoundary instanceForUpdate = this.getSpecificInstance(instanceDomain, instanceId);

		if(update.getType()!=null)
			instanceForUpdate.setType(update.getType());
		
		if(update.getActive()!=null)
			instanceForUpdate.setActive(update.getActive());
		
		if(update.getCreatedTimestamp()!=null)
			instanceForUpdate.setCreatedTimestamp(update.getCreatedTimestamp());
	
		if(update.getCreatedBy()!=null)
			instanceForUpdate.setCreatedBy(update.getCreatedBy());
		
		if(update.getLocation()!=null)
			instanceForUpdate.setLocation(update.getLocation());
		
		if(update.getInstanceAttributes()!=null)
			instanceForUpdate.setInstanceAttributes(update.getInstanceAttributes());
		
		return instanceForUpdate;
	}

	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
		
		boolean found;
		for(int i =0 ; i< instanceEntityVector.size() ;i++) {
			String currdomain = instanceEntityVector.get(i).getInstanceDomain();
			String currId = instanceEntityVector.get(i).getInstanceId();
			if(currdomain.equals(instanceDomain) && currId.equals(currId)) {
				return instanceConverter.toBoundary(instanceEntityVector.get(i));
			}
		}
		throw new RuntimeException("Could not found the specific activity.");
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
