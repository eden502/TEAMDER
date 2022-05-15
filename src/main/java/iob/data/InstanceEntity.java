package iob.data;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class InstanceEntity {

	
	private String id;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private String createdByUserId;
	// Changed to support Geo Search with MongoDB (01/05/2022 By Keren)
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	
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
	/*
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

	}*/
	
	public GeoJsonPoint getLocation() {
		return location;
	}


	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}
	
	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}


	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;

	}

}
