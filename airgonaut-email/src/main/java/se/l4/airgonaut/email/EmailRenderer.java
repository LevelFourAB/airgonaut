package se.l4.airgonaut.email;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.engine.NotificationRenderer;

/**
 * Renderer for notifications that are being sent as e-mails.
 */
public interface EmailRenderer<D extends NotificationData>
	extends NotificationRenderer<D, EmailRenderingEncounter<D>>
{
}
