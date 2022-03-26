package iob.logic;

import org.springframework.stereotype.Component;

import bounderies.ActivityBoundary;
import bounderies.ActivityId;
import bounderies.Instance;
import bounderies.InstanceId;
import bounderies.InvokedBy;
import bounderies.UserId;
import iob.data.ActivityEntity;

@Component
public class ActivityConverter {

	public ActivityEntity toEntity(ActivityBoundary boundary) {
		
		ActivityEntity activityEntity = new ActivityEntity()
				.setActivityId(boundary.getActivityId().getId())
				.setActivityDomain(boundary.getActivityId().getDomain())
				.setType(boundary.getType())
				.setInstanceDomain(boundary.getInstance().getInstanceId().getDomain())
				.setInstanceId(boundary.getInstance().getInstanceId().getId())
				.setCreatedTimestamp(boundary.getCreatedTimestamp())
				.setInvokedUserDomain(boundary.getInvokedBy().getUserId().getDomain())
				.setInvokedUserEmail(boundary.getInvokedBy().getUserId().getEmail())
				.setActivityAttributes(boundary.getActivityAttributes());
		
		return activityEntity;
	}
	
	public ActivityBoundary toBoundary (ActivityEntity entity) {
		
		ActivityId activityId = new ActivityId()
				.setDomain(entity.getActivityDomain())
				.setId(entity.getActivityId());
		
		Instance instance = new Instance()
				.setInstanceId(new InstanceId().setId(entity.getInstanceId()).setDomain(entity.getInstanceDomain()));
		
		InvokedBy invokedBy = new InvokedBy()
				.setUserId(new UserId().setDomain(entity.getInvokedUserDomain()).setEmail(entity.getInvokedUserEmail()));
		
		ActivityBoundary activityBoundary = new ActivityBoundary()
				.setActivityId(activityId)
				.setType(entity.getType())
				.setInstance(instance)
				.setCreatedTimestamp(entity.getCreatedTimestamp())
				.setInvokedBy(invokedBy)
				.setActivityAttributes(entity.getActivityAttributes());
		
		return activityBoundary;
	}
}
