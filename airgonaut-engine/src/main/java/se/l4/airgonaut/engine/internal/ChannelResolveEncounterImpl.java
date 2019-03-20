package se.l4.airgonaut.engine.internal;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import se.l4.airgonaut.ChannelResolveEncounter;
import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.ContactChannel;

/**
 * Implementation of {@link ChannelResolveEncounter}.
 */
public class ChannelResolveEncounterImpl
	implements ChannelResolveEncounter
{
	private final NotificationData notificationData;

	private final Set<ContactChannel> activeDirectChannels;
	private final Set<ContactChannel> allActiveChannels;

	public ChannelResolveEncounterImpl(NotificationData notificationData)
	{
		this.notificationData = notificationData;

		allActiveChannels = new HashSet<>();

		activeDirectChannels = new HashSet<>();
	}

	public Set<ContactChannel> getActiveDirectChannels()
	{
		return activeDirectChannels;
	}

	@Override
	public NotificationData getNotificationData()
	{
		return notificationData;
	}

	@Override
	public Set<ContactChannel> getActiveChannels()
	{
		return new HashSet<>(allActiveChannels);
	}

	@Override
	public Set<ContactChannel> getActiveChannels(Class<? extends ContactChannel> type)
	{
		return getActiveChannels(channel -> channel.getClass().isAssignableFrom(type));
	}

	@Override
	public Set<ContactChannel> getActiveChannels(Predicate<ContactChannel> predicate)
	{
		Set<ContactChannel> channels = new HashSet<>();
		for(ContactChannel channel : allActiveChannels)
		{
			if(predicate.test(channel))
			{
				channels.add(channel);
			}
		}
		return channels;
	}

	@Override
	public boolean hasActiveChannel()
	{
		return ! allActiveChannels.isEmpty();
	}

	@Override
	public boolean hasActiveChannel(Class<? extends ContactChannel> type)
	{
		return hasActiveChannel(channel -> channel.getClass().isAssignableFrom(type));
	}

	@Override
	public boolean hasActiveChannel(Predicate<ContactChannel> predicate)
	{
		for(ContactChannel channel : allActiveChannels)
		{
			if(predicate.test(channel))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void activate(ContactChannel channel)
	{
		activeDirectChannels.add(channel);
		allActiveChannels.add(channel);
	}

}
