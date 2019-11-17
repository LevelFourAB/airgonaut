package se.l4.airgonaut.email;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.NotificationData;

/**
 * {@link NotificationData} that has been rendered using an
 * {@link EmailRenderer}.
 */
public interface RenderedEmailNotification
{
	/**
	 * Get the proposed title of the e-mail as requested by the email.
	 *
	 * @return
	 *   optional with a title, or empty optional if no proposed title
	 */
	@NonNull
	Optional<String> getProposedTitle();

	/**
	 * Get the plain text of the notification.
	 *
	 * @return
	 *   optional with plain text of the notification, or empty optional
	 */
	@NonNull
	Optional<String> getPlainText();

	/**
	 * Get the HTML of the notification.
	 *
	 * @return
	 *   optional with HTML of the notificatio, or empty optional
	 */
	@NonNull
	Optional<String> getHTML();
}
