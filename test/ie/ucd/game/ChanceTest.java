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
        int expectedValuePay= initialMoney - chanceTest.getCardValue();

        int currLocation = playerTest.getLocation();
        chanceTest.dealWithCard(playerTest);
        //PAY
        assertEquals(expectedValuePay, playerTest.getMoney(),"Check money is reduced from Player");
        //INCOME
        int expectedValueIncome = expectedValuePay + chanceTest.getCardValue();;
        chanceTest.setCardType("INCOME");
        chanceTest.dealWithCard(playerTest);
        assertEquals(expectedValueIncome,playerTest.getMoney(),"Checking income works");
        //MOVE
        chanceTest.setCardType("MOVE");
        chanceTest.setCardValue(5);
        chanceTest.dealWithCard(playerTest);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
        //GET OUT OF JAIL FREE
        chanceTest.setCardType("GET_OUT_OF_JAIL");
        chanceTest.dealWithCard(playerTest);
        assertTrue(playerTest.getJailCard().size()>0);
        //JAIL
        chanceTest.setCardType("JAIL");
        chanceTest.dealWithCard(playerTest);
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() {
        chanceTest = null;
    }
}