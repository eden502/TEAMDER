package iob.data;

import java.util.Date;
import java.util.Map;
import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ActivityEntity {

	private String id;
	private String type;
	private String instanceId;
	private Date createdTimestamp;
	private String invokedUserId;
	private Map<String, Object> activityAttributes;

	public ActivityEntity() {

	}


	public String getInvokedUserId() {
		return invokedUserId;
	}


	public void setInvokedUserId(String invokedUserId) {
		this.invokedUserId = invokedUserId;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;

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

	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;

	}

}
