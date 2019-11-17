package se.l4.airgonaut;

import se.l4.airgonaut.channels.ContactChannel;

/**
 * Builder for new notifications. This interface provides a fluent API for
 * creating different types of notifications and specifying who should recieve
 * these notifications.
 *
 * <p>
 * The smallest notification possible contains a single {@link NotificationData}
 * and targets a {@link NotificationReceiver} or a {@link ContactChannel}:
 *
 * <pre>
 * notifications.newNotification()
 *   .withData(new UnknownDeviceSignIn(deviceInfo))
 *   .to(userThatIsNotificationReceiver)
 *   .send();
 * </pre>
 *
 * <p>
 * Notifications may also target multiple receivers:
 *
 * <pre>
 * notifications.newNotification()
 *   .withData(new UnknownDeviceSignIn(deviceInfo))
 *   .to(userThatIsNotificationReceiver)
 *   .to(EmailChannel.create("security@example.com"))
 *   .send();
 * </pre>
 */
public interface NotificationBuilder
{
	/**
	 * Add some notification data to this builder. It's possible to add
	 * multiple data instances in which case a digest notification will be
	 * created.
	 *
	 * @param data
	 *   data of the notification
	 * @return
	 *   self
	 */
	NotificationBuilder withData(NotificationData data);

	/**
	 * Add a receiver of this notification.
	 *
	 * @param receiver
	 *   receiver that will be asked about channels to send notification
	 * @return
	 *   self
	 */
	NotificationBuilder to(NotificationReceiver receiver);

	/**
	 * Add a receiver via a contact channel. Contact channels will always be
	 * sent the final notification.
	 *
	 * @param channel
	 *   the channel to send to
	 * @return
	 *   self
	 */
	NotificationBuilder to(ContactChannel channel);

	/**
	 * Send this notification to all the receivers.
	 *
	 * @throws NotificationException
	 *   if the notification can not be sent
	 */
	void send();
}
