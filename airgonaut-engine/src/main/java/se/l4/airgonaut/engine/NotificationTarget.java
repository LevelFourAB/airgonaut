package se.l4.airgonaut.engine;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.ContactChannel;

/**
 * A target platform/service that a notification can be sent to.
 * Implementations of this controls how notifications are rendered and sent
 * to the receivers. A target may be e-mail, sms, a mobile, a browser or
 * similar devices that can show notifications.
 *
 * @param <Channel>
 *   the type of channel this target can handle
 */
public interface NotificationTarget<Channel extends ContactChannel>
{
	/**
	 * Get the type of renderer used by this target.
	 *
	 * @return
	 *   a class that implements {@link NotificationRenderer}
	 */
	@NonNull
	Class<?> getRendererType();

	/**
	 * Validate if this target supports the given contact channel. At a
	 * minimum a target must check if the channel is assignable from the
	 * generic type {@code <Channel>}.
	 *
	 * @param channel
	 *   the channel to check
	 * @return
	 *   {@code true} if the channel is supported by this target
	 */
	boolean supportsChannel(@NonNull ContactChannel channel);

	/**
	 * Send the notification described by the given encounter. This will
	 * be called with the {@link ContactChannel} and the {@link NotificationData}
	 * that should be sent using the target.
	 */
	void send(@NonNull NotificationTargetEncounter<Channel> encounter);
}
