package ie.ucd.test;

import ie.ucd.game.*;

import java.io.IOException;

public class BankruptcyTest {
    public static void main(String[] args) throws IOException {

       Game.playerList = Player.createListPlayers();


        Board.initialiseBoard();
        System.out.println(Board.properties.size()+" "+Game.playerList.size());



        Board.properties.get(0).buy(Game.playerList.get(0));
        Board.properties.get(1).buy(Game.playerList.get(0));
        Board.properties.get(2).buy(Game.playerList.get(0));

        Property.buildHousesHotels(Game.playerList.get(0));
        Board.properties.get(2).mortgage(Game.playerList.get(0),false);

        Game.playerList.get(0).reduceMoney(2500,null);
    }
}
