package ie.ucd.game;
import java.util.Scanner;

import ie.ucd.game.*;


public class Checks {
	

	public static boolean canBuy(CanOwn ownableCard, Player player) {
		if(ownableCard.getOwner() == null) {
			return true;
		}
		else {
			return false;
		}
		}
	
	public static boolean yesNoInput(String message,Player player) {
		Scanner yesNoScanner = new Scanner(System.in);
		System.out.println(message);
		String acknowledgement = yesNoScanner.next();
		
		while(!(acknowledgement.equalsIgnoreCase("y") || acknowledgement.equalsIgnoreCase("n"))) {
			System.out.println(player.getName()+", please enter a valid response (y/n)");
			acknowledgement = yesNoScanner.next();
		}
		if(acknowledgement.equalsIgnoreCase("y")){
			
			return true;
		}
		else {
			
			return false;
			}
		
		
	}
	
	}
