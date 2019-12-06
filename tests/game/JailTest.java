package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;


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
	//Removing from jail
	@Test
	void testRemoveFromJail() {
		String Instruction1 = "y\r\n";
		InputStream instructionInputStream = new ByteArrayInputStream(Instruction1.getBytes());
		System.setIn(instructionInputStream);
		Jail.sendToJail(p1);
		assertTrue(p1.isInJail());
		Jail.removeFromJail(p1,false,null);
		assertFalse(p1.isInJail());
	}
}
