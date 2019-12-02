
package ie.ucd.test;

import ie.ucd.game.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player = new Player("P1","Red");
    @Test
    void testGetName() {
        assertTrue(player.getName().equals("P1"));
    }

    @Test
    void testGetToken() {
        assertTrue(player.getToken().equals("Red"));
    }

    @Test
    void testGetMoney() {
        assertEquals(1500,player.getMoney());
    }

    @Test
    void testGetLocation() {
        player.setLocation(20);
        assertEquals(20,player.getLocation());
    }

    @Test
    void testGetTitleDeedList() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
        System.out.println(player.getTitleDeedList().get(0).getCardDesc());
        assertTrue(player.getTitleDeedList().get(0).getCardDesc().equals("Old Kent Road"));
    }

    @Test
    void testGetJailCard() {
    }

    @Test
    void testAddJailCard() {
    }

    @Test
    void testSetName() {
    }

    @Test
    void testSetMoney() {
        player.setMoney(2000);
        assertEquals(2000,player.getMoney());
    }

    @Test
    void testGetJailMoves() {
        assertEquals(0, player.getJailMoves());
    }

    @Test
    void testSetJailMoves() {
        player.setJailMoves(2);
        assertEquals(2, player.getJailMoves());
    }

    @Test
    void testSetToken() {
        player.setToken("Green");
        assertEquals("Green", player.getToken());
    }

    @Test
    void testAddMoney() {
        player.addMoney(1000);
        assertEquals(2500,player.getMoney());

    }

    @Test
    void testSetLocation() {
        player.setLocation(25);
        assertEquals(25,player.getLocation());
    }

    @Test
    void testReduceMoney() {
        player.reduceMoney(100,null);
        assertEquals(1400, player.getMoney());
    }

    @Test
    void testMovePlayer() {
        player.movePlayer(10);
        assertEquals(10,player.getLocation());
    }

    @Test
    void testMoveToSquare() {
        player.moveToSquare(25);
        assertEquals(25,player.getLocation());
    }

    @Test
    void testPickCommChestCard() {

    }

    @Test
    void testPickChanceCard() {
    }

    @Test
    void testIsInJail() {
        assertFalse(player.isInJail());
        player.setInJail(true);
        assertTrue(player.isInJail());
    }

    @Test
    void testSetInJail() {
        player.setInJail(true);
        assertTrue(player.isInJail());
    }


    @Test
    void testCSreateListPlayers() {
//        String input = "2\r\nr,red\r\nb,blue\r\n";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        Player.createListPlayers();
    }

    @Test
    void testBankrupt() {
    }

    @Test
    void testAddPurchasedTitleDeed() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
    }

    @Test
    void testRemoveOwnedTitleDeed() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
        player.removeOwnedTitleDeed(player.getTitleDeedList().get(0));
        assertEquals(0,player.getTitleDeedList().size());
    }

    @Test
    void testPayRent() {

    }

    @Test
    void testBankruptcyMortgage() {
    }

    @Test
    void testBankruptcySellHousesHotels() {
    }

    @Test
    void testCompleteBankruptcyTrade() {
    }

    @Test
    void testSaveFromBankruptcy() {
    }

    @Test
    void testClearBankruptcyTradeStatus() {
    }
}