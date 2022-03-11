package bounderies;

import java.util.Map;

public class ActivityBoundary {

	private ActivityId activityId;
	private String type;
	private Instance instance;
	private String createdTimestamp;
	private InvokedBy invokeId;
	private Map<String, Object> activityAttributes;
	
	public ActivityBoundary() {
		
	}

	public ActivityId getActivityId() {
		return activityId;
	}

	public ActivityBoundary setActivityId(ActivityId activityId) {
		this.activityId = activityId;
		return this;
	}

	public String getType() {
		return type;
	}

	public ActivityBoundary setType(String type) {
		this.type = type;
		return this;
	}

	public Instance getInstance() {
		return instance;
	}

	public ActivityBoundary setInstance(Instance instance) {
		this.instance = instance;
		return this;
	}
	
	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public ActivityBoundary setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
		return this;
	}
	
	public InvokedBy getInvokeId() {
		return invokeId;
	}

	public ActivityBoundary setInvokeId(InvokedBy invokeId) {
		this.invokeId = invokeId;
		return this;
	}

	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public ActivityBoundary setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;
		return this;
	}
	
}
