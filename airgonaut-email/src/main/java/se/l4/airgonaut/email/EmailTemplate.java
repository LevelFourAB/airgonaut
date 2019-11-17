package se.l4.airgonaut.email;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.NotificationData;

/**
 * Renderer responsible for applying a template around the plain text and
 * HTML provided for each individual {@link NotificationData}.
 */
public interface EmailTemplate
{
	/**
	 * Render the final e-mail. This will receive the rendered notifications
	 * and is responsible for putting that content in the overall template,
	 * both for the text and HTML versions of the e-mail. It should also decide
	 * on the title of the final e-mail.
	 *
	 * @param encounter
	 *   the encounter containing rendering information
	 */
	void render(@NonNull EmailTemplateEncounter encounter);
}
