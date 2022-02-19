package goog.cal;

import java.util.HashMap;
import java.util.Map;

public class CalendarCreateResponse {

	
	private Integer incomingEventCounter;
	private Integer addedContactCounter;
	private String createdForAuthority;
	private Map<String, String> inviteMap = new HashMap<String, String>();

	
	public String getCreatedForAuthority() {
		return createdForAuthority;
	}

	public void setCreatedForAuthority(String createdForAuthority) {
		this.createdForAuthority = createdForAuthority;
	}

	public Integer getIncomingEventCounter() {
		return incomingEventCounter;
	}

	public void setIncomingEventCounter(Integer incomingContactCounter) {
		this.incomingEventCounter = incomingContactCounter;
	}

	public Integer getAddedContactCounter() {
		return addedContactCounter;
	}

	public void setAddedContactCounter(Integer addedContactCounter) {
		this.addedContactCounter = addedContactCounter;
	}

	public void addInvite(String leadEmailId, String htmlLink) {
		this.inviteMap.put(leadEmailId, htmlLink);
		
	}
	
	

}
