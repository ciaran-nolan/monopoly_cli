package ie.ucd.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanOwnTest {
    private  Player p1 = new Player("P1","Red");
    @BeforeEach
    public static void setup(){
        Board.initialiseBoard();

    }

    @Test
    void getTitleDeedCard() {
    }

    @Test
    void setTitleDeedCard() {
    }

    @Test
    void mortgage() {
        Board.properties.get(0).mortgage(p1,false);
        assertTrue(Board.properties.get(0).getTitleDeedCard().getMortgageStatus());
    }

    @Test
    void demortgage() {
        Board.properties.get(0).mortgage(p1,false);
        assertTrue(Board.properties.get(0).getTitleDeedCard().getMortgageStatus());
        Board.properties.get(0).demortgage(false);
        assertFalse(Board.properties.get(0).getTitleDeedCard().getMortgageStatus());

    }
}