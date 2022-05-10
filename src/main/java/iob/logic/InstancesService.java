package iob.logic;

import java.util.List;

import iob.bounderies.InstanceBoundary;

public interface InstancesService {

	public InstanceBoundary createInstance(InstanceBoundary instance);
	
	@Deprecated
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update);

	@Deprecated
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId);

	@Deprecated
	public List<InstanceBoundary> getAllInstances();
	
	@Deprecated
	public void deleteAllInstances();

	List<InstanceBoundary> getInstancesName(String userDomain, String userEmail, int page, int size, String name);
	
	List<InstanceBoundary> getInstancesType(String userDomain, String userEmail, int page, int size, String type);
}
