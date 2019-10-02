package ie.ucd.game;
import java.io.IOException;

import ie.ucd.game.BoardReader;

public class readerTest {
	public static void main(String[] args) throws IOException { 
	BoardReader b1 = new BoardReader();
	b1.readProperties();
	b1.readSpecialSquares();
	b1.readUtilities();
	b1.readCommunityChests();
	b1.readChances();
	}
}
