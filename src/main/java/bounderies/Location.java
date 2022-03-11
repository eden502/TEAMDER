package bounderies;

public class Location {

	private float lat;
	private float lng;
	
	private Location() {
		
	}

	public float getLat() {
		return lat;
	}

	public Location setLat(float lat) {
		this.lat = lat;
		return this;
	}

	public float getLng() {
		return lng;
	}

	public Location setLng(float lng) {
		this.lng = lng;
		return this;
	}
	
	
}
