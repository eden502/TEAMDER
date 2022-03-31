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

	public void setInstanceDomain(String instanceDomain) {
		this.instanceDomain = instanceDomain;
		
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
		
	}

	public String getCreatedByUserDomain() {
		return createdByUserDomain;
	}

	public void setCreatedByUserDomain(String createdByUserDomain) {
		this.createdByUserDomain = createdByUserDomain;
		
	}

	public String getCreatedByUserEmail() {
		return createdByUserEmail;
	}

	public void setCreatedByUserEmail(String createdByUserEmail) {
		this.createdByUserEmail = createdByUserEmail;
		
	}

	public double getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(double locationLat) {
		this.locationLat = locationLat;
		
	}

	public double getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(double locationLng) {
		this.locationLng = locationLng;
		
	}

	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;
		
	}
	
}
