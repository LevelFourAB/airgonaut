package se.l4.airgonaut;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.channels.ContactChannel;

/**
 * Receiver of a notification, such as a person or system. Receivers are used
 * to resolve {@link ContactChannel}s and allows for full control over
 * how notifications are received.
 */
public interface NotificationReceiver
{
	/**
	 * Resolve how this receiver would like to get a specific notification.
	 */
	void resolveChannels(@NonNull ChannelResolveEncounter encounter);
}
