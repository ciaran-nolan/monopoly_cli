package ie.ucd.test;
import ie.ucd.game.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanOwnTest {
    private  Player p1 = new Player("P1","Red");
    @BeforeEach
    public void setup(){
        Board.initialiseBoard();
    }

    @Test
    public void testGetTitleDeedCard() {
    	Card card = Board.properties.get(3).getTitleDeedCard();
    	assertTrue(card instanceof TitleDeed,"Card obtained is a Title Deed Card");
    }

    @Test
    public void testSetTitleDeedCard() {
    }

    @Test
    public void testMortgage() {
        Board.properties.get(0).mortgage(p1,false);
        assertTrue(Board.properties.get(0).getTitleDeedCard().getMortgageStatus());
    }

    @Test
    public void testDemortgage() {
        Board.properties.get(0).mortgage(p1,false);
        assertTrue(Board.properties.get(0).getTitleDeedCard().getMortgageStatus());
        Board.properties.get(0).demortgage(false);
        assertFalse(Board.properties.get(0).getTitleDeedCard().getMortgageStatus());

    }
	@AfterEach
	void tearDown() throws Exception {
		p1 = null;
	}
}

	