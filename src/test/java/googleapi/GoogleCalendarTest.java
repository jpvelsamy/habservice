package googleapi;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.TimeZone;

import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.CalendarList;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

public class GoogleCalendarTest {

	private static final String DEFAULT_TIMEZONE = "Asia/Kolkata";
	private static final String APPLICATION_NAME = "Google People API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	// https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=ya29.A0ARrdaM8K-fdFCevvC8vRtoxWBmCZjmQ0iS6-KDnmGmLk4TPylzJaJEFPqxZM95apGfZMaZO_UVMV3btP5qaBc_pYNJtrxpMFJpk5nKguBehkXXucRE8UExdc8egO-2alSPBTdYHtdLDjnbnZHlsxaCy9liYe
	private final String accessToken = "ya29.A0ARrdaM9Nfnltxrbs5T4xEHxROM6MzkrzRbhAYLYgTpaojWYy2eItEUCX5wRzCw5pADIMG7pd7F2czgZ89odercp60ksK5972MsBwHSRaVxkGgIl8bPLKKNv2ox_AwKI789Cm6hN0kDg09430PIJzsW_QVbxN";

	@Test
	public void testConnectCalendarList() {
		
		NetHttpTransport HTTP_TRANSPORT;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
			Calendar calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,credential).setApplicationName(APPLICATION_NAME).build();
			TimeZone timeZone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);
			CalendarList calendarList = calendar.calendarList();
			com.google.api.services.calendar.model.CalendarList list = calendarList.list().execute();
			list.forEach((k,v)->{
				System.out.println(k);
				System.out.println(v);
			});
			System.out.println(calendarList);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddNewCalendarEvent() {
		NetHttpTransport HTTP_TRANSPORT;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
			Calendar calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,credential).setApplicationName(APPLICATION_NAME).build();
			TimeZone timeZone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);
			String summary = "Google I/O 2015";
			String location = "800 Howard St., San Francisco, CA 94103";
			String description = "A chance to hear more about Google's developer products.";
			Event event = new Event()
				    .setSummary(summary)
				    .setLocation(location)
				    .setDescription(description);

				String startDate = "2022-02-19T15:00:00-07:00";
				DateTime startDateTime = new DateTime(startDate);
				EventDateTime start = new EventDateTime()
				    .setDateTime(startDateTime)
				    .setTimeZone(DEFAULT_TIMEZONE);
				event.setStart(start);

				String endDate = "2022-02-19T15:15:00-07:00";
				DateTime endDateTime = new DateTime(endDate);
				EventDateTime end = new EventDateTime()
				    .setDateTime(endDateTime)
				    .setTimeZone(DEFAULT_TIMEZONE);
				event.setEnd(end);

				String rule = "RRULE:FREQ=DAILY;COUNT=2";
				String[] recurrence = new String[] {rule};
				event.setRecurrence(Arrays.asList(recurrence));

				String bizOwner = "jana.poornavel@gmail.com";
				String lead = "kavi@askjuno.com";
				EventAttendee[] attendees = new EventAttendee[] {
				    new EventAttendee().setEmail(bizOwner),
				    new EventAttendee().setEmail(lead),
				};
				event.setAttendees(Arrays.asList(attendees));

				EventReminder[] reminderOverrides = new EventReminder[] {
				    new EventReminder().setMethod("email").setMinutes(24 * 60),
				    new EventReminder().setMethod("popup").setMinutes(10),
				};
				Event.Reminders reminders = new Event.Reminders()
				    .setUseDefault(false)
				    .setOverrides(Arrays.asList(reminderOverrides));
				event.setReminders(reminders);

				String calendarId = "primary";
				event = calendarService.events().insert(calendarId, event).execute();
				System.out.printf("Event created: %s\n", event.getHtmlLink());

			
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
