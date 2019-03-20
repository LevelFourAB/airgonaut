package se.l4.airgonaut.engine;

import java.util.List;
import java.util.Optional;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.ContactChannel;

/**
 * Encounter used when a target should send notification to a receiver.
 */
public interface NotificationTargetEncounter<Channel extends ContactChannel>
{
	/**
	 * Get information about the channel.
	 *
	 * @return
	 *   information about the channel
	 */
	Channel getChannel();

	/**
	 * Get the type of rendering that is being done.
	 *
	 * @return
	 *   the type of rendering
	 */
	RenderingType getType();

	/**
	 * Get the {@link NotificationData} that should be sent.
	 *
	 * @return
	 *   list of {@link NotificationData}
	 */
	List<NotificationData> getNotificationData();

	/**
	 * Find a renderer for a certain type of notification data.
	 *
	 * @param data
	 *   the type of data being rendered
	 * @param type
	 *   the type of renderer to use
	 * @return
	 *   renderer
	 */
	<D extends NotificationData, R extends NotificationRenderer<D, ?>> Optional<R> findRenderer(Class<D> data, Class<R> type);

	/**
	 * Request that the given data is rendered using a renderer of a certain
	 * type.
	 *
	 * @param data
	 *   the notification data to render
	 * @param rendererType
	 *   the type of renderer to use
	 * @param encounter
	 *   the encounter to use for rendering
	 */
	<D extends NotificationData, E extends RenderingEncounter<D>, R extends NotificationRenderer<?, ?>> void render(D data, Class<R> rendererType, E encounter);
}
