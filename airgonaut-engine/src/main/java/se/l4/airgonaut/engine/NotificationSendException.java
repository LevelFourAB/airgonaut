package se.l4.airgonaut.engine;

import se.l4.airgonaut.NotificationException;

/**
 * Exception thrown when a notification can not be sent by a
 * {@link NotificationTarget}. This exception is usually caused by an I/O
 * error while sending the notification.
 */
public class NotificationSendException
	extends NotificationException
{
	private static final long serialVersionUID = -5572759180462054261L;

	public NotificationSendException(String message)
	{
		super(message);
	}

	public NotificationSendException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
