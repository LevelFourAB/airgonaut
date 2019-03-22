package se.l4.airgonaut.email.internal;

import java.util.Objects;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.email.EmailRenderingEncounter;
import se.l4.airgonaut.engine.RenderingType;
import se.l4.airgonaut.engine.template.HTMLString;
import se.l4.airgonaut.engine.template.PlainTextString;
import se.l4.airgonaut.engine.template.TemplateEngine;

/**
 * Implementation of {@link EmailRenderingEncounter}.
 */
public class EmailRenderingEncounterImpl<D extends NotificationData>
	implements EmailRenderingEncounter<D>
{
	private final EmailChannel email;
	private final RenderingType type;
	private final D notification;

	private String title;
	private String plainText;
	private String html;

	public EmailRenderingEncounterImpl(
		EmailChannel email,
		RenderingType type,
		D notification
	)
	{
		this.email = email;
		this.type = type;
		this.notification = notification;
	}

	@Override
	public EmailChannel getEmail()
	{
		return email;
	}

	@Override
	public RenderingType getType()
	{
		return type;
	}

	@Override
	public D getData()
	{
		return notification;
	}

	/**
	 * Get the title that has been requested by the renderer.
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
		Objects.requireNonNull(engine);
		Objects.requireNonNull(data);

		this.html = engine.render(data).getRaw();
	}
}
