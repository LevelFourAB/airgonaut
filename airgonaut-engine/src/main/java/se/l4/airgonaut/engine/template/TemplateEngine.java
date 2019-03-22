package se.l4.airgonaut.engine.template;

import javax.annotation.Nonnull;

import se.l4.airgonaut.engine.NotificationRenderException;

/**
 * Abstract of a template engine that can render data to a specific output.
 * Targets may choose to implement support for engines that support a certain
 * output such as {@link HTMLString}.
 */
public interface TemplateEngine<TemplateData, Output>
{
	/**
	 * Render the given data.
	 *
	 * @throws NotificationRenderException
	 *   if unable to render the data
	 */
	@Nonnull
	Output render(@Nonnull TemplateData data);
}
