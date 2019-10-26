package se.l4.airgonaut.email;

import org.apache.james.mime4j.codec.EncoderUtil;

import se.l4.airgonaut.channels.EmailChannel;

/**
 * Helper for dealing with encoding of {@link EmailChannel} into a format
 * understood by e-mail services.
 */
public class EmailChannelEncoding
{
	private EmailChannelEncoding()
	{
	}

	/**
	 * Encode the given channel into a format that includes the name if
	 * available.
	 */
	public static String encode(EmailChannel channel)
	{
		if(channel.getName().isPresent())
		{
			String name = channel.getName().get();
			return EncoderUtil.encodeAddressDisplayName(name)
				+ " <" + channel.getEmail() + ">";
		}
		else
		{
			return channel.getEmail();
		}
	}
}
