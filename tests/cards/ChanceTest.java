package cards;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cards.Chance;
import game.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ChanceTest {
    private Chance chanceTest;
    private Player playerTest;
    @BeforeEach
    public void setUp() throws Exception {
        chanceTest = new Chance("PAY","Pay £100 in taxes",100);
        playerTest = new Player("John", "blue");
    }

    @Test
    void testDealWithCardPAY() {
        //In this case I need to test all cases of the chance card
        Player playerTest = new Player("John", "blue");
        //FIXME @@ciarannolan does this need to be static?
        int initialMoney = playerTest.getMoney();
        int expectedValuePay= initialMoney - chanceTest.getCardValue();
        chanceTest.dealWithCard(playerTest, null);
        //PAY
        assertEquals(expectedValuePay, playerTest.getMoney(),"Check money is reduced from Player");
    }
    @Test
    void testDealWithCardINCOME() {
        //INCOME
    	int initialMoney = playerTest.getMoney();
        int expectedValueIncome = initialMoney + chanceTest.getCardValue();;
        chanceTest.setCardType("INCOME");
        chanceTest.dealWithCard(playerTest, null);
        assertEquals(expectedValueIncome,playerTest.getMoney(),"Checking income works");
    }
    @Test
    void testDealWithCardMOVE() {
        //MOVE
        String instruction = "n\r\n";
        InputStream instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
        Board.initialiseBoard();
    	int currLocation = playerTest.getLocation();
        chanceTest.setCardType("MOVE");
        chanceTest.setCardValue(5);
        chanceTest.dealWithCard(playerTest, null);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
    }
    @Test
    void testDealWithCardGETOUTOFJAIL() {
        //GET OUT OF JAIL FREE
        chanceTest.setCardType("GET_OUT_OF_JAIL");
        chanceTest.dealWithCard(playerTest, null);
        assertNotEquals(0, playerTest.getJailCard().size(), "Checking the player has received a Jail card");
    }
    @Test
    void testDealWithCardJAIL() {
        //JAIL
        chanceTest.setCardType("JAIL");
        chanceTest.dealWithCard(playerTest, null);
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() throws Exception {
        chanceTest = null;
        playerTest = null;
    }
}