package se.l4.airgonaut.email;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.engine.RenderingEncounter;
import se.l4.airgonaut.engine.template.HTMLString;
import se.l4.airgonaut.engine.template.PlainTextString;
import se.l4.airgonaut.engine.template.TemplateEngine;

/**
 * Encounter passed to implementation of {@link EmailRenderingEncounter}
 */
public interface EmailRenderingEncounter<D extends NotificationData>
	extends RenderingEncounter<D>
{
	/**
	 * Get the e-mail this is being sent to.
	 *
	 * @return
	 *   instance of {@link EmailChannel}
	 */
	@NonNull
	EmailChannel getEmail();

	/**
	 * Set a proposed title of the e-mail.
	 *
	 * @param title
	 *   title of the e-mail
	 */
	void setTitle(@NonNull String title);

	/**
	 * Set the plain text copy of this notification.
	 *
	 * @param text
	 *   plain text copy of the notification
	 */
	void setPlainText(@NonNull String text);

	/**
	 * Set the plain text copy of this notification.
	 *
	 * @param engine
	 *   the engine to use for rendering
	 * @param data
	 *   the data to render
	 */
	<TemplateData> void setPlainText(
		@NonNull TemplateEngine<? super TemplateData, PlainTextString> engine,
		@NonNull TemplateData data
	);

	/**
	 * Set the raw HTML of this notification.
	 *
	 * @param rawHTML
	 *   HTML in a raw form for this notification
	 */
	void setHTML(@NonNull String rawHTML);

	/**
	 * Set the HTML by rendering it using a template engine.
	 *
	 * @param engine
	 *   the engine to use for rendering
	 * @param data
	 *   the data to render
	 */
	<TemplateData> void setHTML(
		@NonNull TemplateEngine<? super TemplateData, HTMLString> engine,
		@NonNull TemplateData data
	);
}
