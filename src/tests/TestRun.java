package tests;

import com.laboon.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRun {
	
	
	@Test
	public void testGetSugar() {
		fail("Not implemented");
	}
	
	// Tests Game.doSomething() with the input 'N'
	@Test
	public void testDoSomethingNorth() {
		House mockHouse = mock(House.class);
		Game g = new Game(new Player(), mockHouse);
		int retValue = g.doSomething("N");
		verify(mockHouse, times(1)).moveNorth();
		
		assertEquals(retValue, 0);
	}
	
	// Tests Game.doSomething() with the input 'S'
	@Test
	public void testDoSomethingSouth() {
		House mockHouse = mock(House.class);
		Game g = new Game(new Player(), mockHouse);
		int retValue = g.doSomething("S");
		verify(mockHouse, times(1)).moveSouth();
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void testDoSomethingLook() {
		fail("Not implemented");
	}

	@Test
	public void testDoSomethingInventory() {
		fail("Not implemented");
	}
	
	@Test
	public void testDoSomethingDrink() {
		fail("Not implemented");
	}
	
	@Test
	public void testDoSomethingHelp() {
		fail("Not implemented");
	}
	
	// Tests House.getCurrentRoomInfo()
	@Test
	public void testGetCurrentRoomInfo() {
		House h = new House(5);
		String message = h.getCurrentRoomInfo();
		
		Assert
	}

}
