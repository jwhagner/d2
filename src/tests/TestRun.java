package tests;

import java.io.ByteArrayInputStream;

import com.laboon.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class TestRun {
	
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	///                                                            ///
	///                      Player Tests                          ///
	///                                                            ///
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	
	@Test
	public void testGetSugar() {
		Player p = new Player(false, true, true);
		p.getSugar();
		assertTrue(p.hasAllItems());
	}
	
	@Test
	public void testGetCream() {
		Player p = new Player(true, false, true);
		p.getCream();
		assertTrue(p.hasAllItems());
	}
	

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	///                                                            ///
	///                      Game Tests                            ///
	///                                                            ///
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	
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
	
	@Test
	public void testDoSomethingInvalid() {
		House mockHouse = mock(House.class);
		Game g = new Game(new Player(), mockHouse);
		int retValue = g.doSomething("a");
		verifyZeroInteractions(mockHouse);
		
		assertEquals(retValue, 0);
	}
	
	// Tests Game.run() with winning conditions
	@Test
	public void testRunWin() {
		Room[] rooms = { new Room( false, false, false, false, false) };
		Game g = new Game(new Player(true, true, true), new House(rooms));
		ByteArrayInputStream userInput = new ByteArrayInputStream("d".getBytes());
		System.setIn(userInput);
		
		int result = g.run();
		assertEquals(result, 0);
	}

	// Tests Game.run() with losing conditions
	@Test
	public void testRunLose() {
		Room[] rooms = { new Room( false, false, false, false, false) };
		Game g = new Game(new Player(false, false, false), new House(rooms));
		ByteArrayInputStream userInput = new ByteArrayInputStream("d".getBytes());
		System.setIn(userInput);
		
		int result = g.run();
		assertEquals(result, 1);
	}
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	///                                                            ///
	///                      House Tests                           ///
	///                                                            ///
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	
	// Tests House.getCurrentRoomInfo() when the player has not moved
	@Test
	public void testGetCurrentRoomInfo() {
		House h = new House(1);
		String message = h.getCurrentRoomInfo();
		assertNotNull(message);
	}

	// Tests House.moveNorth() when there is a door to the north
	@Test
	public void testMoveNorthWithDoor() {
		Room[] rooms = { new Room(false, false, false, true, true),
				new Room(false, false, false, true, true) };
		House h = new House(rooms);
		String message_before = h.getCurrentRoomInfo();
		h.moveNorth();
		String message_after = h.getCurrentRoomInfo();
		assertFalse(message_before.equals(message_after));
	}
	
	// Tests House.moveSouth() when there is a door to the south
	@Test
	public void testMoveSouthWithDoor() {
		Room[] rooms = { new Room(false, false, false, true, false),
				new Room(false, false, false, false, true) };
		House h = new House(rooms);
		String message_before = h.getCurrentRoomInfo();
		h.moveNorth();
		h.moveSouth();
		String message_after = h.getCurrentRoomInfo();
		assertTrue(message_before.equals(message_after));
	}
	
	// Tests House.moveSouth() when there is no door to the south
	// Because the player shouldn't be able to move south, the room
	// description should be the same before and after moving
	@Test
	public void testMoveSouthNoDoor() {
		Room[] rooms = { new Room(false, false, false, false, false) };
		House h = new House(rooms);
		String message_before = h.getCurrentRoomInfo();
		h.moveSouth();
		String message_after = h.getCurrentRoomInfo();
		assertTrue(message_before.equals(message_after));
	}
	
	// Tests House.moveNorth() when there is no door to the north
	@Test
	public void testMoveNorthNoDoor() {
		Room[] rooms = { new Room(false, false, false, false, false) };
		House h = new House(rooms);
		String message_before = h.getCurrentRoomInfo();
		h.moveNorth();
		String message_after = h.getCurrentRoomInfo();
		assertTrue(message_before.equals(message_after));
	}
	
	
	// Tests House.look() in a room with all items, expecting the player to pick all of them up
	// DEPENDANT ON: Room.hasItem(), Room.hasCoffee(), Room.hasCream(), and Room.hasSugar()
	@Test
	public void testLookWithItems() {
		Room[] rooms = { new Room( true, true, true, false, false) };
		House h = new House(rooms);
		Player mockPlayer = mock(Player.class);
		h.look(mockPlayer, rooms[0]);
		verify(mockPlayer, times(1)).getCoffee();
		verify(mockPlayer, times(1)).getCream();
		verify(mockPlayer, times(1)).getSugar();
	}
	
	// Tests House.look() in a room with no items
	// Same dependencies as testLookWithItem()
	@Test
	public void testLookNoItems() {
		Room[] rooms = { new Room( false, false, false, false, false) };
		House h = new House(rooms);
		Player mockPlayer = mock(Player.class);
		h.look(mockPlayer, rooms[0]);
		verify(mockPlayer, times(0)).getCoffee();
		verify(mockPlayer, times(0)).getCream();
		verify(mockPlayer, times(0)).getSugar();	
	}
	
	// Tests House.generateRooms() by generating 5 rooms (indexed 0-4)
	// Expected results: room 0 has cream, room 2 has coffee, room 4 has sugar
	@Test
	public void testGenerateRooms() {
		Room[] rooms = (new House(1)).generateRooms(5);
		boolean correctCream = rooms[0].hasCream();
		boolean correctCoffee = rooms[2].hasCoffee();
		boolean correctSugar = rooms[4].hasSugar();
		assertTrue(correctCream && correctCoffee && correctSugar);
	}
}
