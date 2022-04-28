package goog.sheet;

import java.util.Date;
import java.util.Map;

public class PersonInfo {
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String firstName;
	private String lastName;
	private String email;
	private  String photo;
	private String ipAddress;
	private String inboundTime;
	private Boolean isSmsVerified;
	private String verificationCode;
	private String phoneNumber;
	private String alternateNumber;
	private String userFedLocation;
	private String deviceName;
	private String deviceType;
	private Integer sessionTime;	
	private Date createdDate;
	
	private Map<String, Question> lapQuestionMap;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getInboundTime() {
		return inboundTime;
	}
	public void setInboundTime(String inboundTime) {
		this.inboundTime = inboundTime;
	}
	public Boolean getIsSmsVerified() {
		return isSmsVerified;
	}
	public void setIsSmsVerified(Boolean isSmsVerified) {
		this.isSmsVerified = isSmsVerified;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAlternateNumber() {
		return alternateNumber;
	}
	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = alternateNumber;
	}
	public String getUserFedLocation() {
		return userFedLocation;
	}
	public void setUserFedLocation(String userFedLocation) {
		this.userFedLocation = userFedLocation;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Map<String, Question> getLapQuestionMap() {
		return lapQuestionMap;
	}
	public void setLapQuestionMap(Map<String, Question> lapQuestionMap) {
		this.lapQuestionMap = lapQuestionMap;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(Integer sessionTime) {
		this.sessionTime = sessionTime;
	}
	
	
	
	
	
}
