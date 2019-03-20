package se.l4.airgonaut.engine.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import se.l4.airgonaut.NotificationData;
import se.l4.airgonaut.engine.testtarget.TestEncounter;
import se.l4.airgonaut.engine.testtarget.TestRenderer;

public class RenderersTest
{

	@Test
	public void testSimpleFind()
	{
		TestRenderer1 r1 = new TestRenderer1();
		TestRenderer2 r2 = new TestRenderer2();
		Renderers renderers = new Renderers(ImmutableList.of(r1, r2));

		Optional<TestRenderer<TestData2>> renderer = renderers.find(TestRenderer.class, TestData2.class);
		assertThat(renderer, notNullValue());
		assertThat(renderer.isPresent(), is(true));
		assertThat(renderer.get(), is(r2));
	}

	@Test
	public void testNoMatching()
	{
		TestRenderer1 r1 = new TestRenderer1();
		Renderers renderers = new Renderers(ImmutableList.of(r1));

		Optional<TestRenderer<TestData2>> renderer = renderers.find(TestRenderer.class, TestData2.class);
		assertThat(renderer, notNullValue());
		assertThat(renderer.isPresent(), is(false));
	}

	@Test
	public void testFindInherited()
	{
		TestRenderer1 r1 = new TestRenderer1();
		TestRenderer2_Ext r2 = new TestRenderer2_Ext();
		Renderers renderers = new Renderers(ImmutableList.of(r1, r2));

		Optional<TestRenderer<TestData2>> renderer = renderers.find(TestRenderer.class, TestData2.class);
		assertThat(renderer, notNullValue());
		assertThat(renderer.isPresent(), is(true));
		assertThat(renderer.get(), is(r2));
	}

	@Test
	public void testFindAbstractInherited()
	{
		TestRenderer1 r1 = new TestRenderer1();
		TestRenderer3 r2 = new TestRenderer3();
		Renderers renderers = new Renderers(ImmutableList.of(r1, r2));

		Optional<TestRenderer<TestData3>> renderer = renderers.find(TestRenderer.class, TestData3.class);
		assertThat(renderer, notNullValue());
		assertThat(renderer.isPresent(), is(true));
		assertThat(renderer.get(), is(r2));
	}

	public class TestData1 implements NotificationData
	{
	}

	public class TestRenderer1 implements TestRenderer<TestData1>
	{
		@Override
		public void render(TestEncounter<TestData1> encounter)
		{
		}
	}

	public class TestData2 implements NotificationData
	{
	}

	public class TestRenderer2 implements TestRenderer<TestData2>
	{
		@Override
		public void render(TestEncounter<TestData2> encounter)
		{
		}
	}

	public class TestRenderer2_Ext extends TestRenderer2
	{
		@Override
		public void render(TestEncounter<TestData2> encounter)
		{
		}
	}

	public abstract class AbstractTestRenderer<T extends NotificationData> implements TestRenderer<T>
	{
	}

	public class TestData3 implements NotificationData
	{
	}

	public class TestRenderer3 extends AbstractTestRenderer<TestData3>
	{
		@Override
		public void render(TestEncounter<TestData3> encounter)
		{
		}
	}
}
