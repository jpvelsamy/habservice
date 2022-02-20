package goog.contact;

import java.io.Serializable;

public class GoogleContactInfo implements Serializable{
	
	
	private static final long serialVersionUID = 4833051882565784182L;
	private String fullName;
	private String emaiId;
	private String phoneNumber;
	private String alternateNumber;
	private String junoUrl;
	private String location;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmaiId() {
		return emaiId;
	}
	public void setEmaiId(String emaiId) {
		this.emaiId = emaiId;
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
	public String getJunoUrl() {
		return junoUrl;
	}
	public void setJunoUrl(String junoUrl) {
		this.junoUrl = junoUrl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
		
}
