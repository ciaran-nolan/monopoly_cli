package ie.ucd.game;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player = new Player("P1","Red");
    @Test
    void getName() {
        assertTrue(player.getName().equals("P1"));
    }

    @Test
    void getToken() {
        assertTrue(player.getToken().equals("Red"));
    }

    @Test
    void getMoney() {
        assertEquals(1500,player.getMoney());
    }

    @Test
    void getLocation() {
        player.setLocation(20);
        assertEquals(20,player.getLocation());
    }

    @Test
    void getTitleDeedList() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
        System.out.println(player.getTitleDeedList().get(0).getCardDesc());
        assertTrue(player.getTitleDeedList().get(0).getCardDesc().equals("Old Kent Road"));
    }

    @Test
    void getJailCard() {
    }

    @Test
    void addJailCard() {
    }

    @Test
    void setName() {
    }

    @Test
    void setMoney() {
        player.setMoney(2000);
        assertEquals(2000,player.getMoney());
    }

    @Test
    void getJailMoves() {
        assertEquals(0, player.getJailMoves());
    }

    @Test
    void setJailMoves() {
        player.setJailMoves(2);
        assertEquals(2, player.getJailMoves());
    }

    @Test
    void setToken() {
        player.setToken("Green");
        assertEquals("Green", player.getToken());
    }

    @Test
    void addMoney() {
        player.addMoney(1000);
        assertEquals(2500,player.getMoney());

    }

    @Test
    void setLocation() {
        player.setLocation(25);
        assertEquals(25,player.getLocation());
    }

    @Test
    void reduceMoney() {
        player.reduceMoney(100,null);
        assertEquals(1400, player.getMoney());
    }

    @Test
    void movePlayer() {
        player.movePlayer(10);
        assertEquals(10,player.getLocation());
    }

    @Test
    void moveToSquare() {
        player.moveToSquare(25);
        assertEquals(25,player.getLocation());
    }

    @Test
    void pickCommChestCard() {

    }

    @Test
    void pickChanceCard() {
    }

    @Test
    void isInJail() {
        assertFalse(player.isInJail());
        player.setInJail(true);
        assertTrue(player.isInJail());
    }

    @Test
    void setInJail() {
        player.setInJail(true);
        assertTrue(player.isInJail());
    }


    @Test
    void createListPlayers() {
//        String input = "2\r\nr,red\r\nb,blue\r\n";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        Player.createListPlayers();
    }

    @Test
    void bankrupt() {
    }

    @Test
    void addPurchasedTitleDeed() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
    }

    @Test
    void removeOwnedTitleDeed() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
        player.removeOwnedTitleDeed(player.getTitleDeedList().get(0));
        assertEquals(0,player.getTitleDeedList().size());
    }

    @Test
    void payRent() {

    }

    @Test
    void bankruptcyMortgage() {
    }

    @Test
    void bankruptcySellHousesHotels() {
    }

    @Test
    void completeBankruptcyTrade() {
    }

    @Test
    void saveFromBankruptcy() {
    }

    @Test
    void clearBankruptcyTradeStatus() {
    }
}