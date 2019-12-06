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
	//Testing  yes no inputs
	@Test
	void testYesNoInput() {
		//Trying incorrect answer first
	    String answer = "a\r\ny\r\n";
	    answerInputStream = new ByteArrayInputStream(answer.getBytes());
	    System.setIn(answerInputStream);
		boolean answerYesNo = InputOutput.yesNoInput("Please press y for Yes", player, null);
		assertTrue(answerYesNo);
	}
	//Just prints square info
	@Test
	void testSquareInformation() {
		InputOutput.squareInformation(5);
	}
}
