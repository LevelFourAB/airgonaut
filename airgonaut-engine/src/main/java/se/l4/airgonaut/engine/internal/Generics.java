package se.l4.airgonaut.engine.internal;

import java.lang.reflect.Type;
import java.util.Optional;

import se.l4.airgonaut.engine.NotificationRenderer;
import se.l4.commons.types.Types;
import se.l4.commons.types.reflect.TypeRef;

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
		TypeRef type = Types.reference(renderer);

		Optional<TypeRef> renderererInterface = type.findSuperclassOrInterface(NotificationRenderer.class);
		if(! renderererInterface.isPresent())
		{
			throw new IllegalStateException("The renderer " + renderer + " does not implement NotificationRenderer");
		}

		Optional<TypeRef> parameter = renderererInterface.get().getTypeParameter(0);
		if(! parameter.isPresent())
		{
			// Make sure that we have access to the
			throw new IllegalStateException("The renderer " + renderer + " does not specify type parameters when implementing it's renderer interface");
		}

		return parameter.get().getErasedType();
	}

}
