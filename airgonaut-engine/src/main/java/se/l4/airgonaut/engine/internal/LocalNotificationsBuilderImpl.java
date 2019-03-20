package se.l4.airgonaut.engine.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.l4.airgonaut.engine.LocalNotifications;
import se.l4.airgonaut.engine.LocalNotifications.Builder;
import se.l4.airgonaut.engine.NotificationRenderer;
import se.l4.airgonaut.engine.NotificationTarget;
import se.l4.commons.types.TypeFinder;

/**
 * Builder for creating instances of {@link LocalNotificationsImpl}.
 */
public class LocalNotificationsBuilderImpl
	implements LocalNotifications.Builder
{
	private final List<NotificationTarget<?>> targets;
	private final Set<NotificationRenderer<?, ?>> renderers;

	private TypeFinder finder;

	public LocalNotificationsBuilderImpl()
	{
		this.targets = new ArrayList<>();
		this.renderers = new HashSet<>();
	}

	@Override
	public LocalNotifications.Builder withTypeFinder(TypeFinder finder)
	{
		this.finder = finder;
		return this;
	}

	@Override
	public Builder addRenderer(NotificationRenderer<?, ?> renderer)
	{
		renderers.add(renderer);
		return this;
	}

	@Override
	public Builder addRenderers(Iterable<NotificationRenderer<?, ?>> renderers)
	{
		for(NotificationRenderer<?, ?> r : renderers)
		{
			this.renderers.add(r);
		}
		return this;
	}

	@Override
	public LocalNotifications.Builder addTarget(NotificationTarget<?> target)
	{
		targets.add(target);
		return this;
	}

	@Override
	public LocalNotifications build()
	{
		if(finder != null)
		{
			// A finder has been provided ask it for renderers
			for(NotificationRenderer<?, ?> r : finder.getSubTypesAsInstances(NotificationRenderer.class))
			{
				renderers.add(r);
			}
		}

		Renderers renderers = new Renderers(this.renderers);
		return new LocalNotificationsImpl(renderers, targets);
	}

}
