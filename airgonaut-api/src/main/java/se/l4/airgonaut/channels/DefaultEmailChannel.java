package se.l4.airgonaut.channels;

import java.util.Objects;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Simple static implementation of {@link EmailChannel}.
 */
public class DefaultEmailChannel
	implements EmailChannel
{
	private final String email;
	private final Optional<String> name;

	public DefaultEmailChannel(
		@NonNull String email,
		@NonNull Optional<String> name)
	{
		Objects.requireNonNull(email);
		Objects.requireNonNull(name);

		this.email = email;
		this.name = name;
	}

	@Override
	public String getEmail()
	{
		return email;
	}

	@Override
	public Optional<String> getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return "DefaultEmailChannel{" + email + ", name=" + name + "}";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultEmailChannel other = (DefaultEmailChannel) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
