package se.l4.airgonaut.email;

import se.l4.airgonaut.NotificationData;

/**
 * Renderer responsible for applying a template around the plain text and
 * HTML provided for each individual {@link NotificationData}.
 */
public interface EmailTemplate
{
	/**
	 * Render the template wrapping the supplied {@link RenderedEmailNotification}s.
	 *
	 * @param encounter
	 *   the encounter containing rendering information
	 */
	void render(EmailTemplateEncounter encounter);
}
