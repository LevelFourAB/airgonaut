package se.l4.airgonaut.engine;

import se.l4.airgonaut.NotificationException;

/**
 * Exception that is raised if a notification can not be rendered.
 */
public class NotificationRenderException
	extends NotificationException
{
	private static final long serialVersionUID = -3749123269623027770L;

	public NotificationRenderException(String message)
	{
		super(message);
	}

	public NotificationRenderException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
