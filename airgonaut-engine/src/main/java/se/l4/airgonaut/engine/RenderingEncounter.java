package se.l4.airgonaut.engine;

import edu.umd.cs.findbugs.annotations.NonNull;
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
	@NonNull
	RenderingType getType();

	/**
	 * Get the notification being rendered.
	 */
	@NonNull
	D getData();
}
