package se.l4.airgonaut.email;

public class TestEmailTemplate
	implements EmailTemplate
{
	public static final String DIVIDER = "\n=============\n";

	@Override
	public void render(EmailTemplateEncounter encounter)
	{
		StringBuilder builder = new StringBuilder();
		for(RenderedEmailNotification notification : encounter.getEntries())
		{
			builder
				.append(notification.getPlainText())
				.append(DIVIDER);
		}

		encounter.setTitle(String.valueOf(encounter.getEntries().size()));
		encounter.setPlainText(builder.toString());
	}

}
