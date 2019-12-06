package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JailTest {
	private Player p1 = new Player("P1", "Red");

	@BeforeEach
	void setup(){
		Board.initialiseBoard();
	}
	@AfterEach
    void tearDown() {
		Board.clearBoard();
		p1.getJailCard().clear();
	}
	//Sending to jail
	@Test
	void testSendToJail() {
		Jail.sendToJail(p1);
		assertTrue(p1.isInJail());
	}
}
