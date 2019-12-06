package game;
import java.util.Random;

/**
 * This class is for the Dice. Only one instance is created, adhering to the Singleton design method.
 * The instance of the dice is used in multiple classes and tests.
 * We have set it to be a class of type "public final" as we don't want anything to extend it or override it as a class.
 * It contains 3 static integers as class variables which are the roll of dice 1 and dice 2 and also a counter
 * to see how many doubles have been rolled
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public final class Dice {

	public static final Dice dice= new Dice( );
	private static int dice1; 		//The roll of Dice 1
	private static int dice2;		//The roll of Dice 2
	private static int duplicateRollCounter=0;		//A counter to track the number of doubles rolled in a game

	private Dice(){ }
	public static Dice getInstance(){
		return dice;
	}

	/**
	 * Rolling a dice. It uses the Random() function which rolls 2 random values and assigns them to dice1 and dice2 and then prints the output
	 */
	void rollDice(){
		Random rollGenerator = new Random();
		// .nextInt generates between 0 and specified range (exclusively), so its necessary to add 1 to ensure the dice cannot return 0
		dice1 = rollGenerator.nextInt(6)+1; 
		dice2 = rollGenerator.nextInt(6)+1;
		System.out.println("\n-----------------------\n\tDICE ROLL\n-----------------------");
		System.out.println("\tFirst Dice: "+dice1+"\n\tSecond Dice: "+dice2+"\n");
	}
	
	/**
	 * This checks whether a double has been rolled
	 * @return true if dice1==dice2, false if not
	 */
	boolean isDoubleRoll(){
		return dice1 == dice2;
	}

	/**
	 * Gets the current values of the dice after a roll
	 * @return dice1+dice2
	 */
	int getDieVals() {
		//return int containing both die values
		return (dice1 + dice2);
	}
	//Used to set the dice values in testing
	/**
	 * Sets the dice values for testing purposes
	 * @param dice1Val value of first die
	 * @param dice2Val value of second die
	 */
	public void setDieVals(int dice1Val, int dice2Val) {
		//Used in testing
		dice1 = dice1Val;
		dice2 = dice2Val;
	}
	
	/**
	 * Sets the duplicate Roll counter
	 * @param rollCount Just a number of doubles rolled
	 */
	void setDuplicateRollCounter(int rollCount) {
		 duplicateRollCounter=rollCount;
	}
	
	/**
	 * Returns the counter of doubles rolled
	 * @return duplicateRollCounter
	 */
	int getDuplicateRollCounter() {
		 return duplicateRollCounter;
	}
	/**
	 * Checks if a third double has been rolled and then sends the player to jail
	 * @param player The player who has rolled a double 3 times and will be sent to jail
	 * @return true if duplicateRollCounter==3, false if not
	 */
	boolean isThirdDouble(Player player){
		if (duplicateRollCounter == 3) {
			//put player to jail on roll of the third double
			System.out.println("You have rolled doubles for the third time.");
			Jail.sendToJail(player);
			duplicateRollCounter = 0;
			return true;
		}
		return false;
	}
	
	/**
	 * Method which handles a player's roll. If the roll is a double, it increments the duplicateRollCounter by one and checks if it is the third 
	 * roll calling isThirdDouble()
	 * If it is not a double, it sets the duplicate roll counter to zero. 
	 * @param player The player who has rolled
	 * @return !isThirdDouble(player) if it is a double roll, false if not
	 */
	boolean handlePlayerRoll(Player player){
		rollDice();			//Rolls dice
		//Checks if it is a double roll
		if (isDoubleRoll()) {
			duplicateRollCounter++;		//Increments the counter
			return !isThirdDouble(player);	//Checks if it is the third double rolled

		} else {
			duplicateRollCounter = 0;		//Resets the duplicate Roll counter
			return false;					//Returns false
		}
	}
}
