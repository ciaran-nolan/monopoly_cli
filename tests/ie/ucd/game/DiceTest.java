package ie.ucd.game;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.game.Dice;

class DiceTest {

	private Player player = new Player("P1","Red");
	@BeforeEach
	void setUp() throws Exception {
		Board.initialiseBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
		Board.clearBoard();
	}

	@Test
	void testRollDice() {
		Dice.rollDice();
		assertTrue(Dice.getDieVals()>0);
	}

	@Test
	void testIsDoubleRoll() {
		Dice.setDieVals(1, 2);
		assertFalse(Dice.isDoubleRoll());
		Dice.setDieVals(2, 2);
		assertTrue(Dice.isDoubleRoll());
	
	}

	@Test
	void testGetDieVals() {
		Dice.setDieVals(1, 1);
		assertEquals(2,Dice.getDieVals());
	}

	@Test
	void testSetDieVals() {
		Dice.setDieVals(1, 1);
		assertEquals(2,Dice.getDieVals());
	}


	@Test
	void testSetDuplicateRollCounter() {
		Dice.setDuplicateRollCounter(2);
		assertEquals(2,Dice.getDuplicateRollCounter());
	}
	@Test
	void testGetDuplicateRollCounter() {
		Dice.setDuplicateRollCounter(2);
		assertEquals(2,Dice.getDuplicateRollCounter());
	}
	
	@Test
	void testIsThrirdDouble() {
		Dice.setDuplicateRollCounter(3);
		assertTrue(Dice.isThirdDouble(player));
		Dice.setDuplicateRollCounter(2);
		assertFalse(Dice.isThirdDouble(player));
	}
	@Test
	void testHandlePlayerRoll() {
		Dice.handlePlayerRoll(player);
	}

}
