package goog.cal;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import core.HabServiceException;

public class CalendarCreateAction {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CalendarCreateAction.class);
	private static final String COUNT = "COUNT";
	private static final String RRULE_FREQ = "RRULE:FREQ";
	private static final String SEMI_COLON = ";";
	private static final String EQUALS = "=";
	private static final String APPLICATION_NAME = "Hab service";
	private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
	private NetHttpTransport httpTransport;
	private String accessToken;
	private String applicationName = APPLICATION_NAME;

	private GoogleCredential credential;
	private Calendar calendarService;

	public CalendarCreateAction(String accessToken, String junoBusinessName) throws HabServiceException {
		this.accessToken = accessToken;
		this.applicationName = junoBusinessName;
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException | IOException e) {
			throw new HabServiceException(e);
		}
		if (httpTransport != null) {
			credential = new GoogleCredential().setAccessToken(accessToken);
			calendarService = new Calendar.Builder(httpTransport, jsonFactory, credential)
					.setApplicationName(applicationName).build();
		} else {
			throw new HabServiceException("transport layer not initialized");
		}

	}

	public CalendarCreateResponse execute(Collection<CalendarInfo> eventSet) throws HabServiceException {
		final CalendarCreateResponse response = new CalendarCreateResponse();
		final AtomicInteger incomingEventCounter = new AtomicInteger();
		final AtomicInteger addedContactCounter = new AtomicInteger(eventSet.size());
		eventSet.forEach(jEvent -> {
			incomingEventCounter.incrementAndGet();
			Event event = new Event();
			event.setSummary(jEvent.getSummary());
			event.setLocation(jEvent.getLocation());
			event.setDescription(jEvent.getDescription());

			addStartDate(jEvent, event);

			addEndDate(jEvent, event);

			createRecurralRule(jEvent, event);

			addAttendees(jEvent);

			addReminderRule(jEvent, event);

			String calendarId = "primary";
			try {
				event = calendarService.events().insert(calendarId, event).execute();
				final String htmlLink = event.getHtmlLink();
				response.addInvite(jEvent.getLeadEmailId(), htmlLink);
			} catch (IOException e) {
				logger.error("Error adding event for contact "+jEvent.getLeadEmailId(),e);
				addedContactCounter.decrementAndGet();
			}
		});
		return response;
	}

	private void addEndDate(CalendarInfo jEvent, Event event) {
		String endDate = jEvent.getEndDate();
		EventDateTime end = createEventDate(jEvent, event, endDate);
		event.setEnd(end);
	}

	private void addStartDate(CalendarInfo jEvent, Event event) {
		String startDate = jEvent.getStartDate();
		EventDateTime start = createEventDate(jEvent, event, startDate);
		event.setStart(start);
	}

	private void addReminderRule(CalendarInfo jEvent, Event event) {
		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email")
						.setMinutes(jEvent.getEmailNotificationPreNotification() * 60),
				new EventReminder().setMethod("popup").setMinutes(jEvent.getPopUpPreNotification())};
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);
	}

	private void addAttendees(CalendarInfo jEvent) {
		String[] bizAttArr = jEvent.getBusinessAttendees();
		EventAttendee[] attendees = new EventAttendee[bizAttArr.length + 1];
		for (int i = 0; i < bizAttArr.length; i++) {
			attendees[i] = new EventAttendee();
			attendees[i].setEmail(bizAttArr[i]);
		}
		attendees[bizAttArr.length] = new EventAttendee().setEmail(jEvent.getLeadEmailId());
	}

	private void createRecurralRule(CalendarInfo jEvent, Event event) {
		String rule = RRULE_FREQ + EQUALS + jEvent.getTimeInterval() + SEMI_COLON + COUNT + EQUALS
				+ jEvent.getFrequency();
		String[] recurrence = new String[] { rule };
		event.setRecurrence(Arrays.asList(recurrence));
	}

	private EventDateTime createEventDate(CalendarInfo jEvent, Event event, String startDate) {
		DateTime startDateTime = new DateTime(startDate);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone(jEvent.getTimeZone());
		return start;
	}

}
