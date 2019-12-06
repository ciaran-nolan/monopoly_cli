package squares;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import cards.Card;
import cards.TitleDeed;
import game.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CanOwnTest {
    private  Player p1 = new Player("P1","Red");
    private InputStream instructionInputStream;
    @BeforeEach
    void setup(){
        Board.initialiseBoard();
    }

    @AfterEach
    void tearDown(){
        Board.clearBoard();
    }

    @Test
    void testGetTitleDeedCard() {
    	Card card = Board.properties.get(3).getTitleDeedCard();
        assertNotNull(card, "Card obtained is a Title Deed Card");
    }

    @Test
    void testSetTitleDeedCard() {
    }

    //Test mortgaging
    @Test
    void testMortgage() {
        String instruction = "y\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
    	Board.properties.get(3).getTitleDeedCard().setOwner(p1);
        Board.properties.get(3).mortgage(p1,false);
        assertTrue(Board.properties.get(3).getTitleDeedCard().getMortgageStatus());
    }

    //Test removing the mortgage
    @Test
    void testDemortgage() {
        String instruction = "y\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
    	Board.properties.get(4).getTitleDeedCard().setOwner(p1);
        Board.properties.get(4).mortgage(p1,false);
        assertTrue(Board.properties.get(4).getTitleDeedCard().getMortgageStatus());
        Board.properties.get(4).demortgage(false,false);
        System.out.println(Board.properties.get(4).getTitleDeedCard().getMortgageStatus());
        assertFalse(Board.properties.get(4).getTitleDeedCard().getMortgageStatus());
    }
}

	