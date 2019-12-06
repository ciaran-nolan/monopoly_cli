package squares;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import squares.Square.SquareType;

class SpecialTest {
	private Special squareNew;
	@BeforeEach
	void setUp() {
		squareNew = new Special("Test", 10,false, "TAX", 100, SquareType.SPECIAL);
	}

	@AfterEach
	void tearDown() {
		squareNew = null;
	}
	//Testing constructor
	@Test
	void testSpecial() {
		assertEquals("Test", squareNew.getName(),"Checking name is correct");
		assertEquals(10, squareNew.getLocation(),"Checking Location is correct");
		assertFalse(squareNew.getBuyStatus(),"Checking Buy Status is correct");
		assertEquals("TAX", squareNew.getType(),"Checking Special type is TAX");
		assertEquals(100, squareNew.getValue(),"Checking value is correct");
		assertEquals(SquareType.SPECIAL, squareNew.getSquareType(),"Checking Square type");
	}

	@Test
	void testSetType() {
		squareNew.setType("FREE");
		assertEquals("FREE", squareNew.getType(),"Checking type changed to FREE");
	}

	@Test
	void testSetValue() {
		squareNew.setValue(50);
		assertEquals(50,squareNew.getValue(),"Checking value can be changed");
	}
	//All cases of special square
	@Test
	void testImplementSpecialSquare() {
		Player p1 = new Player("P1", "red");
		//Landed on a TAX Square
		squareNew.implementSpecialSquare(p1);
		assertEquals(1400, p1.getMoney(),"Checking TAX works");
		//Landed on the GO square
		squareNew.setValue(200);
		squareNew.setType("GO");
		squareNew.implementSpecialSquare(p1);
		assertEquals(1600, p1.getMoney(), "Checking landing on GO increases money");
		//Free Parking landed on
		squareNew.setType("FREE");
		assertEquals(1600, p1.getMoney(), "Free parking doesn't change money");
		//Landed on Jail Square
		squareNew.setType("JAIL");
		squareNew.setLocation(11);
		squareNew.implementSpecialSquare(p1);
		assertTrue(p1.isInJail());
	}

}
