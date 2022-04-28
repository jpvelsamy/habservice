package goog.sheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import core.HabServiceException;

public class NewSignUpAction {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NewSignUpAction.class);

	private final String sheetId;
	private final String range;

	public NewSignUpAction(String sheetId, String range) {
		super();
		this.sheetId = sheetId;
		this.range = range;
	}

	public void execute(PersonInfo lead) throws HabServiceException {

		List<Object> businessObject = GoogleSheetUtil.createAccountSheetRow(lead);
		List<List<Object>> rowBatch = new ArrayList<List<Object>>();
		rowBatch.add(businessObject);

		ValueRange body = new ValueRange().setValues(rowBatch);
		AppendValuesResponse result;
		try {
			Sheets service = GoogleSheetUtil.createGoogleSvcCredential();
			result = service.spreadsheets().values().append(sheetId, GoogleSheetUtil.NEW_SIGNUP_RANGE, body)
					.setValueInputOption("USER_ENTERED").execute();
			logger.info("cells appended {}", result.getUpdates().getUpdatedCells());
		} catch (IOException | GeneralSecurityException e) {
			logger.error("Error writing to account sheet {} for lead {}", sheetId, lead.getEmail(), e);
			throw new HabServiceException(e);
		}

	}

}
