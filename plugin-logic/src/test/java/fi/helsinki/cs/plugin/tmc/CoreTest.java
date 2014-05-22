package fi.helsinki.cs.plugin.tmc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CoreTest {
	Core core;
	
	@Before
	public void setUp(){
		core = Core.getInstance();
	}
	
	@Test
	public void getInstanceReturnSameObjectOnFurtherCalls() {
		core = Core.getInstance();
		assertEquals(core, Core.getInstance());
	}
	
	@Test
	public void settingsNotNullAfterSingletonInitialization() {
		assertNotNull(core.getSettings());
	}
	
	@Test
	public void courseFetcherNotNullAfterSingletonInitialization() {
		assertNotNull(core.getCourseFetcher());
	}
	
	@Test
	public void coursesNotNullAfterSingletonInitialization() {
		assertNotNull(core.getCourses());
	}
	
	@Test
	public void exerciseFetcherNotNullAfterSingletonInitialization() {
		assertNotNull(core.getExerciseFetcher());
	}
	
	@Test
	public void canSetErrorHandler(){
		MyLittleErrorHandler eh = mock(MyLittleErrorHandler.class);
		Core.setMyLittleErrorHandler(eh);
		assertEquals(eh, Core.getErrorHandler());
	}
}