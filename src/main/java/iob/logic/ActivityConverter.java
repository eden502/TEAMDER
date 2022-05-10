package iob.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.bounderies.Instance;
import iob.bounderies.InvokedBy;
import iob.bounderies.UserId;
import iob.data.ActivityEntity;

@Component
public class ActivityConverter {

	private IdConverter idConverter;

	@Autowired
	public ActivityConverter(IdConverter idConverter) {
		this.idConverter = idConverter;
	}

	public ActivityEntity toEntity(ActivityBoundary boundary) {

		ActivityEntity activityEntity = new ActivityEntity();
		activityEntity.setId(idConverter.getEntityGeneralIdFromDomainAndId(
				boundary.getActivityId().getDomain(),
				boundary.getActivityId().getId()));
		activityEntity.setType(boundary.getType());
		activityEntity.setInstanceId(
				idConverter.getEntityGeneralIdFromDomainAndId(
				boundary.getInstance().getInstanceId().getDomain(),
				boundary.getInstance().getInstanceId().getId()));
		activityEntity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		activityEntity.setInvokedUserId(
				idConverter.getUserEntityIdFromDomainAndEmail(
				boundary.getInvokedBy().getUserId().getDomain(),
				boundary.getInvokedBy().getUserId().getEmail()));
		activityEntity.setActivityAttributes(boundary.getActivityAttributes());

		return activityEntity;
	}

	public ActivityBoundary toBoundary(ActivityEntity entity) {
		
		
		
		GeneralId activityId = new GeneralId();
		activityId.setDomain(idConverter.getDomainFromEntityGeneralId(entity.getId()));
		activityId.setId(idConverter.getIdFromEntityGeneralId(entity.getId()));

		GeneralId instanceId = new GeneralId();
		instanceId.setId(idConverter.getIdFromEntityGeneralId(entity.getInstanceId()));
		instanceId.setDomain(idConverter.getDomainFromEntityGeneralId(entity.getInstanceId()));

		UserId userId = new UserId();
		userId.setEmail(idConverter.getUserEmailFromUserEntityId(entity.getInvokedUserId()));
		userId.setDomain(idConverter.getUserDomainFromUserEntityId(entity.getInvokedUserId()));

		Instance instance = new Instance();
		instance.setInstanceId(instanceId);

		InvokedBy invokedBy = new InvokedBy();
		invokedBy.setUserId(userId);

		ActivityBoundary activityBoundary = new ActivityBoundary();
		activityBoundary.setActivityId(activityId);
		activityBoundary.setType(entity.getType());
		activityBoundary.setInstance(instance);
		activityBoundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		activityBoundary.setInvokedBy(invokedBy);
		activityBoundary.setActivityAttributes(entity.getActivityAttributes());

		return activityBoundary;
	}


}
