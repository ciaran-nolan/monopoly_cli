package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceTest {

	private Player player = new Player("P1","Red");
	private static Dice dice = Dice.getInstance();

	@BeforeEach
	void setUp() {
		Board.initialiseBoard();
	}

	@AfterEach
	void tearDown() {
		Board.clearBoard();
	}

	@Test
	void testRollDice() {
		Dice.rollDice();
		assertNotEquals(0, dice.getDieVals());
	}

	@Test
	void testIsDoubleRoll() {
		dice.setDieVals(1, 2);
		assertFalse(dice.isDoubleRoll());
		dice.setDieVals(2, 2);
		assertTrue(dice.isDoubleRoll());
	
	}

	@Test
	void testGetDieVals() {
		dice.setDieVals(1, 1);
		assertEquals(2,dice.getDieVals());
	}

	@Test
	void testSetDieVals() {
		dice.setDieVals(1, 1);
		assertEquals(2,dice.getDieVals());
	}


	@Test
	void testSetDuplicateRollCounter() {
		dice.setDuplicateRollCounter(2);
		assertEquals(2,dice.getDuplicateRollCounter());
	}
	@Test
	void testGetDuplicateRollCounter() {
		dice.setDuplicateRollCounter(2);
		assertEquals(2,dice.getDuplicateRollCounter());
	}
	
	@Test
	void testIsThrirdDouble() {
		dice.setDuplicateRollCounter(3);
		assertTrue(dice.isThirdDouble(player));
		dice.setDuplicateRollCounter(2);
		assertFalse(dice.isThirdDouble(player));
	}
	@Test
	void testHandlePlayerRoll() {
		dice.handlePlayerRoll(player);
	}

}
