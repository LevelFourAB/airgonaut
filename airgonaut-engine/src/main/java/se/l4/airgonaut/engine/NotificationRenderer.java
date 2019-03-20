package se.l4.airgonaut.engine;

import se.l4.airgonaut.NotificationData;

/**
 * Interface used when rendering a certain type of notification for a certain
 * target.
 */
public interface NotificationRenderer<D extends NotificationData, Encounter extends RenderingEncounter<D>>
{
	/**
	 * Render the notification using the given encounter.
	 *
	 * @param encounter
	 *   the encounter describing
	 */
	void render(Encounter encounter);
}
