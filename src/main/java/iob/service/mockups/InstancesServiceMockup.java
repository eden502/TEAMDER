package iob.service.mockups;

import java.util.List;
import java.util.Vector;
import org.springframework.stereotype.Service;
import bounderies.InstanceBoundary;
import iob.data.InstanceEntity;
import iob.logic.InstancesService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class InstancesServiceMockup implements InstancesService {
	
	Vector<InstanceEntity> v = new Vector<InstanceEntity>();
	
	@Autowired
	public InstancesServiceMockup() {
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InstanceBoundary> getAllInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInstances() {
		// TODO Auto-generated method stub

	}

}
