package goog.cal;

public class CalendarInfo {

	private static final int DEFAULT_FREQUENCY_COUNT = 2;
	private static final String DAILY_INTERVAL = "DAILY";
	private String summary;
	private String location;
	private String description;
	private String startDate;
	private String endDate;
	private String timeInterval=DAILY_INTERVAL;
	private Integer frequency=DEFAULT_FREQUENCY_COUNT;
	private String[] businessAttendees;
	private String leadEmailId;
	private Integer emailNotificationPreNotification=24*60;
	private Integer popUpPreNotification=10;
	private String timeZone;
	
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public String[] getBusinessAttendees() {
		return businessAttendees;
	}
	public void setBusinessOwnerId(String[] businessAttendees) {
		this.businessAttendees = businessAttendees;
	}
	public String getLeadEmailId() {
		return leadEmailId;
	}
	public void setLeadEmailId(String leadEmailId) {
		this.leadEmailId = leadEmailId;
	}
	public Integer getEmailNotificationPreNotification() {
		return emailNotificationPreNotification;
	}
	public void setEmailNotificationPreNotification(Integer emailNotificationPreNotification) {
		this.emailNotificationPreNotification = emailNotificationPreNotification;
	}
	public Integer getPopUpPreNotification() {
		return popUpPreNotification;
	}
	public void setPopUpPreNotification(Integer popUpPreNotification) {
		this.popUpPreNotification = popUpPreNotification;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	
	
}
