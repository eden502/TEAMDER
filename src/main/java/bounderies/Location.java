package bounderies;

public class Location {

	private double lat;
	private double lng;
	
	public Location() {
		
	}

	public double getLat() {
		return lat;
	}

	public Location setLat(double lat) {
		this.lat = lat;
		return this;
	}

	public double getLng() {
		return lng;
	}

	public Location setLng(double lng) {
		this.lng = lng;
		return this;
	}
	
	
}
