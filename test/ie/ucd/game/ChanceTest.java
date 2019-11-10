package ie.ucd.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceTest {
    private Chance chanceTest;
    @BeforeEach
    public void setUp() {
        chanceTest = new Chance("PAY","Pay £100 in taxes",100);
    }

    @Test
    void dealWithCard() {
        //In this case I need to test all cases of the chance card
        Player playerTest = new Player("John", "blue");
        //FIXME @@ciarannolan does this need to be static?
        int initialMoney = playerTest.getMoney();
        int currLocation = playerTest.getLocation();
        //PAY
        assertEquals(initialMoney-chanceTest.getCardValue(), playerTest.getMoney(),"Check money is reduced from Player");
        //INCOME
        chanceTest.setCardType("INCOME");
        assertEquals(initialMoney+chanceTest.getCardValue(),playerTest.getMoney(),"Checking income works");
        //MOVE
        chanceTest.setCardType("MOVE");
        chanceTest.setCardValue(5);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
        //GET OUT OF JAIL FREE
        chanceTest.setCardType("GET_OUT_OF_JAIL");
        assertTrue(playerTest.getJailFreeNum()>0);
        //JAIL
        chanceTest.setCardType("JAIL");
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() {
        chanceTest = null;
    }
}