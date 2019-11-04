package ie.ucd.test;
import java.io.IOException;

import ie.ucd.game.BoardReader;

public class readerTest {
	public static void main(String[] args) throws IOException { 
	
	BoardReader.readProperties();
	BoardReader.readSpecialSquares();
	BoardReader.readUtilities();
	BoardReader.readCommunityChests();
	BoardReader.readChances();
	
	for(int i=0; i<BoardReader.getUtilities().size();i++) {
		System.out.println(BoardReader.getUtilities().get(i).getName());
	}
	}
}
