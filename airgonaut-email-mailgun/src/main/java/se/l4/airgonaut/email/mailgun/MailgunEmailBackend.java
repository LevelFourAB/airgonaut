package se.l4.airgonaut.email.mailgun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.NonNull;
import kong.unirest.HttpResponse;
import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import se.l4.airgonaut.channels.EmailChannel;
import se.l4.airgonaut.email.EmailBackend;
import se.l4.airgonaut.email.EmailChannelEncoding;
import se.l4.airgonaut.email.RenderedEmail;

/**
 * {@link EmailBackend} for sending e-mails using the Mailgun Rest API.
 */
public class MailgunEmailBackend
	implements EmailBackend
{
	/**
	 * URL used for Mailgun in most of the world.
	 */
	public static final String BASE_URL = "https://api.mailgun.net/v3";

	/**
	 * URL used for Mailgun within the EU.
	 */
	public static final String BASE_URL_EU = "https://api.eu.mailgun.net/v3";

	private final String endpoint;
	private final String apiKey;

	public MailgunEmailBackend(
		@NonNull String baseUrl,
		@NonNull String domain,
		@NonNull String apiKey
	)
	{
		Objects.requireNonNull(baseUrl);
		Objects.requireNonNull(domain);
		Objects.requireNonNull(apiKey);

		this.endpoint = baseUrl + '/' + domain + "/messages";
		this.apiKey = apiKey;
	}

	@Override
	public void send(RenderedEmail email)
		throws IOException
	{
		MultipartBody request = Unirest.post(endpoint)
			.basicAuth("api", apiKey)
			.field("from", EmailChannelEncoding.encode(email.getFrom()))
			.field("to", toAddresses(email.getTo()))
			.field("cc", toAddresses(email.getCC()));

		if(email.getPlainText().isPresent())
		{
			request = request.field("text", email.getPlainText().get());
		}

		if(email.getHTML().isPresent())
		{
			request = request.field("html", email.getHTML().get());
		}

		HttpResponse<?> reply = request.asJson();
		if(! reply.isSuccess())
		{
			throw new IOException("Unable to send e-mail via Mailgun");
		}
	}

	private List<String> toAddresses(List<EmailChannel> emails)
	{
		List<String> result = new ArrayList<>(emails.size());
		for(EmailChannel email : emails)
		{
			result.add(EmailChannelEncoding.encode(email));
		}

		return result;
	}
}
