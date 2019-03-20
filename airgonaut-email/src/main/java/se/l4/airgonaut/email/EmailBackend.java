package se.l4.airgonaut.email;

import java.io.IOException;

import se.l4.airgonaut.NotificationData;

/**
 * Backend that sends an e-mail. {@link EmailTarget} uses a backend to send
 * e-mails to end users.
 */
public interface EmailBackend
{
	/**
	 * Send the specified e-mail using this backend. This method is called
	 * when all the {@link NotificationData} has been rendered, the e-mail
	 * template applied and extra data has been created.
	 *
	 * <p>
	 * Implementations of this method should throw an exception it the delivery
	 * fails.
	 *
	 * @param email
	 *   the e-mail to send
	 * @throws IOException
	 *   if any I/O error occurs while sending the e-mail
	 */
	void send(RenderedEmail email)
		throws IOException;
}
