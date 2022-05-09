package iob.data;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.mapping.Document;

import iob.service.dao.AttributesFieldConverter;
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

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;

	}

	@Convert(converter = AttributesFieldConverter.class)
	@Lob
	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;

	}

}
