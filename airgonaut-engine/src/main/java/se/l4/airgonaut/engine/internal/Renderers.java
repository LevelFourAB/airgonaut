package se.l4.airgonaut.engine.internal;

import java.util.Collection;
import java.util.Optional;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.engine.NotificationRenderer;
import se.l4.airgonaut.engine.NotificationTarget;
import se.l4.commons.types.matching.ClassMatchingHashMultimap;
import se.l4.commons.types.matching.ClassMatchingMultimap;
import se.l4.commons.types.matching.MatchedType;

/**
 * Holder for registered renderers. Contains functionality to resolve the
 * renderer to use for an instance of {@link NotificationData} being sent
 * over a {@link NotificationTarget}.
 */
public class Renderers
{
	private final ClassMatchingMultimap<Object, NotificationRenderer<?, ?>>  dataMapping;

	public Renderers(Collection<NotificationRenderer<?, ?>> renderers)
	{
		// Create a map that can be used to collect items that match a certain renderer
		dataMapping = new ClassMatchingHashMultimap<>();

		for(NotificationRenderer<?, ?> r : renderers)
		{
			Class<?> dataType = Generics.findGenericDataTypeOfRenderer(r.getClass());
			dataMapping.put(dataType, r);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public <D extends NotificationData, T extends NotificationRenderer<D, ?>> Optional<T> find(Class<? super T> rendererType, Class<D> dataType)
	{
		// TODO: Introduce caching here
		for(MatchedType<Object, NotificationRenderer<?, ?>> e : dataMapping.getAll(dataType))
		{
			if(rendererType.isAssignableFrom(e.getData().getClass()))
			{
				return Optional.of((NotificationRenderer) e.getData());
			}
		}

		return Optional.empty();
	}

}
