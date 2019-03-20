package se.l4.airgonaut.engine;

import se.l4.airgonaut.NotificationData;

/**
 * Base interface used for rendering {@link NotificationData} for a
 * specific {@link NotificationTarget}.
 */
public interface RenderingEncounter<D extends NotificationData>
{
	/**
	 * Get the type of rendering being performed.
	 */
	RenderingType getType();

	/**
	 * Get the notification being rendered.
	 */
	D getData();
}
