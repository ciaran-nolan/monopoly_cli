package ie.ucd.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChecksTest {
    private Player player = new Player("Player", "Red");
    @BeforeEach
    void setUp() {
        BoardReader.initialiseBoard();
    }

    @Test
    void enoughFunds() {
        player.setMoney(10);
        assertFalse(Checks.enoughFunds(player,100));
        player.setMoney(200);
        assertTrue(Checks.enoughFunds(player,100));
        player.setMoney(1500);
    }

    @Test
    void canBuy() {
      TitleDeed testTitleDeed = BoardReader.properties.get(0).getTitleDeedCard();
      assertTrue(Checks.canBuy(testTitleDeed, player));
      player.addPurchasedTitleDeed(testTitleDeed);
      assertFalse(Checks.canBuy(testTitleDeed,player));
    }

    @Test
    void isPlayerOwner() {
        player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        assertTrue(Checks.isPlayerOwner(player.getTitleDeedList().get(0), player));
    }

    @Test
    void ownAllColour() {
        player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(BoardReader.properties.get(1).getTitleDeedCard());
        assertFalse(Checks.ownAllColour(player,(Property)player.getTitleDeedList().get(0).getOwnableSite())==null);
        player.removeOwnedTitleDeed(player.getTitleDeedList().get(0));
        assertTrue(Checks.ownAllColour(player,(Property)player.getTitleDeedList().get(0).getOwnableSite())==null);
    }

    @Test
    void evenHouseDistribution() {
        player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(BoardReader.properties.get(1).getTitleDeedCard());
        ArrayList<Property> colourGroup = Checks.ownAllColour(player, (Property)player.getTitleDeedList().get(0).getOwnableSite());
        ((Property)player.getTitleDeedList().get(0).getOwnableSite()).setNumHouses(1);
        assertTrue(Checks.evenHouseDistribution(colourGroup,((Property)player.getTitleDeedList().get(1).getOwnableSite()),true));
        assertFalse(Checks.evenHouseDistribution(colourGroup,((Property)player.getTitleDeedList().get(0).getOwnableSite()),true));
    }

    @Test
    void canBuildHousesHotels() {
        player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(BoardReader.properties.get(1).getTitleDeedCard());
        assertEquals(0, Checks.canBuildHousesHotels((Property)player.getTitleDeedList().get(0).getOwnableSite(),player));
    }

    @Test
    void checkHouseHotelValue() {
        player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(BoardReader.properties.get(1).getTitleDeedCard());
        BoardReader.properties.get(0).setNumHouses(4);
        BoardReader.properties.get(1).setNumHouses(3);
        assertEquals(350/2,Checks.checkHouseHotelValue(player));
    }

    @Test
    void checkMortgagingValue() {
        player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(BoardReader.properties.get(1).getTitleDeedCard());
        assertEquals(60, Checks.checkMortgagingValue(player));
    }

    @Test
    void checkBankruptcyTradeValue() {
    }
}