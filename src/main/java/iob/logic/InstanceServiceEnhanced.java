package iob.logic;

import java.util.List;

import iob.bounderies.InstanceBoundary;
import iob.bounderies.UserBoundary;
import iob.data.InstanceEntity;


public interface InstanceServiceEnhanced extends InstancesService{
	public InstanceBoundary updateInstance( String userDomain, String userEmail,String instanceDomain, String instanceId, InstanceBoundary update);

	public List<InstanceBoundary> getInstancesNear(String userDomain, String userEmail, int page, int size, double lat, double lng,
			double distance);

	public InstanceBoundary getSpecificInstance(String userDomain, String userEmail, String instanceDomain,
			String instanceId);
	
	public List<InstanceBoundary> getAllInstances(String userDomain,String userEmail,int size,int page);

	public void deleteAllInstances(String domain, String email);
	
	public List<InstanceBoundary> getInstancesName(String userDomain, String userEmail, int page, int size, String name);
	
	public List<InstanceBoundary> getInstancesType(String userDomain, String userEmail, int page, int size, String type);

}

