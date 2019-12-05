package ie.ucd.cards;
//In the case of a purchase, the title deed card will be transferred to the user who now owns the property.
//Thus, mortgage and priceBuy will now be moved from CanOwn to their title deed card
//The title deed card will be associated with the Square of the Property or Utility

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ie.ucd.game.Game;
import ie.ucd.game.Player;
import ie.ucd.operations.InputOutput;
import ie.ucd.squares.CanOwn;
/**
 * The Title Deed class descirbes the title deed card of a property, train station or utility site such as a Water Works.
 * This title deed card is exactly the same as you would have in a real game of Monopoly if you owned one of these sites.
 * It includes details such as the square's colour, the price of buying the site and what site it is linked to on the board
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class TitleDeed extends Card {
    //These are the variables
    private String squareColour; 		//Colour of square
    private int priceBuy;				//Buy Price
    private int[] rents;				//Array of rents
    private int housePrice; 			//Only applicable to properties
    private Player owner;				//The current owner
    private boolean mortgageStatus;		//Whether there is a mortgage
    private CanOwn ownableSite;			//The site its linked to
    private HashMap<Integer, Player> bankruptcyTradeStatus = new HashMap<>();
    private int mortgage;				//Value of mortgage

    /**
     * The class constructor is what we use to create the title deed card and it takes a lot of arguments to the higher parent
     * class of Card but also edits the class variables unique to the Title Deed card
     *
     * @param cardType This is given as a string "Title Deed"
     * @param cardTitle This is given as the name of the Property, Train Station or Water Works which it belongs to
     * @param cardValue This is set to zero as it has no value
     * @param squareColour This is the colour of the square. Valid for properties only
     * @param priceBuy The price to buy this Title Deed card's site
     * @param rents An array of rents depending on how many houses/hotels you have on a site or how many sites of a certain colour you own
     * @param housePrice The price of building a house on the site
     * @param mortgage The value of the mortgage for the site
     * @param owner A Player type which is used as the owner of this card and thus, the site
     * @param ownableSite The site which this Title Deed card is associated with such as a Property or Train Station
     */
    public TitleDeed(String cardType, String cardTitle, int cardValue, String squareColour, int priceBuy, int[] rents, int housePrice, int mortgage, Player owner, CanOwn ownableSite){
        super(cardType,cardTitle, 0);
        this.squareColour = squareColour;
        this.priceBuy = priceBuy;
        this.rents = rents;
        this.housePrice = housePrice;
        this.mortgage = mortgage;
        this.owner = owner;
        this.mortgageStatus = false;
        this.ownableSite = ownableSite;
    }
    //Can ignore as I needed to override it to implement it
    @Override
    public void dealWithCard(Player player1, BufferedReader userInput) {

    }

    //Setters and Getters for all of the variables
    public CanOwn getOwnableSite(){
        return this.ownableSite;
    }

    public int getHousePrice(){
        return this.housePrice;
    }

    public void setHousePrice(int price){
    	this.housePrice = price;
    }

    public int getPriceBuy(){
        return this.priceBuy;
    }

    public void setpriceBuy(int price){
        this.priceBuy = price;
    }

    public int[] getRents(){
        return this.rents;
    }

    public void setRents(int[] rents){
        this.rents = rents;
    }

    public String getSquareColour(){
        return this.squareColour;
    }

    public void setSquareColour(String squareColour){
        this.squareColour = squareColour;
    }

    public Player getOwner(){
        return this.owner;
    }

    public void setOwner(Player newOwner){
        this.owner = newOwner;
    }

    public boolean getMortgageStatus(){
        //If the card is faced down, it is mortgaged
        return this.mortgageStatus;
    }

    public void setMortgageStatus(boolean newStatus){
        this.mortgageStatus = newStatus;
    }

    public int getMortgage(){
        return this.mortgage;
    }

    public void setMortgage(int mortgage){
        this.mortgage = mortgage;
    }

    /**
     * This is used to determine the trade status of the site associated with this title deed card and whether it will be enough to make the player not bankrupt
     * A player is agreed with to provisionally receive the property
     * @param agreedPrice The agreed price to pay for the property by the recipient
     * @param recipient The Player object which is given the property if the owner achieves saving themselves from bankruptcy
     */
    public void setBankruptcyTradeStatus(int agreedPrice, Player recipient){
        bankruptcyTradeStatus.put(agreedPrice, recipient);
    }

    public HashMap<Integer, Player> getBankruptcyTradeStatus(){
        return this.bankruptcyTradeStatus;
    }

    /**
     * This is the player auction method which initiates an auction between a number of players and the owner of the property.
     * It increases the bidding pool size and then a number of Player objects can go into a bidding war with each other.
     * The auction ends when somebody enters a value to pay which all of the remaining players in the bidding war don't want to bid against by
     * entering "n" instead of "y"
     * If you enter a lower bid than the current highest bit, it will prompt you to enter a new bid as yours is lower than the current highest
     *
     * @param bankruptPlayer The bankrupt player object who owns the property which is being auctioned off
     */


    public void playerAuction(Player bankruptPlayer, BufferedReader userInput) {
        if(userInput==null) userInput = new BufferedReader(new InputStreamReader(System.in));
        int[] currentAuctionDetails = new int[] {0,0};
        //Adds bidding players
        ArrayList<Player> biddingPlayers = new ArrayList<>(Game.playerList);
        //Iterator to go through the bidding players
        Iterator<Player> it = biddingPlayers.iterator();
        if(bankruptPlayer!=null){
            while(it.hasNext()){
                Player currentPlayer = it.next();
                if(currentPlayer==bankruptPlayer){
                    it.remove();
                }
            }
        }
        //Initiate a bidding pool size
        int biddingPoolSize = biddingPlayers.size();

        while(biddingPoolSize > 1){
            //update the bidding pool size
            for (int i = 0; i< biddingPoolSize; i++) {
                if(biddingPoolSize==1 && currentAuctionDetails[0]>0){
                    break;
                }
                //check user has enough funds to create a larger bid than the current highest
                if(biddingPlayers.get(i).getMoney()<= currentAuctionDetails[0]) {
                    //user does not have enough funds to make a winning bid, remove them from the auction pool
                    System.out.println(biddingPlayers.get(i).getName()+" does not have enough funds to make a winning bid on "+this.getCardDesc()
                            +"\nCurrent bid: "+currentAuctionDetails[0]+"\nYour Funds: "+biddingPlayers.get(i).getMoney());
                    biddingPlayers.remove(i);
                    i --;	//reduce index as pool size has decreased

                    biddingPoolSize = biddingPlayers.size(); //update building pool size for while loop
                    currentAuctionDetails[1]--;			 //index of user with winning needs to be reduced by one

                    //if there is only one player remaining, the auction is over and they win, so break from loop
                    if(currentAuctionDetails[0] > 0 && biddingPoolSize == 1) {
                        break;
                    }
                    //if there is multiple remaining players left, return to beginning of
                    else {
                        continue;
                    }
                }

                //if a previous bid has already been made, display to user
                if(currentAuctionDetails[0] > 0) {
                    System.out.println("Current bid: "+currentAuctionDetails[0]+" by "+biddingPlayers.get(currentAuctionDetails[1]).getName());
                }

                //user has indicated intention to bid
                if(InputOutput.yesNoInput((biddingPlayers.get(i).getName()+" would you like to place a bid on "+this.getCardDesc()+"? (y/n)"), biddingPlayers.get(i), userInput)) {
                    //prompt user to enter a bid
                    System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");

                    //read in user bid
                    int temporaryBid = InputOutput.integerMenu(0,biddingPlayers.get(0).getMoney(), userInput);

                    //check the user's bid is greater than current highest bid or that they do not have enough money to make the specified bid
                    while(temporaryBid <= currentAuctionDetails[0]) {
                        //bid is less than current highest bid, prompt for intention to re input bid
                        if(temporaryBid <= currentAuctionDetails[0]) {
                            System.out.println(biddingPlayers.get(i).getName() +
                                    " your bid must be greater than the current bid of: "+currentAuctionDetails[0]);
                        }
                        //check if user has confirmed intention to bid again
                        if(InputOutput.yesNoInput("\nWould you like to make another bid? (y/n)", biddingPlayers.get(i), userInput)) {
                            System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");
                            //read in new bid
                            temporaryBid = InputOutput.integerMenu(0,biddingPlayers.get(0).getMoney(), userInput);
                        }
                        //user has declared intention to NOT bid again, remove from list of current users in auction
                        else {
                            if(i<currentAuctionDetails[1]){
                                currentAuctionDetails[1] --;
                            }
                            biddingPlayers.remove(i);
                            i--;
                            biddingPoolSize=biddingPlayers.size();
                            //reset temporary bid back to highest bid so it is not overwritten
                            temporaryBid = currentAuctionDetails[0];
                            break;
                        }
                    }
                    //update the current highest bid to most recent successful bid
                    currentAuctionDetails[0] = temporaryBid;
                    //update the index of the user who has made the highest bid
                    currentAuctionDetails[1] = i;

                }
                //user has indicated intention to not make a bid, remove from pool and update
                else {
                    if(i<currentAuctionDetails[1]){
                        currentAuctionDetails[1]--;
                    }
                    biddingPlayers.remove(i);
                    i--;
                    biddingPoolSize=biddingPlayers.size();
                }
                //updating bidding pool size
                biddingPoolSize = biddingPlayers.size();
            }
            //only one player remaining in bid pool, assign property to winner
            if (biddingPoolSize == 1) {
                if(bankruptPlayer!=null){
                    System.out.println(this.getCardDesc()+" has been won successfully in the preliminary bankruptcy auction by"+biddingPlayers.get(0).getName());
                    this.bankruptcyTradeStatus.put(currentAuctionDetails[0],  biddingPlayers.get(0));
                    biddingPlayers.get(0).reduceMoney(currentAuctionDetails[0],null);
                }
                else {
                    System.out.println(biddingPlayers.get(0).getName()+" has successfully won "+this.getCardDesc()+" at auction for: "+currentAuctionDetails[0]);
                    // add purchased card also updates the owner of said title deed card
                    biddingPlayers.get(0).addPurchasedTitleDeed(this);
                    biddingPlayers.get(0).reduceMoney(currentAuctionDetails[0], null);
                    break;
                }
            }
        }
        //no player made an intention to bid, property remains with a null owner
        if(this.getOwner() == null) {
            System.out.println("There was no winning bid. "+this.getCardDesc()+" remains unpurchased");
        }
    }
}
