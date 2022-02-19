package goog.contact;

public class ContactCreateResponse {

	
	private Integer incomingContactCounter;
	private Integer addedContactCounter;
	private String createdForAuthority;

	
	public String getCreatedForAuthority() {
		return createdForAuthority;
	}

	public void setCreatedForAuthority(String createdForAuthority) {
		this.createdForAuthority = createdForAuthority;
	}

	public Integer getIncomingContactCounter() {
		return incomingContactCounter;
	}

	public void setIncomingContactCounter(Integer incomingContactCounter) {
		this.incomingContactCounter = incomingContactCounter;
	}

	public Integer getAddedContactCounter() {
		return addedContactCounter;
	}

	public void setAddedContactCounter(Integer addedContactCounter) {
		this.addedContactCounter = addedContactCounter;
	}
	
	

}
