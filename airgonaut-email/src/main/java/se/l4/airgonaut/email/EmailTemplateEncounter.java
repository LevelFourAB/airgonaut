package se.l4.airgonaut.email;

import java.util.List;

import javax.annotation.Nonnull;

import se.l4.airgonaut.engine.RenderingType;

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
	@Nonnull
	RenderingType getType();

	/**
	 * Get the entries that email should include.
	 *
	 * @return
	 *   list of rendered entries
	 */
	@Nonnull
	List<RenderedEmailNotification> getEntries();

	/**
	 * Set the title of the e-mail to be sent.
	 *
	 * @param title
	 *   the title
	 */
	void setTitle(@Nonnull String title);

	/**
	 * Set the plain text of the final e-mail to be sent.
	 *
	 * @param text
	 *   the text
	 */
	void setPlainText(@Nonnull String text);

	/**
	 * Set the HTML of the final e-mail to be sent.
	 */
	void setHTML(@Nonnull String html);
}
