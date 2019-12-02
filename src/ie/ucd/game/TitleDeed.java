package ie.ucd.game;
//In the case of a purchase, the title deed card will be transferred to the user who now owns the property.
//Thus, mortgage and priceBuy will now be moved from CanOwn to their title deed card
//The title deed card will be associated with the Square of the Property or Utility

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class TitleDeed extends Card {
    //This is the title deed card which extends Card
    private String squareColour;
    private int priceBuy;
    private int[] rents;
    private int housePrice; //In the case of a train or water works, this will be set to Null
    private Player owner;
    private boolean mortgageStatus;
    private CanOwn ownableSite;
    private HashMap<Integer, Player> bankruptcyTradeStatus = new HashMap<>();
    //FIXME need to perhaps add mortgage to here or to another location on the title deed cards
    private int mortgage;
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
    @Override
    public void dealWithCard(Player player1) {

    }
    public CanOwn getOwnableSite(){
        return this.ownableSite;
    }
    //FIXME change out of property
    public int getHousePrice(){
        return this.housePrice;
    }

    public void setHousePrice(int price){ this.housePrice = price; }

    public int getPriceBuy(){
        return this.priceBuy;
    }

    public void setpriceBuy(int price){
        this.priceBuy = price;
    }
    //FIXME change out of Property
    public int[] getRents(){
        return this.rents;
    }

    public void setRents(int[] rents){
        this.rents = rents;
    }
    //FIXME change out of Property
    public String getSquareColour(){
        return this.squareColour;
    }

    public void setSquareColour(String squareColour){
        this.squareColour = squareColour;
    }
    //FIXME change out of CanOwn
    public Player getOwner(){
        return this.owner;
    }

    public void setOwner(Player newOwner){
        this.owner = newOwner;
    }
    //FIXME buy will now be associated with the square........will still be in practice and implemented in the square.......
    //FIXME but instead the card will be handed over
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
    //FIXME isMortgage will no longer be passed in when you check whether to sell houses
    //This is the title deed card which extends Card

    public void setBankruptcyTradeStatus(int agreedPrice, Player recipient){
        bankruptcyTradeStatus.put(agreedPrice, recipient);
    }

    public HashMap<Integer, Player> getBankruptcyTradeStatus(){
        return this.bankruptcyTradeStatus;
    }

    public void playerAuction(Player bankruptPlayer) {
        int[] currentAuctionDetails = new int[] {0,0};
        ArrayList<Player> biddingPlayers = new ArrayList<>(Game.playerList);
        Iterator<Player> it = biddingPlayers.iterator();
        if(bankruptPlayer!=null){
            while(it.hasNext()){
                Player currentPlayer = it.next();
                if(currentPlayer==bankruptPlayer){
                    it.remove();
                }
            }
        }
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
                    //reduce index as pool size has decreased
                    i --;
                    //update building pool size for while loop
                    biddingPoolSize = biddingPlayers.size();
                    //index of user with winning needs to be reduced by one
                    currentAuctionDetails[1] --;

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
                if(InputOutput.yesNoInput((biddingPlayers.get(i).getName()+" would you like to place a bid on "+this.getCardDesc()+"? (y/n)"), biddingPlayers.get(i))) {
                    //prompt user to enter a bid
                    System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");

                    //read in user bid
                    int temporaryBid = InputOutput.integerMenu(0,biddingPlayers.get(0).getMoney());

                    //check the user's bid is greater than current highest bid or that they do not have enough money to make the specified bid
                    while(temporaryBid <= currentAuctionDetails[0]) {
                        //bid is less than current highest bid, prompt for intention to re input bid
                        if(temporaryBid <= currentAuctionDetails[0]) {
                            System.out.println(biddingPlayers.get(i).getName() +
                                    " your bid must be greater than the current bid of: "+currentAuctionDetails[0]);
                        }
                        //check if user has confirmed intention to bid again
                        if(InputOutput.yesNoInput("\nWould you like to make another bid? (y/n)", biddingPlayers.get(i))) {
                            System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");
                            //read in new bid
                            temporaryBid = InputOutput.integerMenu(0,biddingPlayers.get(0).getMoney());
                        }
                        //user has declared intention to NOT bid again, remove from list of current users in auction
                        else {
                            if(i<currentAuctionDetails[1]){
                                currentAuctionDetails[1] --;
                            }
                            biddingPlayers.remove(i);
                            i --;
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
