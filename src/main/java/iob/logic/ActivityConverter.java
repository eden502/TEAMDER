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
		activityEntity.setId(getEntityGeneralIdFromDomainAndId(boundary.getActivityId().getDomain(),
				boundary.getActivityId().getId()));
		activityEntity.setType(boundary.getType());
		activityEntity.setInstanceId(getEntityGeneralIdFromDomainAndId(
				boundary.getInstance().getInstanceId().getDomain(), boundary.getInstance().getInstanceId().getId()));
		activityEntity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		activityEntity.setInvokedUserId(getUserEntityIdFromDomainAndEmail(
				boundary.getInvokedBy().getUserId().getDomain(), boundary.getInvokedBy().getUserId().getEmail()));
		activityEntity.setActivityAttributes(boundary.getActivityAttributes());

		return activityEntity;
	}

	public ActivityBoundary toBoundary(ActivityEntity entity) {
		
		
		
		GeneralId activityId = new GeneralId();
		activityId.setDomain(getDomainFromEntityGeneralId(entity.getId()));
		activityId.setId(getIdFromEntityGeneralId(entity.getId()));

		GeneralId instanceId = new GeneralId();
		instanceId.setId(getIdFromEntityGeneralId(entity.getInstanceId()));
		instanceId.setDomain(getDomainFromEntityGeneralId(entity.getInstanceId()));

		UserId userId = new UserId();
		userId.setEmail(getUserEmailFromUserEntityId(entity.getInvokedUserId()));
		userId.setDomain(getUserDomainFromUserEntityId(entity.getInvokedUserId()));

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

	public String getUserEntityIdFromDomainAndEmail(String domain, String email) {

		return idConverter.getUserEntityIdFromDomainAndEmail(domain, email);
	}

	public String getEntityGeneralIdFromDomainAndId(String domain, String id) {

		return idConverter.getEntityGeneralIdFromDomainAndId(domain, id);
	}

	public String getUserEmailFromUserEntityId(String userId) {

		return idConverter.getUserEmailFromUserEntityId(userId);
	}

	public String getUserDomainFromUserEntityId(String userId) {

		return idConverter.getUserDomainFromUserEntityId(userId);
	}

	public String getIdFromEntityGeneralId(String generalId) {

		return idConverter.getIdFromEntityGeneralId(generalId);
	}

	public String getDomainFromEntityGeneralId(String generalId) {

		return idConverter.getDomainFromEntityGeneralId(generalId);
	}

}
