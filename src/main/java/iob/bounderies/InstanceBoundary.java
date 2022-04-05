package iob.bounderies;

import java.util.Map;

public class InstanceBoundary {

	private GeneralId instanceId;
	private String type;
	private String name;
	private Boolean active;
	private String createdTimestamp;
	private CreatedBy createdBy;
	private Location location;
	private Map<String, Object> instanceAttributes;

	public InstanceBoundary() {

	}

	public GeneralId getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(GeneralId instanceId) {
		this.instanceId = instanceId;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;

	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;

	}

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;

	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;

	}

	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;

	}

}
