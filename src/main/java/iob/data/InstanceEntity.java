package iob.data;

import java.util.Map;


public class InstanceEntity {
	
	private String instanceDomain;
	private String instanceId;
	private String type;
	private String name;
	private boolean active;
	private String createdTimestamp;
	private String createdByUserDomain;
	private String createdByUserEmail;
	private double locationLat;
	private double locationLng;
	private Map<String, Object> instanceAttributes;
	
	public InstanceEntity() {
		
	}

	public String getInstanceDomain() {
		return instanceDomain;
	}

	public InstanceEntity setInstanceDomain(String instanceDomain) {
		this.instanceDomain = instanceDomain;
		return this;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public InstanceEntity setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public String getType() {
		return type;
	}

	public InstanceEntity setType(String type) {
		this.type = type;
		return this;
	}

	public String getName() {
		return name;
	}

	public InstanceEntity setName(String name) {
		this.name = name;
		return this;
	}

	public boolean isActive() {
		return active;
	}

	public InstanceEntity setActive(boolean active) {
		this.active = active;
		return this;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public InstanceEntity setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
		return this;
	}

	public String getCreatedByUserDomain() {
		return createdByUserDomain;
	}

	public InstanceEntity setCreatedByUserDomain(String createdByUserDomain) {
		this.createdByUserDomain = createdByUserDomain;
		return this;
	}

	public String getCreatedByUserEmail() {
		return createdByUserEmail;
	}

	public InstanceEntity setCreatedByUserEmail(String createdByUserEmail) {
		this.createdByUserEmail = createdByUserEmail;
		return this;
	}

	public double getLocationLat() {
		return locationLat;
	}

	public InstanceEntity setLocationLat(double locationLat) {
		this.locationLat = locationLat;
		return this;
	}

	public double getLocationLng() {
		return locationLng;
	}

	public InstanceEntity setLocationLng(double locationLng) {
		this.locationLng = locationLng;
		return this;
	}

	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public InstanceEntity setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;
		return this;
	}
	
}
