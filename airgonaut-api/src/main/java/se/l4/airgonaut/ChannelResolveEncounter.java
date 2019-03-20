package se.l4.airgonaut;

import java.util.Set;
import java.util.function.Predicate;

import se.l4.airgonaut.channels.ContactChannel;

/**
 * Encounter used when determining if certain {@link NotificationData} should
 * be delivered to a {@link ContactChannel}. The encounter is built in way so
 * that a {@link NotificationReceiver} can determine exactly which channels
 * a notification is delivered to.
 */
public interface ChannelResolveEncounter
{
	/**
	 * Get the notification data being sent.
	 *
	 * @return
	 *   notification data to be sent
	 */
	NotificationData getNotificationData();

	/**
	 * Get the channels that so far has been activated.
	 */
	Set<ContactChannel> getActiveChannels();

	/**
	 * Get the channels that have been activated for the given type.
	 *
	 * @param type
	 *   the type of channel to look for
	 * @return
	 *   set containing the active channels
	 */
	Set<ContactChannel> getActiveChannels(Class<? extends ContactChannel> type);

	/**
	 * Get the channels that match the given predicate.
	 *
	 * @param predicate
	 *   the predicate to match against
	 * @return
	 *   set containing the active channels
	 */
	Set<ContactChannel> getActiveChannels(Predicate<ContactChannel> predicate);

	/**
	 * Get if any channel has been activated for this receiver.
	 *
	 * @return
	 *   {@code true} if any channel has been activated
	 */
	boolean hasActiveChannel();

	/**
	 * Get if a channel of the given type has been activated for this receiver.
	 * Commonly used to for example send the notification to only the primary
	 * e-mail address.
	 *
	 * @param type
	 *   the type of channel to look for
	 * @return
	 *   {@code true} if any channel of the given type has been activated
	 */
	boolean hasActiveChannel(Class<? extends ContactChannel> type);

	/**
	 * Get if a channel is active by matching against the given predicate.
	 *
	 * @param predicate
	 *   predicate to match against
	 * @return
	 *   {@code true} if a channel matching given the predicate is active
	 */
	boolean hasActiveChannel(Predicate<ContactChannel> predicate);

	/**
	 * Activate this channel and request that the notification is delivered
	 * right away.
	 */
	void activate(ContactChannel channel);
}
