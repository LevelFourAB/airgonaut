package se.l4.airgonaut.engine.template;

import java.util.Objects;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Container for HTML. Used to make it clear that a {@link TemplateEngine}
 * returns HTML.
 */
public class HTMLString
{
	private final String raw;

	public HTMLString(@NonNull String raw)
	{
		Objects.requireNonNull(raw);

		this.raw = raw;
	}

	/**
	 * Get the HTML as a string.
	 *
	 * @return
	 *   raw HTML
	 */
	@NonNull
	public String getRaw()
	{
		return raw;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raw == null) ? 0 : raw.hashCode());
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
		HTMLString other = (HTMLString) obj;
		if (raw == null) {
			if (other.raw != null)
				return false;
		} else if (!raw.equals(other.raw))
			return false;
		return true;
	}

}
