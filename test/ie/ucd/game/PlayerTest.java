package ie.ucd.game;

import org.junit.jupiter.api.Test;

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
    }

    @Test
    void getJailMoves() {
    }

    @Test
    void setJailMoves() {
    }

    @Test
    void setToken() {
    }

    @Test
    void addMoney() {
        player.addMoney(1000);
        assert
    }

    @Test
    void setLocation() {
    }

    @Test
    void reduceMoney() {
    }

    @Test
    void movePlayer() {
    }

    @Test
    void moveToSquare() {
    }

    @Test
    void pickCommChestCard() {
    }

    @Test
    void pickChanceCard() {
    }

    @Test
    void isInJail() {
    }

    @Test
    void setInJail() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void createListPlayers() {
    }

    @Test
    void bankrupt() {
    }

    @Test
    void addPurchasedTitleDeed() {
    }

    @Test
    void removeOwnedTitleDeed() {
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