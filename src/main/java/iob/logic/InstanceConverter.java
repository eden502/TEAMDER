package iob.logic;
import org.springframework.stereotype.Component;

import iob.bounderies.CreatedBy;
import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.bounderies.Location;
import iob.bounderies.UserId;
import iob.data.InstanceEntity;

@Component
public class InstanceConverter {

	public InstanceBoundary toBoundary (InstanceEntity entity) {
		
		GeneralId instanceId = new GeneralId();
		instanceId.setDomain(entity.getInstanceDomain());
		instanceId.setId(entity.getInstanceId());
		
		Location location = new Location();
		location.setLat(entity.getLocationLat());
		location.setLng(entity.getLocationLng());
		
		UserId userId = new UserId();
		userId.setDomain(entity.getCreatedByUserDomain());
		userId.setEmail(entity.getCreatedByUserEmail());

		
		CreatedBy createdBy = new CreatedBy();
		createdBy.setUserId(userId);
		
		InstanceBoundary instanceBoundary = new InstanceBoundary();
		instanceBoundary.setType(entity.getType());
		instanceBoundary.setName(entity.getName());
		instanceBoundary.setActive(entity.isActive());
		instanceBoundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		instanceBoundary.setLocation(location);
		instanceBoundary.setInstanceAttributes(entity.getInstanceAttributes());
		instanceBoundary.setCreatedBy(createdBy);
		instanceBoundary.setInstanceId(instanceId);

		
		return instanceBoundary;
	}
	
	public InstanceEntity toEntity (InstanceBoundary boundary) {
		
		// default primitive values
		if(boundary.getActive() == null)
			boundary.setActive(false);
		if(boundary.getLocation() == null) {
			Location location = new Location();
			location.setLat(0.0);
			location.setLng(0.0);
			boundary.setLocation(location);
		}
		
		InstanceEntity instanceEntity = new InstanceEntity();
		instanceEntity.setInstanceId(boundary.getInstanceId().getId());
		instanceEntity.setInstanceDomain(boundary.getInstanceId().getDomain());
		instanceEntity.setType(boundary.getType());
		instanceEntity.setName(boundary.getName());
		instanceEntity.setActive(boundary.getActive().booleanValue());
		instanceEntity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		instanceEntity.setCreatedByUserDomain(boundary.getCreatedBy().getUserId().getDomain());
		instanceEntity.setCreatedByUserEmail(boundary.getCreatedBy().getUserId().getEmail());
		instanceEntity.setLocationLat(boundary.getLocation().getLat());
		instanceEntity.setLocationLng(boundary.getLocation().getLng());
		instanceEntity.setInstanceAttributes(boundary.getInstanceAttributes());
		
		return instanceEntity;
	}
}
