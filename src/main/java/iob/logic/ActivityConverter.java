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
		activityEntity.setActivityId(boundary.getActivityId().getId());
		activityEntity.setActivityDomain(boundary.getActivityId().getDomain());
		activityEntity.setType(boundary.getType());
		activityEntity.setInstanceDomain(boundary.getInstance().getInstanceId().getDomain());
		activityEntity.setInstanceId(boundary.getInstance().getInstanceId().getId());
		activityEntity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		activityEntity.setInvokedUserDomain(boundary.getInvokedBy().getUserId().getDomain());
		activityEntity.setInvokedUserEmail(boundary.getInvokedBy().getUserId().getEmail());
		activityEntity.setActivityAttributes(boundary.getActivityAttributes());

		return activityEntity;
	}

	public ActivityBoundary toBoundary(ActivityEntity entity) {

		GeneralId activityId = new GeneralId();
		activityId.setDomain(entity.getActivityDomain());
		activityId.setId(entity.getActivityId());

		GeneralId instanceId = new GeneralId();
		instanceId.setId(entity.getInstanceId());
		instanceId.setDomain(entity.getInstanceDomain());

		UserId userId = new UserId();
		userId.setEmail(entity.getInvokedUserEmail());
		userId.setDomain(entity.getInvokedUserDomain());

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

	public String getEntityGeneralIdFromDomainAndGeneralId(String domain, String id) {

		return idConverter.getEntityGeneralIdFromDomainAndGeneralId(domain, id);
	}
	
	public String getUserEmailFromUserEntityId(String createdByUserId) {

		return idConverter.getUserEmailFromUserEntityId(createdByUserId);
	}

	public String getUserDomainFromUserEntityId(String createdByUserId) {

		return idConverter.getUserDomainFromUserEntityId(createdByUserId);
	}

	public String getGeneralIdFromEntityGeneralId(String id) {

		return idConverter.getGeneralIdFromEntityGeneralId(id);
	}

	public String getDomainFromEntityGeneralId(String id) {

		return idConverter.getDomainFromEntityGeneralId(id);
	}
	
}
