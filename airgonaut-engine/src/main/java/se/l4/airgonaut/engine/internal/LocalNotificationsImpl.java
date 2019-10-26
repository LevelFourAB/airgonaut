package se.l4.airgonaut.engine.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.l4.airgonaut.NotificationBuilder;
import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.NotificationException;
import se.l4.airgonaut.NotificationReceiver;
import se.l4.airgonaut.Notifications;
import se.l4.airgonaut.channels.ContactChannel;
import se.l4.airgonaut.engine.LocalNotifications;
import se.l4.airgonaut.engine.NotificationRenderer;
import se.l4.airgonaut.engine.NotificationTarget;
import se.l4.airgonaut.engine.RenderingType;

/**
 * Implementation of {@link Notifications} that implements the main
 * notification resolving and sending.
 */
public class LocalNotificationsImpl
	implements LocalNotifications
{
	private final Logger log;

	private final Renderers renderers;
	private final NotificationTarget[] targets;

	LocalNotificationsImpl(Renderers renderers, List<NotificationTarget<?>> targets)
	{
		log = LoggerFactory.getLogger(LocalNotifications.class);

		this.renderers = renderers;
		this.targets = targets.toArray(new NotificationTarget[targets.size()]);
	}

	@Override
	public NotificationBuilder newNotification()
	{
		return new NotificationBuilderImpl();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sendNotification(
		NotificationTarget target,
		ContactChannel channel,
		Collection<NotificationData> data
	)
	{
		ImmutableList.Builder<NotificationData> renderableDataBuilder =
			ImmutableList.builder();

		/*
		 * Go through all of the notification data and resolve which ones can
		 * actually be rendered.
		 */
		Class rendererType = target.getRendererType();
		for(NotificationData nd : data)
		{
			Optional<NotificationRenderer> renderer = renderers.find(rendererType, nd.getClass());
			if(renderer.isPresent())
			{
				// There is a renderer available - keep the data
				renderableDataBuilder.add(nd);
			}
		}

		ImmutableList<NotificationData> renderableData = renderableDataBuilder.build();

		if(renderableData.isEmpty())
		{
			// There is no data to be rendered - skip everything
			log.debug("Skipping sending to {}, no renderers available for target", channel);
			return;
		}

		if(log.isDebugEnabled())
		{
			log.debug("Rendering and sending {} to {}", data, channel);
		}

		// Depending on the amount of of data either request single or multiple rendering
		RenderingType type = renderableData.size() > 1
			? RenderingType.MULTIPLE
			: RenderingType.SINGLE;

		// Create the encounter and request that the target sends it
		NotificationTargetEncounterImpl encounter =
			new NotificationTargetEncounterImpl(
				renderers,
				channel,
				type,
				renderableData
			);

		target.send(encounter);
	}

	private class NotificationBuilderImpl
		implements NotificationBuilder
	{
		private final Set<NotificationReceiver> receivers;
		private final Set<ContactChannel> channels;
		private final List<NotificationData> data;

		public NotificationBuilderImpl()
		{
			receivers = new HashSet<>();
			channels = new HashSet<>();

			data = new ArrayList<>();
		}

		@Override
		public NotificationBuilder withData(NotificationData data)
		{
			this.data.add(data);
			return this;
		}

		@Override
		public NotificationBuilder to(NotificationReceiver receiver)
		{
			receivers.add(receiver);
			return this;
		}

		@Override
		public NotificationBuilder to(ContactChannel channel)
		{
			channels.add(channel);
			return this;
		}

		@Override
		public void send()
		{
			if(data.isEmpty())
			{
				throw new NotificationException("At least one instance of NotificationData is required to send notification");
			}

			if(channels.isEmpty() && receivers.isEmpty())
			{
				throw new NotificationException("At least one ContactChannel or one NotificationReceiver is needed to send notification");
			}

			Multimap<ContactChannel, NotificationData> dataPerChannel = ArrayListMultimap.create();

			/*
			 * Every channel directly added receives all of the notifications.
			 */
			for(ContactChannel channel : channels)
			{
				dataPerChannel.putAll(channel, data);
			}

			/*
			 * Go through all of the receivers and ask them what channels
			 * should be used.
			 */
			for(NotificationReceiver receiver : receivers)
			{
				for(NotificationData nd : data)
				{
					ChannelResolveEncounterImpl encounter = new ChannelResolveEncounterImpl(nd);

					// Ask the reciever about the channels this notification should go to
					receiver.resolveChannels(encounter);

					for(ContactChannel channel : encounter.getActiveDirectChannels())
					{
						dataPerChannel.put(channel, nd);
					}
				}
			}

			if(log.isDebugEnabled())
			{
				// Log information about the final channels if debug is active
				log.debug("Sending notification to {}", dataPerChannel.keySet());
			}

			/*
			 * Go through all the channels and decide which receiver is going
			 * to handle them.
			 */
			_outer:
			for(ContactChannel channel : dataPerChannel.keySet())
			{
				for(NotificationTarget<?> target : targets)
				{
					if(target.supportsChannel(channel))
					{
						// This target should handle this notification
						sendNotification(target, channel, dataPerChannel.get(channel));

						// Break so no other targets handle the channel
						continue _outer;
					}
				}

				if(log.isDebugEnabled())
				{
					log.debug("Channel {} not supported by any active NotificationTarget", channel);
				}
			}
		}
	}
}
