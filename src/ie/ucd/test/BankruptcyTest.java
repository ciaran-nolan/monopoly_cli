package ie.ucd.test;

import ie.ucd.game.*;

import java.io.IOException;

public class BankruptcyTest {
    public static void main(String[] args) throws IOException {

       Game.playerList = Player.createListPlayers();


        BoardReader.initialiseBoard();
        System.out.println(BoardReader.properties.size()+" "+Game.playerList.size());



        BoardReader.properties.get(0).buy(Game.playerList.get(0));
        BoardReader.properties.get(1).buy(Game.playerList.get(0));
        BoardReader.properties.get(2).buy(Game.playerList.get(0));

        Property.buildHousesHotels(Game.playerList.get(0));
        BoardReader.properties.get(2).mortgage(Game.playerList.get(0));

        Game.playerList.get(0).reduceMoney(2500,null);
    }
}
