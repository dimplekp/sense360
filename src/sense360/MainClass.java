package sense360;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class MainClass {

	LocalTime nightIntervalStart = LocalTime.parse("20:00:00",
			DateTimeFormatter.ofPattern("HH:mm:ss"));
	LocalTime nightIntervalEnd = LocalTime.parse("08:00:00",
			DateTimeFormatter.ofPattern("HH:mm:ss"));

	public static void main(String[] args) {

		List<String> input = readInput();

		DateTimeFormatter dtf = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm:ss");

		Map<Point, UserStayInfo> stayCount = new HashMap<>();

		PriorityQueue<UserStayInfo> queue = new PriorityQueue<>(
				new QueueComparator());

		for (String line : input) {
			try {
				String tokens[] = line.split("\\|");

				// Round off latitude and longitude to 3 decimal places
				// to consider coordinates to 3 decimal places as same
				// coordinates
				double latitude = Double.parseDouble(tokens[0].trim());
				double longitude = Double.parseDouble(tokens[1].trim());

				LocalDateTime arriveTime = LocalDateTime.parse(tokens[2].trim(),
						dtf);
				LocalDateTime departTime = LocalDateTime.parse(tokens[3].trim(),
						dtf);
				if (departTime.isBefore(arriveTime)) {
					System.out.println("[Ignored] " + arriveTime.toString()
							+ " - " + departTime.toString()
							+ " : Incorrect sequence of arrival and departure DATE-TIME");
					continue;
				}

				Point p = new Point(latitude, longitude);

				if (!stayCount.containsKey(p)) {
					stayCount.put(p, new UserStayInfo(p));
				}

				long hours = LocalDateTime.from(arriveTime).until(departTime,
						ChronoUnit.HOURS);

				stayCount.get(p).nightHours += withinIntervalAtNight(arriveTime,
						departTime);
				stayCount.get(p).totalHours += hours;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		Iterator<Entry<Point, UserStayInfo>> it = stayCount.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<Point, UserStayInfo> pair = (Entry<Point, UserStayInfo>) it
					.next();
			queue.add(pair.getValue());
			it.remove();
		}

		System.out.println("=============================");
		if (!queue.isEmpty()) {
			UserStayInfo usi = queue.poll();
			if (usi.totalHours < 30) {
				System.out.println(
						"Home location | latitude : " + usi.point.latitude
								+ ", longitude : " + usi.point.longitude);
			} else {
				System.out.println("Home not found");
			}
		} else {
			System.out.println("Home not found");
		}
	}

	private static long withinIntervalAtNight(LocalDateTime arriveTime,
			LocalDateTime departTime) {
		// simple logic, doesn't cover edge cases

		// if day of arrival and departure is same
		// don't consider it hours in night hours
		if (!arriveTime.toLocalDate().equals(departTime.toLocalDate())) {
			return arriveTime.until(departTime, ChronoUnit.HOURS);
		}
		return 0;
	}

	private static List<String> readInput() {
		List<String> lines = new ArrayList<String>();
		String fileName = "/Users/dimple/Desktop/tests.txt";
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(lines::add);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
