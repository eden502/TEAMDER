package iob.data;

import java.util.Date;
import java.util.Map;

public class ActivityEntity {

	private String activityDomain;
	private String activityId;
	private String type;
	private String instanceDomain;
	private String instanceId;
	private Date createdTimestamp;
	private String invokedUserDomain;
	private String invokedUserEmail;
	private Map<String, Object> activityAttributes;

	public ActivityEntity() {

	}

	public String getActivityDomain() {
		return activityDomain;
	}

	public void setActivityDomain(String activityDomain) {
		this.activityDomain = activityDomain;

	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;

	}

	public String getInstanceDomain() {
		return instanceDomain;
	}

	public void setInstanceDomain(String instanceDomain) {
		this.instanceDomain = instanceDomain;

	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;

	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;

	}

	public String getInvokedUserDomain() {
		return invokedUserDomain;
	}

	public void setInvokedUserDomain(String invokedUserDomain) {
		this.invokedUserDomain = invokedUserDomain;

	}

	public String getInvokedUserEmail() {
		return invokedUserEmail;
	}

	public void setInvokedUserEmail(String invokedUserEmail) {
		this.invokedUserEmail = invokedUserEmail;

	}

	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;

	}

}
