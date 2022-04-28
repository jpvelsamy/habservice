package clicksend.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.clicksend.ClickSendClient;
import com.clicksend.models.SmsMessage;
import com.clicksend.models.SmsMessageCollection;
import com.fasterxml.jackson.core.JsonProcessingException;

import core.HabServiceException;

public class ClickSendSmsProvider {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ClickSendSmsProvider.class);
	private String userName;
	private String key;

	public ClickSendSmsProvider() {

	}

	public void execute(String countryCode, String targetMobile, String message) throws HabServiceException {
		if (StringUtils.isAllBlank(countryCode, targetMobile))
			return;
		ClickSendClient c = new ClickSendClient(this.userName, this.key);
		SmsMessageCollection smsMessages = new SmsMessageCollection();
		List<SmsMessage> smsList = new ArrayList<SmsMessage>();
		SmsMessage msg = new SmsMessage();
		msg.setBody(message);
		msg.setCountry(countryCode);
		msg.setTo(targetMobile);
		smsList.add(msg);
		smsMessages.setMessages(smsList);
		try {
			logger.info("Sending sms to {} with content {}", targetMobile, message);
			c.getSMS().sendSms(smsMessages);
		} catch (JsonProcessingException e) {
			logger.info("Error Sending sms to {} with content {}", targetMobile, message, e);
		} catch (Throwable e) {
			logger.info("Error Sending sms to {} with content {}", targetMobile, message, e);
		}

	}

}
