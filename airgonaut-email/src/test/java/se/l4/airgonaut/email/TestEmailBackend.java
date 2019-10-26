package se.l4.airgonaut.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * E-mail backend used for testing.
 */
public class TestEmailBackend
	implements EmailBackend
{
	private final List<RenderedEmail> emails;

	public TestEmailBackend()
	{
		emails = new ArrayList<>();
	}

	@Override
	public void send(RenderedEmail email)
		throws IOException
	{
		emails.add(email);
	}

	public List<RenderedEmail> getEmails()
	{
		return emails;
	}

	public Optional<RenderedEmail> firstEmail()
	{
		return emails.isEmpty() ? Optional.empty() : Optional.of(emails.get(0));
	}
}
