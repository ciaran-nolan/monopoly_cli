package ie.ucd.test;

import ie.ucd.game.*;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceTest {
    private Chance chanceTest;
    private Player playerTest = new Player("John", "blue");
    @BeforeEach
    public void setUp() throws Exception {
        chanceTest = new Chance("PAY","Pay €100 in taxes",100);
    }

    @Test
    void testDealWithCardPAY() {
        //In this case I need to test all cases of the chance card
        Player playerTest = new Player("John", "blue");
        //FIXME @@ciarannolan does this need to be static?
        int initialMoney = playerTest.getMoney();
        int expectedValuePay= initialMoney - chanceTest.getCardValue();
        chanceTest.dealWithCard(playerTest);
        //PAY
        assertEquals(expectedValuePay, playerTest.getMoney(),"Check money is reduced from Player");
    }
    @Test
    void testDealWithCardINCOME() {
        //INCOME
    	int initialMoney = playerTest.getMoney();
    	int expectedValuePay= initialMoney - chanceTest.getCardValue();
        int expectedValueIncome = expectedValuePay + chanceTest.getCardValue();;
        chanceTest.setCardType("INCOME");
        chanceTest.dealWithCard(playerTest);
        assertEquals(expectedValueIncome,playerTest.getMoney(),"Checking income works");
    }
    @Test
    void testDealWithCardMOVE() {
        //MOVE
    	int currLocation = playerTest.getLocation();
        chanceTest.setCardType("MOVE");
        chanceTest.setCardValue(5);
        chanceTest.dealWithCard(playerTest);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
    }
    @Test
    void testDealWithCardGETOUTOFJAIL() {
        //GET OUT OF JAIL FREE
        chanceTest.setCardType("GET_OUT_OF_JAIL");
        chanceTest.dealWithCard(playerTest);
        assertTrue(playerTest.getJailCard().size() > 0);
    }
    @Test
    void testDealWithCardJAIL() {
        //JAIL
        chanceTest.setCardType("JAIL");
        chanceTest.dealWithCard(playerTest);
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() throws Exception {
        chanceTest = null;
    }
}