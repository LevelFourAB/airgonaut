package se.l4.airgonaut.email;

import java.io.IOException;

/**
 * Backend for e-mails that does nothing. Useful during development where
 * actual sending of e-mails is not usually desired, but you still want to be
 * able to see that the notification chain works.
 */
public class NoopEmailBackend
	implements EmailBackend
{
	@Override
	public void send(RenderedEmail email)
		throws IOException
	{
		// Does nothing
	}
}
