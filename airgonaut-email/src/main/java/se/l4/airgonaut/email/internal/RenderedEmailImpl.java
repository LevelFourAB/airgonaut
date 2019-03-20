package se.l4.airgonaut.email.internal;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.email.RenderedEmail;

/**
 * Implementation of {@link RenderedEmail}.
 */
public class RenderedEmailImpl
	implements RenderedEmail
{
	private final List<EmailChannel> to;
	private final List<EmailChannel> cc;

	private final String title;

	private final Optional<String> plainText;
	private final Optional<String> html;

	private final Set<String> tags;

	public RenderedEmailImpl(
		@Nonnull List<EmailChannel> to,
		@Nonnull List<EmailChannel> cc,
		@Nonnull String title,
		@Nonnull Optional<String> plainText,
		@Nonnull Optional<String> html,
		@Nonnull Set<String> tags
	)
	{
		this.to = Objects.requireNonNull(to);
		this.cc = Objects.requireNonNull(cc);

		this.title = Objects.requireNonNull(title);

		this.plainText = Objects.requireNonNull(plainText);
		this.html = Objects.requireNonNull(html);

		this.tags = Objects.requireNonNull(tags);
	}

	@Override
	public List<EmailChannel> getTo()
	{
		return to;
	}

	@Override
	public List<EmailChannel> getCC()
	{
		return cc;
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
	public Optional<String> getPlainText()
	{
		return plainText;
	}

	@Override
	public Optional<String> getHTML()
	{
		return html;
	}

	@Override
	public Set<String> getTags()
	{
		return tags;
	}

}
