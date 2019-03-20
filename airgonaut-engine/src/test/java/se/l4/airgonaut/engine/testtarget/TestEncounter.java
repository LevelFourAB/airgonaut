package se.l4.airgonaut.engine.testtarget;

import java.util.LinkedList;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.engine.RenderingEncounter;
import se.l4.airgonaut.engine.RenderingType;

public class TestEncounter<D extends NotificationData>
	implements RenderingEncounter<D>
{
	private final RenderingType type;
	D data;

	private final LinkedList<Object> testData;

	public TestEncounter(RenderingType type)
	{
		this.type = type;

		testData = new LinkedList<>();
	}

	@Override
	public RenderingType getType()
	{
		return type;
	}

	@Override
	public D getData()
	{
		return data;
	}

	public void push(Object testData)
	{
		this.testData.add(testData);
	}

	public void check(Object data)
	{
		if(testData.isEmpty()) throw new AssertionError("No more data available, expected " + data);

		Object current = testData.poll();
		if(! current.equals(data))
		{
			throw new AssertionError("Expected " + data + " but got " + current);
		}
	}

	public void checkEmpty()
	{
		if(! testData.isEmpty()) throw new AssertionError("Still data available, next item is: " + testData.poll());
	}
}
