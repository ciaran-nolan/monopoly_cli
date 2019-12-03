/**
 * Class to implement to simulation of a dice throw for two die, and to implement the required monopoly rules around multiple duplicate throws
 * i.e. 3 Duplicate rolls results in the player being sent directly to jail
*/
package ie.ucd.game;
import java.util.Random;

public final class Dice {

	private static int dice1;
	private static int dice2;
	private static int duplicateRollCounter=0;

	//roll a dice
	public static void rollDice(){
		Random rollGenerator = new Random();
		// .nextInt generates between 0 and specified range (exclusively), so its necessary to add 1 to ensure the dice cannot return 0
		dice1 = rollGenerator.nextInt(6)+1; 
		dice2 = rollGenerator.nextInt(6)+1;
		System.out.println("\n-----------------------\n\tDICE ROLL\n-----------------------");
		System.out.println("\tFirst Dice: "+dice1+"\n\tSecond Dice: "+dice2+"\n");
	}
	//check if doubles are rolled
	public static boolean isDoubleRoll(){
		return dice1 == dice2;
	}

	//get the value of the die
	public static int getDieVals() {
		//return object containing both die values
		return (dice1 + dice2);
	}
	public static void setDieVals() {
		//return object containing both die values
		dice1 = 2;
		dice2 = 3;
	}
	//check if the third double ha been rolled
	private static boolean isThirdDouble(Player player){
		if (duplicateRollCounter == 3) {
			//put player to jail on roll of the third double
			System.out.println("You have rolled doubles for the third time.");
			Jail.sendToJail(player);
			duplicateRollCounter = 0;
			return true;
		}
		return false;
	}
	//method to handle a normal player roll
	static boolean handlePlayerRoll(Player player){
		rollDice();
		if (isDoubleRoll()) {
			duplicateRollCounter++;
			return !isThirdDouble(player);

		} else {
			duplicateRollCounter = 0;
			return false;
		}
	}
}
