package ie.ucd.test;

import ie.ucd.game.Board;
import ie.ucd.game.Jail;
import ie.ucd.game.Player;

import java.io.IOException;

public class JailTest {
    public static void  main(String[] args) throws IOException {
        Board.readProperties();
        Board.readSpecialSquares();
        Board.readUtilities();
        Board.readCommunityChests();
        Board.readChances();

        Player p1 = new Player("p1", "thing1");

        p1.setInJail(true);
        boolean doubleRoll = false;



        Jail.handleJailMove(p1);
        Jail.handleJailMove(p1);
        Jail.handleJailMove(p1);


    }
}
