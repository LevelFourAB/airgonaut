package se.l4.airgonaut.engine.testtarget;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.engine.NotificationRenderer;

public interface TestRenderer<D extends NotificationData>
	extends NotificationRenderer<D, TestEncounter<D>>
{

}
