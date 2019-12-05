package operations;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Player;

class InputOutputTest {
	private InputStream answerInputStream;
	 private Player player;
	@BeforeEach
	void setUp() throws Exception {
		Board.initialiseBoard();
		player = new Player("P1","red");
	}

	@AfterEach
	void tearDown() throws Exception {
		Board.clearBoard();
		player = null;
	}

	@Test
	void testYesNoInput() {
		//Trying incorrect answer first
	    String answer = "a\r\ny\r\n";
	    answerInputStream = new ByteArrayInputStream(answer.getBytes());
	    System.setIn(answerInputStream);
		boolean answerYesNo = InputOutput.yesNoInput("Please press y for Yes", player, null);
		assertTrue(answerYesNo);
	}

	@Test
	void testTitleDeedOperationMenu() {
		String answer = "a\r\ny\r\n";
	    answerInputStream = new ByteArrayInputStream(answer.getBytes());
	    System.setIn(answerInputStream);
	    player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
	    InputOutput.titleDeedOperationMenu(player, "" , housesHotels, userInput);
	}

	@Test
	void testSquareInformation() {
		
	}

	@Test
	void testIntegerMenu() {
		fail("Not yet implemented");
	}

	@Test
	void testHandleUserOption() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectPlayerMenu() {
		fail("Not yet implemented");
	}

}
