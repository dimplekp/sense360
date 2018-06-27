package sense360;

import java.util.Comparator;

// Comparator logic : https://stackoverflow.com/a/21790745/4116052

/**
 * Compares distance between two points
 */
public class DistanceComparator implements Comparator<Point> {

	Point me;

	public DistanceComparator(Point me) {
		this.me = me;
	}

	public DistanceComparator() {
	}

	private Double distanceFromMe(Point p) {
		double theta = p.longitude - me.longitude;
		double dist = Math.sin(deg2rad(p.latitude))
				* Math.sin(deg2rad(me.latitude))
				+ Math.cos(deg2rad(p.latitude)) * Math.cos(deg2rad(me.latitude))
						* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		return dist;
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	@Override
	public int compare(Point p1, Point p2) {
		return distanceFromMe(p1).compareTo(distanceFromMe(p2));
	}

}
