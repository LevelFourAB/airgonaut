package se.l4.airgonaut.engine.internal;

import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.classmate.ResolvedType;

import se.l4.airgonaut.engine.NotificationRenderer;
import se.l4.commons.types.Types;

/**
 * Utilities for working with some common generics functions used in the
 * library.
 */
public class Generics
{
	private Generics()
	{
	}

	/**
	 * Find the data type used by the given renderer by looking at the type
	 * parameters used when implementing the {@link NotificationRenderer}
	 * interface.
	 *
	 * @param renderer
	 *   the type of the renderer
	 * @return
	 *   the class for the data the renderer handles
	 */
	public static Class<?> findGenericDataTypeOfRenderer(Type renderer)
	{
		ResolvedType rt = Types.resolve(renderer);

		ResolvedType renderererInterface = rt.findSupertype(NotificationRenderer.class);
		if(renderererInterface == null)
		{
			throw new IllegalStateException("The renderer " + renderer + " does not implement NotificationRenderer");
		}

		List<ResolvedType> parameters = renderererInterface.getTypeParameters();
		if(parameters.isEmpty())
		{
			// Make sure that we have access to the
			throw new IllegalStateException("The renderer " + renderer + " does not specify type parameters when implementing it's renderer interface");
		}

		// Return the first parameter as that is the ones used for
		ResolvedType dataType = parameters.get(0);
		return dataType.getErasedType();
	}

}
