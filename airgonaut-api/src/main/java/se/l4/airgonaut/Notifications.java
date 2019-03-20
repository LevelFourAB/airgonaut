package se.l4.airgonaut;

/**
 * Send notifications to people.
 */
public interface Notifications
{
	/**
	 * Start creating a new notification that should be sent.
	 *
	 * @return
	 *   builder used to add data and receivers of the notification
	 */
	NotificationBuilder newNotification();
}
