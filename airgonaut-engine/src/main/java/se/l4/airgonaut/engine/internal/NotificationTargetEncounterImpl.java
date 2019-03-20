package se.l4.airgonaut.engine.internal;

import java.util.List;
import java.util.Optional;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.ContactChannel;
import se.l4.airgonaut.engine.NotificationRenderException;
import se.l4.airgonaut.engine.NotificationRenderer;
import se.l4.airgonaut.engine.NotificationTargetEncounter;
import se.l4.airgonaut.engine.RenderingEncounter;
import se.l4.airgonaut.engine.RenderingType;

public class NotificationTargetEncounterImpl<Channel extends ContactChannel>
	implements NotificationTargetEncounter<Channel>
{
	private final Renderers renderers;
	private final Channel channel;
	private final RenderingType type;
	private final List<NotificationData> notificationData;

	public NotificationTargetEncounterImpl(
		Renderers renderers,
		Channel channel,
		RenderingType type,
		List<NotificationData> data
	)
	{
		this.renderers = renderers;

		this.channel = channel;
		this.type = type;
		this.notificationData = data;
	}

	@Override
	public Channel getChannel()
	{
		return channel;
	}

	@Override
	public RenderingType getType()
	{
		return type;
	}

	@Override
	public List<NotificationData> getNotificationData()
	{
		return notificationData;
	}

	@Override
	public <D extends NotificationData, R extends NotificationRenderer<D, ?>> Optional<R> findRenderer(
		Class<D> data,
		Class<R> type
	)
	{
		return renderers.find(type, data);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <D extends NotificationData, E extends RenderingEncounter<D>, R extends NotificationRenderer<?, ?>> void render(
		D data,
		Class<R> rendererType,
		E encounter
	)
	{
		Optional<NotificationRenderer> renderer = renderers.find((Class) rendererType, (Class) data.getClass());
		if(! renderer.isPresent())
		{
			throw new NotificationRenderException("No render available for " + data);
		}

		renderer.get().render(encounter);
	}

}
