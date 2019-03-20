package se.l4.airgonaut.engine.testtarget;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.channels.ContactChannel;
import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.engine.NotificationTarget;
import se.l4.airgonaut.engine.NotificationTargetEncounter;

public class TestTarget
	implements NotificationTarget<EmailChannel>
{
	private TestEncounter encounter;

	public TestTarget()
	{
	}

	@Override
	public Class getRendererType()
	{
		return TestRenderer.class;
	}

	@Override
	public boolean supportsChannel(ContactChannel channel)
	{
		return channel instanceof EmailChannel;
	}

	@Override
	public void send(NotificationTargetEncounter<EmailChannel> encounter)
	{
		this.encounter = new TestEncounter(encounter.getType());

		for(NotificationData data : encounter.getNotificationData())
		{
			this.encounter.data = data;
			encounter.render(data, TestRenderer.class, this.encounter);
		}
	}

	public void check(Object data)
	{
		if(encounter == null) throw new AssertionError("Nothing has been sent");

		encounter.check(data);
	}

	public void checkEmpty()
	{
		if(encounter == null) return;

		encounter.checkEmpty();
	}

}
