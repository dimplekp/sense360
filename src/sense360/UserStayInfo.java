package sense360;

public class UserStayInfo {
	Point point;
	long totalHours;
	long nightHours;

	public UserStayInfo(Point point) {
		this.point = point;
		totalHours = 0;
		nightHours = 0;
	}
}
