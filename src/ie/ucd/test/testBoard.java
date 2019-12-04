package ie.ucd.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.game.Board;

class testBoard {

	@BeforeEach
	void setUp() throws Exception {
		Board.initialiseBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
		Board.properties = null;
		Board.trains = null;
		Board.utilities = null;
		Board.communityChests = null;
		Board.chances = null;
		Board.board = null;
	}
	//This is testing all of the other methods in the class as Board.initiliaseBoard calls all of them
	@Test
	void testInitialiseBoard() {
		//Checking properties
		assertEquals("Whitechapel Road", Board.properties.get(1).getName(),"Checking names of properties are added correctly");
		assertEquals("Light blue" , Board.properties.get(2).getSquareColour(),"Checking Square colours are populated");
		assertEquals(22,Board.properties.size(),"Checking size of it is correctly 22");
		//Checking trains
		assertEquals("Marylebone Station", Board.trains.get(0).getName(),"Checking names of train stations are added correctly");
		//Checking utilities
		assertEquals(75, Board.utilities.get(1).getTitleDeedCard().getMortgage(),"Checking mortgages are entered correctly");
		//Checking special squares
		assertEquals("GO", Board.specialSquares.get(0).getType(),"Checking the type of a Special Square is GO");
	}

}
