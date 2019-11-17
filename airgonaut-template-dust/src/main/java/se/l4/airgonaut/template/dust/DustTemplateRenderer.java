package se.l4.airgonaut.template.dust;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.engine.NotificationRenderException;
import se.l4.airgonaut.engine.template.TemplateEngine;
import se.l4.dust.api.template.DefaultRenderingContext;
import se.l4.dust.api.template.TemplateOutputStream;
import se.l4.dust.api.template.TemplateRenderer;
import se.l4.dust.core.template.html.HtmlTemplateOutput;

/**
 * Template engine that uses {@link TemplateRenderer} from Dust. This will
 * render objects to HTML and is useful for e-mail or in browser targets.
 */
public class DustTemplateRenderer
	implements TemplateEngine<Object, String>
{
	private final TemplateRenderer renderer;

	public DustTemplateRenderer(@NonNull TemplateRenderer renderer)
	{
		this.renderer = Objects.requireNonNull(renderer);
	}

	@Override
	public String render(Object data)
	{
		DefaultRenderingContext ctx = new DefaultRenderingContext();

		StringWriter writer = new StringWriter();
		try(TemplateOutputStream out = new HtmlTemplateOutput(writer))
		{
			renderer.render(ctx, data, out);
		}
		catch(IOException e)
		{
			throw new NotificationRenderException("Rendering to HTML failed; " + e.getMessage(), e);
		}

		return writer.toString();
	}

}
