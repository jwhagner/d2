package tests;

import static org.junit.Assert.*;

import com.laboon.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTestsHalf {
	
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

}
