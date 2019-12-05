package ie.ucd.cards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ie.ucd.cards.Card;
import ie.ucd.cards.Chance;
import ie.ucd.cards.CommunityChest;
import ie.ucd.cards.TitleDeed;

class CardTest {
    private Card commChestTest;
    private Card chanceTest;
    private Card titleDeedTest;
    @BeforeEach
    void setUp() {
        //In this I will set up a Community Chest, Chance and Title Deed Card
        commChestTest = new CommunityChest("PAY","Pay £100 in taxes", 100);
        chanceTest = new Chance("INCOME", "You won £100", 100);
        titleDeedTest = new TitleDeed("Title Deed","Piccadilly",0,"Orange", 200,
                new int[]{4,10,20,30}, 15,20,null, null);
    }

    @Test
    void testGetCardType() {
        String cardTypeTest = "PAY";
        assertEquals(cardTypeTest, commChestTest.getCardType(),"Checking Card Type");
        cardTypeTest = "INCOME";
        assertEquals(cardTypeTest, chanceTest.getCardType(),"Checking Card Type");
    }

    @Test
    void testGetCardDesc() {
        String cardDescTest = "Pay";
        assertNotEquals(cardDescTest,commChestTest.getCardDesc(),"Checking Card Description");
        cardDescTest = "Pay £100 in taxes";
        assertEquals(cardDescTest,commChestTest.getCardDesc(),"Checking Card Description Same");
    }

    @Test
    void testGetCardValue() {
        int cardValueTest = 50;
        assertNotEquals(cardValueTest,commChestTest.getCardValue(),"Checking card value different");
        cardValueTest = 100;
        assertEquals(cardValueTest,commChestTest.getCardValue(),"Checking values are same");
    }

    @Test
    void testSetCardType() {
        chanceTest.setCardType("PAY");
        assertEquals("PAY",chanceTest.getCardType(),"Checking Card type can be changed");
    }

    @Test
    void testSetCardDesc() {
        chanceTest.setCardDesc("You won £20");
        assertEquals("You won £20", chanceTest.getCardDesc(),"Checking card description can be changed");
    }

    @Test
    void testSetCardValue() {
        chanceTest.setCardValue(20);
        assertEquals(20,chanceTest.getCardValue(),"Checking card value can be set to £20");
    }

    //FIXME could change this to be tested in the card class itselg
    @Test
    void testDealWithCard() {
    }
    
    @AfterEach
    void tearDown() {
        commChestTest = null;
        chanceTest = null;
        titleDeedTest = null;
    }
}