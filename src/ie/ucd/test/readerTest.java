package ie.ucd.test;
import java.io.IOException;

import ie.ucd.game.Board;

public class readerTest {
	public static void main(String[] args) throws IOException { 
	
	Board.readProperties();
	Board.readSpecialSquares();
	Board.readUtilities();
	Board.readCommunityChests();
	Board.readChances();
	
	for(int i = 0; i< Board.getUtilities().size(); i++) {
		System.out.println(Board.getUtilities().get(i).getName());
	}
	}
}
