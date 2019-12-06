package squares;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import squares.Train;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class TrainTest {
	private Train train1;
	private InputStream instructionInputStream;
	
	private Player p2;
	@BeforeEach
	void setUp() throws Exception {
		p2 = new Player("P2","blue");
		Board.initialiseBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
		train1 = null;
		p2=null;
	}
	//Buying with no owner
	@Test
	void testBuyPlayerNO_OWNER() {
		String instruction = "y\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
		System.setIn(instructionInputStream);
		System.out.println("\n------------\nTEST: PLEASE ENTER y to BUY TRAIN\n------------");
		Board.trains.get(0).buy(p2, null);
		assertTrue(p2.getTitleDeedList().contains(Board.trains.get(0).getTitleDeedCard()));
	}
	//Buying with owner
	@Test
	void testBuyPlayerOWNER() {
		String instruction = "y\r\n";
		instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
		System.setIn(instructionInputStream);
		System.out.println("\n------------\nTEST: PLEASE ENTER y to BUY TRAIN\n------------");
		Board.trains.get(1).buy(p2, null);
		assertTrue(p2.getTitleDeedList().contains(Board.trains.get(1).getTitleDeedCard()));
	}
	//Test the constructor
	@Test
	void testTrain() {
		train1 = new Train("testConstruct",10);
		assertEquals("testConstruct", train1.getName(),"Checking Name is constructed correctly");
		assertEquals(10,train1.getLocation());
	}

}
