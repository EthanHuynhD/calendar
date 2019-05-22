/**
* MyCalendar
* @EthanHuynh
* @1.0 5/4/19
*/


import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A calendar tester that runs and call methods from the MyCalendar, Event, and
 * TimeInterval classes.
 */
public class SimpleCalendar {
	public static void main(String[]args) {
		File file = new File("events.txt");
		try {
			DataModel model = new DataModel(file);
			MainFrame main = new MainFrame(model);
		} catch (FileNotFoundException x) {
			DataModel model = new DataModel();
			MainFrame main = new MainFrame(model);
		} finally {
			System.out.println("Done");
		}
	}
}