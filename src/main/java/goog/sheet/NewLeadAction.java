package goog.sheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.google.api.services.sheets.v4.Sheets;

import core.HabServiceException;

public class NewLeadAction {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NewLeadAction.class);
	private final String sheetId;
	private final String range;

	public NewLeadAction(String sheetId, String range) {
		super();
		this.sheetId = sheetId;
		this.range = range;
	}

	public String execute(PersonInfo lead) throws HabServiceException {
		try {
			Sheets service = GoogleSheetUtil.createGoogleSvcCredential();
			List<Object> businessObject = GoogleSheetUtil.createAccountSheetRow(lead);
			List<List<Object>> rowBatch = new ArrayList<List<Object>>();
			rowBatch.add(businessObject);
			try {
				logger.info("Writing a new lead to sheet {} for lead id {}", sheetId, lead.getId());
				String updatedRange = GoogleSheetUtil.appendToSheet(service, sheetId, rowBatch, range);
				return updatedRange;
			} catch (IOException e) {
				logger.error("Error writing to account sheet {} for lead {}", sheetId, lead.getEmail(), e);
				throw new HabServiceException(e);
			}
		} catch (GeneralSecurityException | IOException e) {
			throw new HabServiceException(e);
		}
	}

}
