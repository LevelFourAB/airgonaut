package se.l4.airgonaut.engine;

import se.l4.airgonaut.NotificationData;

/**
 * Information about the different types of rendering a notification might
 * encounter.
 */
public enum RenderingType
{
	/**
	 * Single {@link NotificationData} is being rendered.
	 */
	SINGLE,

	/**
	 * Multiple {@link NotificationData}s are being rendered. This type of
	 * rendering means that the invoker added several data instances to the
	 * notification.
	 */
	MULTIPLE,

	/**
	 * Single or multiple {@link NotificationData}s are being rendered as part
	 * of a digest. This type of rendering should provide a smaller compact
	 * version of the information within the notification.
	 */
	DIGEST;
}
