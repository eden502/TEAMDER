package iob.service.mockups;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import bounderies.InstanceBoundary;
import iob.data.InstanceEntity;
import iob.logic.InstanceConverter;
import iob.logic.InstancesService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class InstancesServiceMockup implements InstancesService {
	
	Vector<InstanceEntity> instanceEntityVector = new Vector<InstanceEntity>();
	InstanceConverter instanceConverter;
	
	
	@Autowired
	public InstancesServiceMockup(InstanceConverter instanceConverter) {
		this.instanceConverter = instanceConverter;
	}
	
	@Override
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		// TODO Auto-generated method stub
		return null;
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
