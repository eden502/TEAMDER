package iob.logic;
import org.springframework.stereotype.Component;

import bounderies.InstanceBoundary;
import bounderies.InstanceId;
import bounderies.Location;
import iob.data.InstanceEntity;

@Component
public class InstanceConverter {

	public InstanceBoundary toBoundary (InstanceEntity entity) {
		
		InstanceId instanceId = new InstanceId()
				.setDomain(entity.getInstanceDomain())
				.setId(entity.getInstanceId());
		InstanceBoundary instanceBoundary = new InstanceBoundary()
				.setType(entity.getType())
				.setName(entity.getName())
				.setActive(new Boolean(entity.isActive()))
				.setCreatedTimestamp(entity.getCreatedTimestamp())
				.setLocation(new Location().setLat(entity.getLocationLat()).setLng(entity.getLocationLng()))
				.setInstanceAttributes(entity.getInstanceAttributes());
		return instanceBoundary;
	}
	
	public InstanceEntity toEntity (InstanceBoundary boundary) {
		
		InstanceEntity instanceEntity = new InstanceEntity()
				.setInstanceId(boundary.getInstanceId().getId())
				.setInstanceDomain(boundary.getInstanceId().getDomain())
				.setType(boundary.getType())
				.setName(boundary.getName())
				.setActive(boundary.getActive().booleanValue())
				.setCreatedTimestamp(boundary.getCreatedTimestamp())
				.setCreatedByUserDomain(boundary.getCreatedBy().getUserId().getDomain())
				.setCreatedByUserEmail(boundary.getCreatedBy().getUserId().getEmail())
				.setLocationLat(boundary.getLocation().getLat())
				.setLocationLng(boundary.getLocation().getLng())
				.setInstanceAttributes(boundary.getInstanceAttributes());
		return instanceEntity;
	}
}
