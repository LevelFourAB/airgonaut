package se.l4.airgonaut;

/**
 * Exception root for all exceptions thrown when using {@link Notifications}.
 */
public class NotificationException
	extends RuntimeException
{

	private static final long serialVersionUID = 9138426252503811296L;

	public NotificationException(String message)
	{
		super(message);
	}

	public NotificationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
