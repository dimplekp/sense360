package sense360;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainClass {

	LocalTime nightIntervalStart = LocalTime.parse("20:00:00",
			DateTimeFormatter.ofPattern("HH:mm:ss"));
	LocalTime nightIntervalEnd = LocalTime.parse("08:00:00",
			DateTimeFormatter.ofPattern("HH:mm:ss"));

	public MainClass() {

		List<String> input = readInput();

		DateTimeFormatter dtf = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm:ss");

		// TreeMap sorted by distance between points
		Map<Point, UserStayInfo> stayCount = new HashMap<>();

		for (String line : input) {
			String tokens[] = line.split("|");

			// Rounds off latitude and longitude to 3 decimal places
			// to consider coordinates to 3 decimal places as same coordinates
			double latitude = Math.round(
					Double.parseDouble((tokens[0].trim())) * 1000d) / 1000d;
			double longitude = Math.round(
					Double.parseDouble((tokens[1].trim())) * 1000d) / 1000d;

			LocalDateTime arriveTime = LocalDateTime.parse(tokens[2].trim(),
					dtf);
			LocalDateTime departTime = LocalDateTime.parse(tokens[3].trim(),
					dtf);

			Point p = new Point(latitude, longitude);

			if (!stayCount.containsKey(p)) {
				stayCount.put(p, new UserStayInfo());
			}

			long hours = LocalDateTime.from(arriveTime).until(departTime,
					ChronoUnit.HOURS);

			stayCount.get(p).nightHours += withinIntervalAtNight(arriveTime,
					departTime);
			stayCount.get(p).totalHours += hours;
		}
	}

	private long withinIntervalAtNight(LocalDateTime arriveTime,
			LocalDateTime departTime) {
		if (!arriveTime.toLocalDate().equals(departTime.toLocalDate())) {
			return arriveTime.until(departTime, ChronoUnit.HOURS);
		}
		return 0;
	}

	private List<String> readInput() {

		return null;
	}
}
