package se.l4.airgonaut.email;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.engine.LocalNotifications;

public class EmailTargetTest
{
	private TestEmailBackend backend;
	private EmailTarget target;
	private LocalNotifications notifications;

	@Before
	public void setup()
	{
		backend = new TestEmailBackend();
		target = new EmailTarget(
			backend,
			EmailChannel.create("test@example.org"),
			new TestEmailTemplate()
		);
		notifications = LocalNotifications.builder()
			.addTarget(target)
			.addRenderer(new TestDataRenderer())
			.build();
	}

	@Test
	public void testSingle()
	{
		notifications.newNotification()
			.to(EmailChannel.create("test@test.com"))
			.withData(new TestData("Simple Test"))
			.send();

		Optional<RenderedEmail> first = backend.firstEmail();
		assertThat(first.isPresent(), is(true));

		RenderedEmail email = first.get();
		assertThat(email.getTitle(), is("1"));
		assertThat(email.getPlainText().get(), containsString("Simple Test"));
		assertThat(email.getTo(), hasItem(EmailChannel.create("test@test.com")));
	}

	@Test
	public void testMultipleData()
	{
		notifications.newNotification()
			.to(EmailChannel.create("test@test.com"))
			.withData(new TestData("Test1"))
			.withData(new TestData("Test2"))
			.send();

		Optional<RenderedEmail> first = backend.firstEmail();
		assertThat(first.isPresent(), is(true));

		RenderedEmail email = first.get();
		assertThat(email.getTitle(), is("2"));
		assertThat(email.getPlainText().get(), containsString("Test1"));
		assertThat(email.getPlainText().get(), containsString("Test2"));
		assertThat(email.getTo(), hasItem(EmailChannel.create("test@test.com")));
	}

	@Test
	public void testMultipleTargets()
	{
		notifications.newNotification()
			.to(EmailChannel.create("test@test.com"))
			.to(EmailChannel.create("test2@test.com"))
			.withData(new TestData("MultiTarget"))
			.send();

		List<RenderedEmail> emails = backend.getEmails();
		assertThat(emails.size(), is(2));

		RenderedEmail email1 = emails.get(0);
		assertThat(email1.getTitle(), is("1"));
		assertThat(email1.getPlainText().get(), containsString("MultiTarget"));
		assertThat(email1.getTo(), hasItem(EmailChannel.create("test2@test.com")));

		RenderedEmail email2 = emails.get(1);
		assertThat(email2.getTitle(), is("1"));
		assertThat(email2.getPlainText().get(), containsString("MultiTarget"));
		assertThat(email2.getTo(), hasItem(EmailChannel.create("test@test.com")));
	}

	public class TestData
		implements NotificationData
	{
		public final String value;

		public TestData(String value)
		{
			this.value = value;
		}
	}

	public class TestDataRenderer
		implements EmailRenderer<TestData>
	{

		@Override
		public void render(EmailRenderingEncounter<TestData> encounter)
		{
			encounter.setPlainText(encounter.getData().value);
		}

	}
}
