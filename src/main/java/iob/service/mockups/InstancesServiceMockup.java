package iob.service.mockups;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.data.InstanceEntity;
import iob.logic.InstanceConverter;
import iob.logic.InstancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@Service
public class InstancesServiceMockup implements InstancesService {
	
	private List<InstanceEntity> instanceEntityList; 
	private InstanceConverter instanceConverter;
	private AtomicLong idGenerator; 
	private String domain;

	@Autowired
	public InstancesServiceMockup(InstanceConverter instanceConverter) {
		this.instanceConverter = instanceConverter;
		this.idGenerator = new AtomicLong();
	}
	
	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@PostConstruct
	public void init () {
		// create a thread safe list
		this.instanceEntityList = Collections.synchronizedList(new ArrayList<>()); 
	}
	
	@Override
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		validateInstanceBoundary(instance);
		GeneralId instanceId = new GeneralId();
		instanceId.setDomain(this.domain);
		instanceId.setId("" + idGenerator.incrementAndGet());
		instance.setInstanceId(instanceId);
		instance.setCreatedTimestamp(java.time.LocalDateTime.now().toString());
		

		instanceEntityList.add(instanceConverter.toEntity(instance));

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
		
		if(update.getName()!=null)
			instanceForUpdate.setName(update.getName());
		
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
		for(int i =0 ; i< instanceEntityList.size() ;i++) {
			String currdomain = instanceEntityList.get(i).getInstanceDomain();
			String currId = instanceEntityList.get(i).getInstanceId();
			if(currdomain.equals(instanceDomain) && currId.equals(instanceId)) {
				return instanceEntityList.get(i);
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
		return instanceEntityList 
				.stream()
				.map(instanceConverter::toBoundary) 
				.collect(Collectors.toList()); 
	}

	@Override
	public void deleteAllInstances() {
		instanceEntityList.clear();

	}



}
