package sendgrid.email;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import core.HabServiceException;

import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EMailSender
{

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EMailSender.class);
	private String asUser;
	private final SendGrid sg;

	public EMailSender(String sendGridKey, String asUser)
	{
		this.sg = new SendGrid(sendGridKey);
		this.asUser = asUser;
	}

	public void execute(String subject, String message, String emailId) throws HabServiceException
	{
		Request request = new Request();
		com.sendgrid.helpers.mail.objects.Email from = new com.sendgrid.helpers.mail.objects.Email(asUser);
		com.sendgrid.helpers.mail.objects.Email to = new com.sendgrid.helpers.mail.objects.Email(emailId);
		com.sendgrid.helpers.mail.objects.Content content = new com.sendgrid.helpers.mail.objects.Content("text/html", message);
		com.sendgrid.helpers.mail.Mail mail = new com.sendgrid.helpers.mail.Mail(from, subject, to, content);
		logger.info("Sending email with contents subject={}, emailid={}, from={}", subject, emailId, message);
		request.setMethod(Method.POST);
		// file:///home/jpvel/Workspace/jam.gamma/.metadata/.plugins/org.eclipse.jdt.ui/jdt-images/14.png
		request.setEndpoint("mail/send");
		try
		{
			request.setBody(mail.build());
			Response response = sg.api(request);
			logger.info("Email sending response {}", response.getBody());
		} catch (IOException e)
		{
			logger.error("Something went wrong when system tried to send email for message{}, with subject{} for email id {}", message, subject, emailId, e);
			throw new HabServiceException(e);
		}

	}

	public void execute(String subject, String message, String[] emailId)
	{
		for (int i = 0; i < emailId.length; i++)
		{
			Request request = new Request();
			Email from = new Email(asUser);
			Email to = new Email(emailId[i]);
			Content content = new Content("text/html", message);
			Mail mail = new Mail(from, subject, to, content);
			logger.info("Sending email with contents subject={}, emailid={}, from={}", subject, emailId, message);
			request.setMethod(Method.POST);
			// file:///home/jpvel/Workspace/jam.gamma/.metadata/.plugins/org.eclipse.jdt.ui/jdt-images/14.png
			request.setEndpoint("mail/send");
			try
			{
				request.setBody(mail.build());
				Response response = sg.api(request);
				logger.info("Email sending response {}", response.getBody());
			} catch (IOException e)
			{
				logger.error("Something went wrong when system tried to send email for message{}, with subject{} for email id {}", message, subject, emailId,
						e);
			}
		}

	}
}
