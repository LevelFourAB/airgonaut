package se.l4.airgonaut.email;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.channels.EmailChannel;

/**
 * Information about a fully rendered e-mail. This is used to provide
 * information that to an {@link EmailBackend} that it uses for delivering
 * the e-mail.
 */
public interface RenderedEmail
{
	/**
	 * Get the {@link EmailChannel} this e-mail is from.
	 *
	 * @return
	 */
	@NonNull
	EmailChannel getFrom();

	/**
	 * Get the {@link EmailChannel}s to send to.
	 *
	 * @return
	 *   list of e-mails to send directly to
	 */
	@NonNull
	List<EmailChannel> getTo();

	/**
	 * Get the {@link EmailChannel}s to CC (carbon copy).
	 *
	 * @return
	 *   list of e-mails to include as CC
	 */
	@NonNull
	List<EmailChannel> getCC();

	/**
	 * Get the title of the e-mail.
	 *
	 * @return
	 *   title of the e-mail, never {@code null}
	 */
	@NonNull
	String getTitle();

	/**
	 * Get the plain text of the e-mail. Plain text is not always available,
	 * but if plain text is not available {@link #getHTML()} will be available.
	 *
	 * @return
	 *   plain text of e-mail, or empty optional if no plain text
	 */
	@NonNull
	Optional<String> getPlainText();

	/**
	 * Get the HTML of the e-mail. HTML is not always available,
	 * but if HTML is not available {@link #getPlainText()} will be available.
	 *
	 * @return
	 *   HTML of e-mail, or empty optional if no HTML
	 */
	@NonNull
	Optional<String> getHTML();

	/**
	 * Get tags specified for e-mail. Tags are provided as a lot of
	 * transactional e-mail providers support specifying tags for tracking
	 * purposes.
	 *
	 * @return
	 *   set with tags
	 */
	@NonNull
	Set<String> getTags();
}
