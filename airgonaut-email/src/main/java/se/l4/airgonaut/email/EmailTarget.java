package se.l4.airgonaut.email;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.NonNull;
import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.ContactChannel;
import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.email.internal.EmailRenderingEncounterImpl;
import se.l4.airgonaut.email.internal.EmailTemplateEncounterImpl;
import se.l4.airgonaut.email.internal.RenderedEmailImpl;
import se.l4.airgonaut.engine.NotificationRenderException;
import se.l4.airgonaut.engine.NotificationSendException;
import se.l4.airgonaut.engine.NotificationTarget;
import se.l4.airgonaut.engine.NotificationTargetEncounter;

/**
 * Target for sending e-mails. This target will render notification data using
 * {@link EmailRenderer}s and then use an {@link EmailTemplate} to render the
 * full e-mail. E-mails are then sent over a {@link EmailBackend}.
 */
public class EmailTarget
	implements NotificationTarget<EmailChannel>
{
	private final EmailBackend backend;
	private final EmailChannel defaultFrom;
	private final EmailTemplate template;

	public EmailTarget(
		@NonNull EmailBackend backend,
		@NonNull EmailChannel defaultFrom,
		@NonNull EmailTemplate template
	)
	{
		this.defaultFrom = Objects.requireNonNull(defaultFrom);
		this.backend = Objects.requireNonNull(backend);
		this.template = Objects.requireNonNull(template);
	}

	@Override
	public Class<?> getRendererType()
	{
		return EmailRenderer.class;
	}

	@Override
	public boolean supportsChannel(ContactChannel channel)
	{
		return channel instanceof EmailChannel;
	}

	@Override
	public void send(NotificationTargetEncounter<EmailChannel> encounter)
	{
		List<NotificationData> data = encounter.getNotificationData();
		EmailTemplateEncounterImpl templateEncounter =
			new EmailTemplateEncounterImpl(encounter.getType());

		for(NotificationData d : data)
		{
			EmailRenderingEncounterImpl<NotificationData> renderingEncounter
				= new EmailRenderingEncounterImpl<>(
					encounter.getChannel(),
					encounter.getType(),
					d
				);

			// Request that we render the data using the encounter
			try
			{
				encounter.render(d, EmailRenderer.class, renderingEncounter);
			}
			catch(Exception e)
			{
				String message = e.getMessage();
				Throwable cause = e;
				if(e instanceof NotificationRenderException)
				{
					cause = e.getCause();
				}

				// Rethrow this as a render exception
				throw new NotificationRenderException("Unable to render data " + d + " for e-mail; " + message, cause);
			}

			// Make the rendered data available for the template
			templateEncounter.push(
				renderingEncounter.getTitle(),
				renderingEncounter.getPlainText(),
				renderingEncounter.getHTML()
			);
		}

		// Try to transfer the title if there is only one rendered notification
		List<RenderedEmailNotification> list = templateEncounter.getEntries();
		if(list.size() == 1)
		{
			RenderedEmailNotification emailNotification = list.get(0);
			if(emailNotification.getProposedTitle().isPresent())
			{
				templateEncounter.setTitle(emailNotification.getProposedTitle().get());
			}
		}

		// Request the template render
		try
		{
			template.render(templateEncounter);
		}
		catch(Exception e)
		{
			String message = e.getMessage();
			Throwable cause = e;
			if(e instanceof NotificationRenderException)
			{
				cause = e.getCause();
			}

			throw new NotificationRenderException("Unable to render e-mail; " + message, cause);
		}

		// Validate that there is a title
		if(templateEncounter.getTitle() == null)
		{
			throw new NotificationRenderException("E-mails require a title to be set");
		}

		// Validate that there is content
		if(templateEncounter.getPlainText() == null && templateEncounter.getHTML() == null)
		{
			throw new NotificationRenderException("E-mails require plain text, HTML content or both to be present");
		}

		// Construct the final e-mail that will be sent
		RenderedEmail email = new RenderedEmailImpl(
			defaultFrom,

			Collections.singletonList(encounter.getChannel()),
			Collections.emptyList(),
			templateEncounter.getTitle(),

			Optional.ofNullable(templateEncounter.getPlainText()),
			Optional.ofNullable(templateEncounter.getHTML()),

			Collections.emptySet()
		);

		// Request that the backend sends it
		try
		{
			backend.send(email);
		}
		catch(Exception e)
		{
			String message = e.getMessage();
			Throwable cause = e;
			if(e instanceof NotificationSendException)
			{
				cause = e.getCause();
			}

			throw new NotificationSendException("Unable to send e-mail; " + message, cause);
		}
	}

}
