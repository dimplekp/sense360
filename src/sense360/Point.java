package sense360;

public class Point {
	Double latitude;
	Double longitude;

	public Point(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		return 31 * latitude.hashCode() + longitude.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point))
			return false;

		Point p = (Point) obj;
		return latitude.equals(p.latitude) && longitude.equals(p.longitude);
	}

}
