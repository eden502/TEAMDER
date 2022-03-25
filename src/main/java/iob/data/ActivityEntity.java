package iob.data;

import java.util.Map;

public class ActivityEntity {
	
	private String activityDomain;
	private String activityId;
	private String type;
	private String instanceDomain;
	private String instanceId;
	private String createdTimestamp;
	private String invokedUserDomain;
	private String invokedUserEmail;
	private Map<String, Object> activityAttributes;
	
	public ActivityEntity() {
		
	}

	public String getActivityDomain() {
		return activityDomain;
	}

	public ActivityEntity setActivityDomain(String activityDomain) {
		this.activityDomain = activityDomain;
		return this;
	}

	public String getActivityId() {
		return activityId;
	}

	public ActivityEntity setActivityId(String activityId) {
		this.activityId = activityId;
		return this;
	}

	public String getType() {
		return type;
	}

	public ActivityEntity setType(String type) {
		this.type = type;
		return this;
	}

	public String getInstanceDomain() {
		return instanceDomain;
	}

	public ActivityEntity setInstanceDomain(String instanceDomain) {
		this.instanceDomain = instanceDomain;
		return this;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public ActivityEntity setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public ActivityEntity setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
		return this;
	}

	public String getInvokedUserDomain() {
		return invokedUserDomain;
	}

	public ActivityEntity setInvokedUserDomain(String invokedUserDomain) {
		this.invokedUserDomain = invokedUserDomain;
		return this;
	}

	public String getInvokedUserEmail() {
		return invokedUserEmail;
	}

	public ActivityEntity setInvokedUserEmail(String invokedUserEmail) {
		this.invokedUserEmail = invokedUserEmail;
		return this;
	}

	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public ActivityEntity setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;
		return this;
	}
	
	
}
