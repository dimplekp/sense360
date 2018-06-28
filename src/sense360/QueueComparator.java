package sense360;

import java.util.Comparator;

public class QueueComparator implements Comparator<UserStayInfo> {

	@Override
	public int compare(UserStayInfo s1, UserStayInfo s2) {

		// Point where there are more night hours is prioritized
		// and if they are same, point with greater total hours is prioritized
		return (s2.nightHours > s1.nightHours)
				? ((int) (s2.nightHours - s1.nightHours))
				: ((int) (s2.totalHours - s1.totalHours));
	}

}
