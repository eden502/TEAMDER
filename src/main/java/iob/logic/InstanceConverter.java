package iob.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

import iob.bounderies.CreatedBy;
import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.bounderies.Location;
import iob.bounderies.UserId;
import iob.data.InstanceEntity;

@Component
public class InstanceConverter {

	private IdConverter idConverter;

	@Autowired
	public InstanceConverter(IdConverter idConverter) {
		this.idConverter = idConverter;
	}

	public InstanceBoundary toBoundary(InstanceEntity entity) {

		GeneralId instanceId = new GeneralId();
		instanceId.setDomain(idConverter.getDomainFromEntityGeneralId(entity.getId()));
		instanceId.setId(idConverter.getIdFromEntityGeneralId(entity.getId()));

		Location location = new Location();
		location.setLat(entity.getLocation().getX());
		location.setLng(entity.getLocation().getY());

		UserId userId = new UserId();
		userId.setDomain(idConverter.getUserDomainFromUserEntityId(entity.getCreatedByUserId()));
		userId.setEmail(idConverter.getUserEmailFromUserEntityId(entity.getCreatedByUserId()));

		CreatedBy createdBy = new CreatedBy();
		createdBy.setUserId(userId);

		InstanceBoundary instanceBoundary = new InstanceBoundary();
		instanceBoundary.setType(entity.getType());
		instanceBoundary.setName(entity.getName());
		instanceBoundary.setActive(entity.getActive());
		instanceBoundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		instanceBoundary.setLocation(location);
		instanceBoundary.setInstanceAttributes(entity.getInstanceAttributes());
		instanceBoundary.setCreatedBy(createdBy);
		instanceBoundary.setInstanceId(instanceId);

		return instanceBoundary;
	}



	public InstanceEntity toEntity(InstanceBoundary boundary) {

		// default primitive values
		if (boundary.getActive() == null)
			boundary.setActive(false);
		if (boundary.getLocation() == null) {
			Location location = new Location();
			location.setLat(0.0);
			location.setLng(0.0);
			boundary.setLocation(location);
		}

		InstanceEntity instanceEntity = new InstanceEntity();
		instanceEntity.setId(
				idConverter.getEntityGeneralIdFromDomainAndId(
						boundary.getInstanceId().getDomain(),
						boundary.getInstanceId().getId()));
		instanceEntity.setType(boundary.getType());
		instanceEntity.setName(boundary.getName());
		instanceEntity.setActive(boundary.getActive().booleanValue());
		instanceEntity.setCreatedTimestamp(boundary.getCreatedTimestamp());

		instanceEntity.setCreatedByUserId(
				idConverter.getUserEntityIdFromDomainAndEmail(
						boundary.getCreatedBy().getUserId().getDomain(),
						boundary.getCreatedBy().getUserId().getEmail()));

		//double [] location = {boundary.getLocation().getLat(),boundary.getLocation().getLng()};
		instanceEntity.setLocation(new GeoJsonPoint(boundary.getLocation().getLat(),boundary.getLocation().getLng()));
		instanceEntity.setInstanceAttributes(boundary.getInstanceAttributes());

		return instanceEntity;
	}
	
	

	

}
