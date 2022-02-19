package goog.contact;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import com.google.api.services.people.v1.model.Url;

import core.HabServiceException;

public class CreateContactAction {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CreateContactAction.class);
	private static final String APPLICATION_NAME = "Hab service";
	private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
	private NetHttpTransport httpTransport;
	private String accessToken;
	private String applicationName;
	private PeopleService peopleService;
	private GoogleCredential credential;

	public CreateContactAction(String accessToken, String junoBusinessName) throws HabServiceException {
		this.accessToken = accessToken;
		this.applicationName = junoBusinessName;
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException | IOException e) {
			throw new HabServiceException(e);
		}
		if (httpTransport != null) {
			credential = new GoogleCredential().setAccessToken(this.accessToken);
			peopleService = new PeopleService.Builder(httpTransport, jsonFactory, credential)
					.setApplicationName(APPLICATION_NAME).build();
		} else {
			throw new HabServiceException("transport layer not initialized");
		}

	}

	public ContactCreateResponse execute(Collection<ContactInfo> contactCollection) throws HabServiceException {
		final AtomicInteger incomingContactCounter = new AtomicInteger();
		final AtomicInteger addedContactCounter = new AtomicInteger();
		final ContactCreateResponse response = new ContactCreateResponse();
		response.setCreatedForAuthority(applicationName);

		if (!contactCollection.isEmpty()) {
			contactCollection.forEach(contact -> {
				incomingContactCounter.incrementAndGet();

				final Person person = new Person();
				final String fullName = contact.getFullName();
				final String emaiId = contact.getEmaiId();
				final String location = contact.getLocation();
				final String phoneNumber = contact.getPhoneNumber();
				final String junoUrl = contact.getJunoUrl();

				final ArrayList<PhoneNumber> phoneList = createPhoneNumber(phoneNumber);
				final ArrayList<Name> nameList = createContactName(fullName, location);
				final ArrayList<EmailAddress> emailList = createEMailId(emaiId);
				final ArrayList<Url> sourceList = createSourceUrl(junoUrl);

				person.setPhoneNumbers(phoneList);
				person.setNames(nameList);
				person.setEmailAddresses(emailList);
				person.setUrls(sourceList);

				try {
					Person personResponse = peopleService.people().createContact(person).execute();
					addedContactCounter.incrementAndGet();
				} catch (IOException e) {
					logger.error("Error adding contact to gcontact", e);
				}
			});
			response.setAddedContactCounter(addedContactCounter.get());
			response.setIncomingContactCounter(incomingContactCounter.get());
			return response;
		} else {
			throw new HabServiceException("Inbound collection cannot be empty");
		}

	}

	private ArrayList<Url> createSourceUrl(final String junoUrl) {
		final ArrayList<Url> sourceList = new java.util.ArrayList<Url>();
		final Url source = new Url();
		source.setValue(junoUrl);
		sourceList.add(source);
		return sourceList;
	}

	private ArrayList<EmailAddress> createEMailId(final String emaiId) {
		final ArrayList<EmailAddress> emailList = new java.util.ArrayList<EmailAddress>();
		final EmailAddress personEmail = new EmailAddress();
		personEmail.setDisplayName(emaiId);
		personEmail.setValue(emaiId);
		emailList.add(personEmail);
		return emailList;
	}

	private ArrayList<Name> createContactName(final String fullName, final String location) {
		final ArrayList<Name> nameList = new java.util.ArrayList<Name>();
		final Name personName = new Name();
		personName.setDisplayName(fullName);
		personName.setFamilyName(location);
		personName.setGivenName(fullName);
		nameList.add(personName);
		return nameList;
	}

	private ArrayList<PhoneNumber> createPhoneNumber(final String phoneNumber) {
		final ArrayList<PhoneNumber> phoneList = new java.util.ArrayList<PhoneNumber>();
		final PhoneNumber phone = new PhoneNumber();
		phone.setValue(phoneNumber);
		phoneList.add(phone);
		return phoneList;
	}

}
