package googleapi;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

public class GoogleContactTest {

	private static final String APPLICATION_NAME = "Google People API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	//https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=ya29.A0ARrdaM8K-fdFCevvC8vRtoxWBmCZjmQ0iS6-KDnmGmLk4TPylzJaJEFPqxZM95apGfZMaZO_UVMV3btP5qaBc_pYNJtrxpMFJpk5nKguBehkXXucRE8UExdc8egO-2alSPBTdYHtdLDjnbnZHlsxaCy9liYe
	private final String accessToken = "ya29.A0ARrdaM9Nfnltxrbs5T4xEHxROM6MzkrzRbhAYLYgTpaojWYy2eItEUCX5wRzCw5pADIMG7pd7F2czgZ89odercp60ksK5972MsBwHSRaVxkGgIl8bPLKKNv2ox_AwKI789Cm6hN0kDg09430PIJzsW_QVbxN";
//													 = "ya29.A0ARrdaM__1qOB5_g_zt6LpqzUkicsZVCWyBfP1YC1Z5fNsEvEd-krJ97L6qhPP7VdEIespP08Ia_QM5-gH-8_F7kVL1STkzvUZvokmrDfygymNXEpog_pkitdJ7J354acvS47k74pQ-gBHw8c85jjD307dAgi";

	//https://developers.google.com/api-client-library/java/google-api-java-client/oauth2
	@Test
	public void testConnect() {
		try {
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
			// PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT,
			// JSON_FACTORY, cred).setApplicationName(APPLICATION_NAME).build();
			PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			Person profile = peopleService.people().get("people/me")
				    .setPersonFields("names,emailAddresses")
				    .execute();
			System.out.println(profile);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreate()
	{
		NetHttpTransport HTTP_TRANSPORT;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
			// PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT,
			// JSON_FACTORY, cred).setApplicationName(APPLICATION_NAME).build();
			PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			Person contactToCreate = new Person();
			List<Name> names = new ArrayList<Name>();
			names.add(new Name().setGivenName("Jane").setFamilyName("Moe"));
			contactToCreate.setNames(names);

			Person createdContact = peopleService.people().createContact(contactToCreate).execute();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getCurrentDate()
	{
		Instant instant = Instant.now() ;  // Capture the current moment in UTC.
		ZoneId z = ZoneId.of( "Asia/Kolkata" ) ;
		ZonedDateTime zdt = instant.atZone( z ) ;  // Same moment, different wall-clock time.
		
		int dayOfMonth = zdt.getDayOfMonth();
		int monthValue = zdt.getMonthValue();
		int year = zdt.getYear();
		
		int hour = zdt.getHour();
		int minute = zdt.getMinute();
		int second = zdt.getSecond();
		
		System.out.println("Day="+dayOfMonth);
		System.out.println("Month="+monthValue);
		System.out.println("Year="+year);
		System.out.println("Hour="+hour);
		System.out.println("minute="+minute);
		System.out.println("second="+second);
		
//		LocalDate ld = LocalDate.of( 2018 , Month.JANUARY , 23 ) ;
//		LocalTime lt = LocalTime.of( 14 , 0 ) ;  // 14:00 = 2 PM.
//		ZonedDateTime zdt = ZonedDateTime.of( ld , lt , z ) ;
	}
}
