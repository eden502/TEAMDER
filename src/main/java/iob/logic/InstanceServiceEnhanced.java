package iob.logic;

import java.util.List;

import iob.bounderies.InstanceBoundary;
import iob.data.InstanceEntity;


public interface InstanceServiceEnhanced extends InstancesService{
	public InstanceBoundary updateInstance( String userDomain, String userEmail,String instanceDomain, String instanceId, InstanceBoundary update);

	public List<InstanceBoundary> getInstancesNear(String userDomain, String userEmail, String page, String size, double lat, double lng,
			double distance);
	public void deleteAllInstances(String domain, String email);
}
