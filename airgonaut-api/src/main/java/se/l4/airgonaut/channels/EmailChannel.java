package se.l4.airgonaut.channels;

import java.util.Optional;

/**
 * E-mail address used for contacting a {@link se.l4.airgonaut.NotificationReceiver}.
 */
public interface EmailChannel
	extends ContactChannel
{
	/**
	 * Get the email address. This is the address any notifications would be
	 * sent to.
	 *
	 * @return
	 *   valid e-mail address, never {@code null}
	 */
	String getEmail();

	/**
	 * Get the name if available.
	 *
	 * @return
	 *   optional that is either empty or contains a human readable name
	 */
	Optional<String> getName();

	/**
	 * Create a new instance for the given email.
	 *
	 * @param email
	 *   the e-mail to create for, never {@code null}
	 * @return
	 *   instance
	 */
	static EmailChannel create(String email)
	{
		return new DefaultEmailChannel(email, Optional.empty());
	}

	/**
	 * Create a new instance for the given email.
	 *
	 * @param email
	 *   the e-mail to create for, never {@code null}
	 * @param name
	 *   the name to create for, never {@code null}
	 * @return
	 *   instance
	 */
	static EmailChannel create(String email, String name)
	{
		return new DefaultEmailChannel(email, Optional.of(name));
	}

	/**
	 * Create a new instance for the given email.
	 *
	 * @param email
	 *   the e-mail to create for, never {@code null}
	 * @param name
	 *   optional with the name of receiver
	 * @return
	 *   instance
	 */
	static EmailChannel create(String email, Optional<String> name)
	{
		return new DefaultEmailChannel(email, name);
	}
}
