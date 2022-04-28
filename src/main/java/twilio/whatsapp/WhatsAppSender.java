package twilio.whatsapp;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.LoggerFactory;

public class WhatsAppSender {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WhatsAppSender.class);
	private String accountId;
	private String authToken;
	
	public WhatsAppSender(String accountId, String authToken) {
		this.accountId = accountId;
		this.authToken = authToken;
	}

	public void execute(String body, String phoneNumber) {
		Twilio.init(accountId, authToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+"+phoneNumber),
                "",
                body)
            .create();
        logger.info(message.getSid());
	}
}
