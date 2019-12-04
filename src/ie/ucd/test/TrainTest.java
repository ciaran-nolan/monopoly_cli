package ie.ucd.test;

import ie.ucd.game.*;
import ie.ucd.squares.Train;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainTest {
	private Train train1;
	private Player p1;
	private Player p2;
	@BeforeEach
	void setUp() throws Exception {
		p1 = new Player("P1","red");
		p2 = new Player("P2","blue");
		Board.initialiseBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
		train1 = null;
		p1=null;
		p2=null;
	}

	@Test
	void testBuyPlayerNO_OWNER() {
		System.out.println("\n------------\nTEST: PLEASE ENTER y to BUY TRAIN\n------------");
		Board.trains.get(0).buy(p2);
		assertTrue(p2.getTitleDeedList().contains(Board.trains.get(0).getTitleDeedCard()));
	}
	
	@Test
	void testBuyPlayerOWNER() {
		System.out.println("\n------------\nTEST: PLEASE ENTER y to BUY TRAIN\n------------");
		Board.trains.get(1).buy(p2);
		assertTrue(p2.getTitleDeedList().contains(Board.trains.get(1).getTitleDeedCard()));
	}

	@Test
	void testTrain() {
		train1 = new Train("testConstruct",10);
		assertEquals("testConstruct", train1.getName(),"Checking Name is constructed correctly");
		assertEquals(10,train1.getLocation());
	}

}
