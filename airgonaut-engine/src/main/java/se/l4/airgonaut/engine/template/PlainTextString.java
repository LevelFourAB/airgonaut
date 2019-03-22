package se.l4.airgonaut.engine.template;

import java.util.Objects;

/**
 * Container for plain text. Used to make it clear that a {@link TemplateEngine}
 * returns plain text.
 */
public class PlainTextString
{
	private final String text;

	public PlainTextString(String text)
	{
		Objects.requireNonNull(text);

		this.text = text;
	}

	/**
	 * Get the text as a string.
	 *
	 * @return
	 *   raw text
	 */
	public String getText()
	{
		return text;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		PlainTextString other = (PlainTextString) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}
