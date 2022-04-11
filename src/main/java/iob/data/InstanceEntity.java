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


import iob.service.dao.AttributesFieldConverter;



@Entity
@Table(name="INSTANCES")
public class InstanceEntity {

	
	private String id;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private String createdByUserId;
	private double locationLat;
	private double locationLng;
	private Map<String, Object> instanceAttributes;

	public InstanceEntity() {

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;

	}
	@Column(name="CREATION_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;

	}

	public String getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;

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
	
	@Convert(converter = AttributesFieldConverter.class)
	@Lob
	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;

	}

}
