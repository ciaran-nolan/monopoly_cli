package ie.ucd.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.cards.CommunityChest;


class JailTest {
	private Player p1 = new Player("P1", "Red");
	
	
	@BeforeEach
	void setup(){
		Board.initialiseBoard();
	}
	@AfterEach
    void tearDown() throws Exception {
		p1.getJailCard().clear();
	}

	@Test
	void testSendToJail() {
		Jail.sendToJail(p1);
		assertTrue(p1.isInJail());
	}
	
	@Test
	void testRemoveFromJail() {
		Jail.sendToJail(p1);
		assertTrue(p1.isInJail());
		Jail.removeFromJail(p1,false);
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
		Jail.handleJailMove(p1);
		assertFalse(p1.isInJail());
		
		System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "5 ENTER 1 ENTER(If prompted to buy property) n ENTER");
		System.out.println("Test not enough money for fine");
		Jail.sendToJail(p1);
		p1.setMoney(40);
		Jail.handleJailMove(p1);
		assertTrue(p1.isInJail());
		p1.setInJail(false);
		p1.setJailMoves(0);
		
		System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "5 ENTER 0 ENTER 5 ENTER 0 ENTER 5 ENTER (If prompted to buy property) n ENTER"
				);
		System.out.println("Roll three times, without double");
		Jail.sendToJail(p1);
		p1.setMoney(1500);
		Jail.handleJailMove(p1);
		Jail.handleJailMove(p1);
		Jail.handleJailMove(p1);
		assertFalse(p1.isInJail());
	}

}
