package goog.sheet;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheetUtil {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GoogleSheetUtil.class);
	public static String NEW_LAP_LEAD_RANGE = "Landingpage_Leads-Info!A1:Z";
	public static String NEW_PLATFORM_LEAD_RANGE = "Platform_Leads-Info!A1:Z";
	public static String NEW_SIGNUP_RANGE = "Inbound-Signups!A1:Z";
	public static String SVC_EMAIL = "adbuddy@tranquil-buffer-235404.iam.gserviceaccount.com";
//	public static String DEFAULT_ID = "12sKffYdR306j8SwNP81S9hzO4_jT0kBrJpjiJZAk0mM";
	public static String DEFAULT_ID = "1v8wiMlFTnOVz2_j5cnHsDibJX4RG09tPL7HvO-Up7gE";

	public static Sheets createGoogleSvcCredential() throws GeneralSecurityException, IOException {
		List<String> scopeList = new ArrayList<String>();
		scopeList.add(SheetsScopes.DRIVE);
		scopeList.add(SheetsScopes.DRIVE_FILE);
		scopeList.add(SheetsScopes.SPREADSHEETS);
		InputStream in = GoogleSheetUtil.class.getClassLoader()
				.getResourceAsStream("adbuddy-google-service-account.json");
		String path = GoogleSheetUtil.class.getClassLoader().getResource("adbuddy-google-service-account.json")
				.getPath();
		logger.info("Identified service account file {}", path);
		final JacksonFactory defaultInstance = JacksonFactory.getDefaultInstance();
		// GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(defaultInstance,
		// new InputStreamReader(in));

		final NetHttpTransport newTrustedTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = GoogleCredential.fromStream(in, newTrustedTransport, defaultInstance);
		Credential c = credential.createScoped(scopeList);
		Sheets service = new Sheets.Builder(newTrustedTransport, defaultInstance, c)
				.setApplicationName("adbuddy-under-askjuno").build();
		return service;
	}

	/*
	 * public static Credential authorize() throws IOException { // Load client
	 * secrets. InputStream in =
	 * GoogleSheetUtil.class.getResourceAsStream("/credentials.json");
	 * GoogleClientSecrets clientSecrets =
	 * GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new
	 * InputStreamReader(in));
	 * 
	 * // Build flow and trigger user authorization request.
	 * GoogleAuthorizationCodeFlow flow; try { flow = new
	 * GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.
	 * newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets,
	 * Collections.singletonList(SheetsScopes.SPREADSHEETS))
	 * .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(
	 * System.getProperty("user.home"),
	 * ".credentials/sheets.googleapis.com-java-quickstart")))
	 * .setAccessType("offline") .build(); LocalServerReceiver receiver = new
	 * LocalServerReceiver.Builder().setPort(8888).build(); return new
	 * AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	 * 
	 * } catch (GeneralSecurityException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return null;
	 * 
	 * }
	 * 
	 * public static Sheets getSheetsService() throws IOException { Credential
	 * credential = authorize(); try { return new
	 * Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
	 * JacksonFactory.getDefaultInstance(), credential)
	 * .setApplicationName("Testing GSheet API") .build(); } catch
	 * (GeneralSecurityException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return null; }
	 */
	public static List<Object> createGenericSheetRow(PersonInfo lead) {

		final String name = lead.getFirstName() + " " + lead.getLastName();
		final String email = lead.getEmail();
		final String photo = lead.getPhoto();
		final String ipAddress = lead.getIpAddress();
		final String inboundTime = DateUtil.formatDateTime(DateUtil.getNowDate());
		final Boolean isSmsVerified = lead.getIsSmsVerified();
		final String verificationCode = lead.getVerificationCode();
		final String phoneNumber = lead.getPhoneNumber();
		final String alternateNumber = lead.getAlternateNumber();
		final String userFedLocation = lead.getUserFedLocation();
		final String device = lead.getDeviceName();
		final String browser = lead.getDeviceType();

		List<Object> rowList = new ArrayList<Object>();
		rowList.add(lead.getCreatedDate());
		rowList.add(name);
		rowList.add(email);
		rowList.add(photo);
		rowList.add(device);
		rowList.add(ipAddress);
		rowList.add(inboundTime);
		rowList.add(browser);
		rowList.add(isSmsVerified);
		rowList.add(verificationCode);
		rowList.add(phoneNumber);
		rowList.add(alternateNumber);
		rowList.add(userFedLocation);
		if (lead.getLapQuestionMap() != null && lead.getLapQuestionMap().isEmpty()) {
			lead.getLapQuestionMap().forEach((id, question) -> {
				rowList.add(question + "=" + question.getAnswer());
			});
		}
		return rowList;
	}

	public static List<Object> createAccountSheetRow(PersonInfo lead) {
		// First name Last name Mobile Alternate Email Verified Verification code
		// Location status Inbound date
		// Location Status Inbound Date CQ1 TO CQ10
		List<Object> rowList = new ArrayList<Object>();
		
		rowList.add(lead.getFirstName());
		rowList.add(lead.getLastName());
		rowList.add(lead.getPhoneNumber());
		rowList.add(lead.getAlternateNumber());
		rowList.add(lead.getEmail());
		rowList.add(lead.getIsSmsVerified());
		rowList.add(lead.getVerificationCode());
		rowList.add(lead.getUserFedLocation());
		rowList.add("NEW");
		rowList.add(DateUtil.formatDate(lead.getCreatedDate()));
		rowList.add(lead.getSessionTime());

		if (lead.getLapQuestionMap() != null && !lead.getLapQuestionMap().isEmpty()) {
			lead.getLapQuestionMap().forEach((id, question) -> {
				if (question.getQuestion() != null) {
					rowList.add(question.getQuestion() + "=" + question.getAnswer());
				} else {
					rowList.add(question.getAnswer());
				}
			});
		}

		return rowList;
	}

	public static String appendToSheet(Sheets service, String sheetId, List<List<Object>> rowBatch, String range)
			throws IOException {
		ValueRange body = new ValueRange().setValues(rowBatch);
		AppendValuesResponse result = service.spreadsheets().values().append(sheetId, range, body)
				.setValueInputOption("USER_ENTERED").execute();
		logger.info("cells appended {}", result.getUpdates().getUpdatedCells());
		String updatedRange = result.getUpdates().getUpdatedRange();
		return updatedRange;
	}

	/* removed Leads from update. By DS */
	public static void updateToSheet(Sheets service, String sheetId, List<List<Object>> rowBatch1, String updateRange) {
		ValueRange updatedValue = new ValueRange();
		updatedValue.setValues(rowBatch1);

		UpdateValuesResponse updatedResult;
		try {
			updatedResult = service.spreadsheets().values().update(sheetId, updateRange, updatedValue)
					.setValueInputOption("USER_ENTERED").execute();
		} catch (IOException e) {
			logger.error("Error updating sheet for lead", e);
		}
	}

	public static ValueRange getValues(Sheets service, String sheetId, String range) throws IOException {
		ValueRange result = service.spreadsheets().values().get(sheetId, range).execute();
		int numRows = result.getValues() != null ? result.getValues().size() : 0;
		System.out.printf("%d rows retrieved.", numRows);
		return result;
	}

	public static ClearValuesResponse deleteFromSheet(Sheets service, String sheetId, String range) throws IOException {

		ClearValuesRequest requestBody = new ClearValuesRequest();
		ClearValuesResponse result = service.spreadsheets().values().clear(sheetId, range, requestBody).execute();
		return result;

	}

}
