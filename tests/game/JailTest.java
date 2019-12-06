package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cards.CommunityChest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


class JailTest {
	private Player p1 = new Player("P1", "Red");
	private InputStream instructionInputStream;
	
	@BeforeEach
	void setup(){
		Board.initialiseBoard();
	}
	@AfterEach
    void tearDown() {
		Board.clearBoard();
		p1.getJailCard().clear();
	}

	@Test
	void testSendToJail() {
		Jail.sendToJail(p1);
		assertTrue(p1.isInJail());
	}
	
	@Test
	void testRemoveFromJail() {
		String Instruction1 = "y\r\n";
		instructionInputStream = new ByteArrayInputStream(Instruction1.getBytes());
		System.setIn(instructionInputStream);
		Jail.sendToJail(p1);
		assertTrue(p1.isInJail());
		Jail.removeFromJail(p1,false,null);
		assertFalse(p1.isInJail());
	}

	@Test
	void testHandleJailMove() {
		System.out.println("Exit Jail by paying Fine");
		System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "5 ENTER 1 ENTER (If prompted to buy property) n ENTER"
				);
		Jail.sendToJail(p1);

		String Instruction1 = "5\r\n1\r\ny\r\n";
		instructionInputStream = new ByteArrayInputStream(Instruction1.getBytes());
		System.setIn(instructionInputStream);

		Jail.handleJailMove(p1);
		assertFalse(p1.isInJail());
		
		System.out.println("Exit Jail by using get out of jail card");
		System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "5 ENTER 2 ENTER (If prompted to buy property) n ENTER"
				);
		
		Jail.sendToJail(p1);
		CommunityChest temp = new CommunityChest("GET_OUT_OF_JAIL","Get out of jail free. This card may be kept until needed or sold",0);
		Board.communityChests.set(0, temp);
		p1.pickCommChestCard();

		String Instruction2 = "5\r\n2\r\ny\r\n";
		instructionInputStream = new ByteArrayInputStream(Instruction2.getBytes());
		System.setIn(instructionInputStream);

		Jail.handleJailMove(p1);
		assertFalse(p1.isInJail());
		
		System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "5 ENTER 1 ENTER(If prompted to buy property) n ENTER");
		System.out.println("Test not enough money for fine");
		Jail.sendToJail(p1);
		p1.setMoney(40);

		String Instruction3 = "5\r\n1\r\ny\r\n";
		instructionInputStream = new ByteArrayInputStream(Instruction3.getBytes());
		System.setIn(instructionInputStream);

		Jail.handleJailMove(p1);
		assertTrue(p1.isInJail());
		p1.setInJail(false);
		p1.setJailMoves(0);
		
//		System.out.println("*********\n"+
//				"Player 1 Press:\n"
//				+ "5 ENTER 0 ENTER 5 ENTER 0 ENTER 5 ENTER (If prompted to buy property) n ENTER"
//				);
//		System.out.println("Roll three times, without double");
//		Jail.sendToJail(p1);
//		p1.setMoney(1500);
//		String Instruction4 = "5\r\n0\r\n";
//		instructionInputStream = new ByteArrayInputStream(Instruction4.getBytes());
//		System.setIn(instructionInputStream);
//
//		Jail.handleJailMove(p1);
//		System.in.reset();
//		Jail.handleJailMove(p1);
//
//		String Instruction5 = "5\r\n0\r\nn\r\n";
//		instructionInputStream = new ByteArrayInputStream(Instruction5.getBytes());
//		System.setIn(instructionInputStream);
//		Jail.handleJailMove(p1);
//		assertFalse(p1.isInJail());
	}

}
