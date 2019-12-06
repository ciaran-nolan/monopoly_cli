package cards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import game.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;



class ChanceTest {
    private Chance chanceTest;
    private Player playerTest;
    @BeforeEach
    void setUp() {
        chanceTest = new Chance("PAY","Pay £100 in taxes",100);
        playerTest = new Player("John", "blue");
    }

    @AfterEach
    void tearDown()  {
        chanceTest = null;
        playerTest = null;
    }

    @Test
    void testDealWithCardPAY() {
        //Testing a Chance card with Pay
        Player playerTest = new Player("John", "blue");
        int initialMoney = playerTest.getMoney();
        int expectedValuePay= initialMoney - chanceTest.getCardValue();
        chanceTest.dealWithCard(playerTest, null);
        //PAY
        assertEquals(expectedValuePay, playerTest.getMoney(),"Check money is reduced from Player");
    }
    @Test
    void testDealWithCardINCOME() {
        //Checking an INCOME chest card
    	int initialMoney = playerTest.getMoney();
        int expectedValueIncome = initialMoney + chanceTest.getCardValue();
        chanceTest.setCardType("INCOME");
        chanceTest.dealWithCard(playerTest, null);
        assertEquals(expectedValueIncome,playerTest.getMoney(),"Checking income works");
    }
    @Test
    void testDealWithCardMOVE() {
        //Checking a Move Chance card
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
        //GET OUT OF JAIL FREE card test
        chanceTest.setCardType("GET_OUT_OF_JAIL");
        chanceTest.dealWithCard(playerTest, null);
        assertNotEquals(0, playerTest.getJailCard().size(), "Checking the player has received a Jail card");
    }
    @Test
    void testDealWithCardJAIL() {
        //JAIL testing in jail
        chanceTest.setCardType("JAIL");
        chanceTest.dealWithCard(playerTest, null);
        assertTrue(playerTest.isInJail());
    }
}