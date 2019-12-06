package game;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Dice;
import game.Player;

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
	//Test rolling the dice
	@Test
	void testRollDice() {
		Dice.rollDice();
		assertNotEquals(0, Dice.getDieVals());
	}
	//Check if a double is rolled
	@Test
	void testIsDoubleRoll() {
		Dice.setDieVals(1, 2);
		assertFalse(Dice.isDoubleRoll());
		Dice.setDieVals(2, 2);
		assertTrue(Dice.isDoubleRoll());
	}

	//Getting the value of the die
	@Test
	void testGetDieVals() {
		Dice.setDieVals(1, 1);
		assertEquals(2,Dice.getDieVals());
	}
	//Setting the dievals. This was purely used for testing
	@Test
	void testSetDieVals() {
		Dice.setDieVals(1, 1);
		assertEquals(2,Dice.getDieVals());
	}
	//Set the duplicate roll counter
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
	//Is the third double
	@Test
	void testIsThrirdDouble() {
		Dice.setDuplicateRollCounter(3);
		assertTrue(Dice.isThirdDouble(player));
		Dice.setDuplicateRollCounter(2);
		assertFalse(Dice.isThirdDouble(player));
	}
	//Handling the roll of a player
	@Test
	void testHandlePlayerRoll() {
		Dice.handlePlayerRoll(player);
	}

}
