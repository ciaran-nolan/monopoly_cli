/**
 * Class to implement to simulation of a dice throw for two die, and to implement the required monopoly rules around multiple duplicate throws
 * i.e. 3 Duplicate rolls results in the player being sent directly to jail
*/
package ie.ucd.game;
import java.util.Random;

public class Dice {

	private int dice1;
	private int dice2;
	private int duplicateRollCounter=0;
	
	public boolean rollDice(){
		Random rollGenerator = new Random();
			
		// .nextInt generates between 0 and specified range (exclusively), so its necessary to add 1 to ensure the dice cannot return 0
		this.dice1 = rollGenerator.nextInt(6)+1; 
		this.dice2 = rollGenerator.nextInt(6)+1;
		
		System.out.println("You have rolled "+this.dice1+" and "+this.dice2);
		if (this.dice1==this.dice2) {
			return true;
		}
		return false;
	}
	
	public int getDieVals() {
		//return object containing both die values
		return (this.dice1 + this.dice2);
	}
	
	public int getDuplicateRollCounter() {
		return duplicateRollCounter;
	}
	public void resetDuplicateRollCounter() {
		duplicateRollCounter = 0;
	}
	public boolean incrementDulicateRollCounter(){
		//method will only increment as long as third duplicate has not been reached
		if (this.duplicateRollCounter >= 2) {
			resetDuplicateRollCounter();
			return true;
		}
		else {
			//third duplicate roll has not yet been reached so increment counter
			duplicateRollCounter+=1;
		}
		return false;
		}
	
}
