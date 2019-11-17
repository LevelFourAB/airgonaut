package se.l4.airgonaut.email;

import java.util.List;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.engine.RenderingType;
import se.l4.airgonaut.engine.template.HTMLString;
import se.l4.airgonaut.engine.template.PlainTextString;
import se.l4.airgonaut.engine.template.TemplateEngine;

/**
 * Encounter used when an {@link EmailTemplate} is used to render the final
 * e-mail to be sent.
 */
public interface EmailTemplateEncounter
{
	/**
	 * Get the type of notification being rendered.
	 *
	 * @return
	 *   type being rendered
	 */
	@NonNull
	RenderingType getType();

	/**
	 * Get the entries that email should include.
	 *
	 * @return
	 *   list of rendered entries
	 */
	@NonNull
	List<RenderedEmailNotification> getEntries();

	/**
	 * Set the title of the e-mail to be sent.
	 *
	 * @param title
	 *   the title
	 */
	void setTitle(@NonNull String title);

	/**
	 * Set the plain text of the final e-mail to be sent.
	 *
	 * @param text
	 *   the text
	 */
	void setPlainText(@NonNull String text);

	/**
	 * Set the plain text of the final e-mail to be sent.
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
	 * Set the HTML of the final e-mail to be sent.
	 *
	 * @param html
	 *   string containing the HTML document representing the e-mail
	 */
	void setHTML(@NonNull String html);

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
