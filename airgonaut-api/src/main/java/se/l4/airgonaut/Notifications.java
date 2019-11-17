package se.l4.airgonaut;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Send notifications to people. This interface is the main entry for services
 * that send notifications. Call {@link #newNotification()} to start building
 * a notification:
 *
 * <pre>
 * notifications.newNotification()
 *   .withData(new UnknownDeviceSignIn(deviceInfo))
 *   .to(userThatIsNotificationReceiver)
 *   .send();
 * </pre>
 *
 * The most common implementation of this interface is available in the engine
 * part of the library as {@code LocalNotifications}.
 */
public interface Notifications
{
	/**
	 * Start creating a new notification that should be sent.
	 *
	 * @return
	 *   builder used to add data and receivers of the notification
	 */
	@NonNull
	NotificationBuilder newNotification();
}
