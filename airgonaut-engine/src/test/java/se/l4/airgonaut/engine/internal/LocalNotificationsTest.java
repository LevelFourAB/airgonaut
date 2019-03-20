package se.l4.airgonaut.engine.internal;

import org.junit.Test;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.engine.LocalNotifications;
import se.l4.airgonaut.engine.testtarget.TestEncounter;
import se.l4.airgonaut.engine.testtarget.TestRenderer;
import se.l4.airgonaut.engine.testtarget.TestTarget;

public class LocalNotificationsTest
{
	@Test
	public void testCreate()
	{
		TestTarget testTarget = new TestTarget();

		LocalNotifications.builder()
			.addTarget(testTarget)
			.build();

		testTarget.checkEmpty();
	}

	@Test
	public void testSendSingle()
	{
		TestTarget testTarget = new TestTarget();

		LocalNotifications notifications = LocalNotifications.builder()
			.addRenderer(new TestDataRenderer())
			.addTarget(testTarget)
			.build();

		notifications.newNotification()
			.withData(new TestData(1))
			.to(EmailChannel.create("test@example.com"))
			.send();

		testTarget.check(1);
		testTarget.checkEmpty();
	}

	private static class TestData
		implements NotificationData
	{
		private final Object payload;

		public TestData(Object payload)
		{
			this.payload = payload;
		}
	}

	private static class TestDataRenderer
		implements TestRenderer<TestData>
	{
		@Override
		public void render(TestEncounter<TestData> encounter)
		{
			encounter.push(encounter.getData().payload);
		}
	}
}
