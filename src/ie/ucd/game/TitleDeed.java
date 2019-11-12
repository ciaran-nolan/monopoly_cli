package ie.ucd.game;
//In the case of a purchase, the title deed card will be transferred to the user who now owns the property.
//Thus, mortgage and priceBuy will now be moved from CanOwn to their title deed card
//The title deed card will be associated with the Square of the Property or Utility

public class TitleDeed extends Card {
    //This is the title deed card which extends Card
    private String squareColour;
    private int priceBuy;
    private int[] rents;
    private int housePrice; //In the case of a train or water works, this will be set to Null
    private Player owner;
    private boolean mortgageStatus;
    private CanOwn ownableSite;
    //FIXME need to perhaps add mortgage to here or to another location on the title deed cards
    private int mortgage;
    public TitleDeed(String cardType, String cardTitle, int cardValue, String squareColour, int priceBuy, int[] rents, int housePrice, int mortgage, Player owner,CanOwn ownableSite){
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

    public void setHousePrice(int price){
        this.housePrice = price;
    }

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
        //If the card is faced down, it is
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
}
