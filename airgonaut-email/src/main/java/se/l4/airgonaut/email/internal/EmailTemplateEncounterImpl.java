package se.l4.airgonaut.email.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.email.EmailTemplateEncounter;
import se.l4.airgonaut.email.RenderedEmailNotification;
import se.l4.airgonaut.engine.RenderingType;
import se.l4.airgonaut.engine.template.HTMLString;
import se.l4.airgonaut.engine.template.PlainTextString;
import se.l4.airgonaut.engine.template.TemplateEngine;

/**
 * Implementation of {@link EmailTemplateEncounter}.
 */
public class EmailTemplateEncounterImpl
	implements EmailTemplateEncounter
{
	private final RenderingType type;
	private final List<RenderedEmailNotification> entries;

	private String title;
	private String plainText;
	private String html;

	public EmailTemplateEncounterImpl(
		@NonNull RenderingType type
	)
	{
		this.type = type;
		entries = new ArrayList<>();
	}

	@Override
	public RenderingType getType()
	{
		return type;
	}

	public void push(String title, String plainText, String html)
	{
		entries.add(new RenderedEmailNotificationImpl(title, plainText, html));
	}

	@Override
	public List<RenderedEmailNotification> getEntries()
	{
		return entries;
	}

	/**
	 * Get the title that the e-mail should have.
	 */
	public String getTitle()
	{
		return title;
	}

	@Override
	public void setTitle(String title)
	{
		Objects.requireNonNull(title);

		this.title = title;
	}

	/**
	 * Get the plain text that was previously set by the renderer via
	 * {@link #setPlainText(String)}.
	 */
	public String getPlainText()
	{
		return plainText;
	}

	@Override
	public void setPlainText(String text)
	{
		Objects.requireNonNull(text);

		this.plainText = text;
	}

	@Override
	public <TemplateData> void setPlainText(TemplateEngine<? super TemplateData, PlainTextString> engine, TemplateData data)
	{
		Objects.requireNonNull(engine)	;
		Objects.requireNonNull(data);

		this.html = engine.render(data).getText();
	}

	/**
	 * Get the HTML that was previously set by the renderer via
	 * {@link #setHTML(String)}.
	 */
	public String getHTML()
	{
		return html;
	}

	@Override
	public void setHTML(String rawHTML)
	{
		Objects.requireNonNull(rawHTML);

		this.html = rawHTML;
	}

	@Override
	public <TemplateData> void setHTML(TemplateEngine<? super TemplateData, HTMLString> engine, TemplateData data)
	{
		Objects.requireNonNull(engine)	;
		Objects.requireNonNull(data);

		this.html = engine.render(data).getRaw();
	}

	private static class RenderedEmailNotificationImpl
		implements RenderedEmailNotification
	{
		private final Optional<String> title;
		private final Optional<String> plainText;
		private final Optional<String> html;

		public RenderedEmailNotificationImpl(
			String title,
			String plainText,
			String html
		)
		{
			this.title = Optional.ofNullable(title);
			this.plainText = Optional.ofNullable(plainText);
			this.html = Optional.ofNullable(html);
		}

		@Override
		public Optional<String> getProposedTitle()
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

	}
}
