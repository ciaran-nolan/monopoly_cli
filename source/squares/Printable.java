package squares;
/**
 * Interface called Printable which shows the data of an instance
 * @author Robert Keenan and Ciaran Nolan
 *
 */

public interface Printable {
    /**
     * This method displays data about a particular class that implements this interface
     * Example: Property
     *
     */
     void printInstanceData();

    /**
     * This shows whether a titleDeed is available for purchase
     *
     * @return true if no owner, false if there is an owner
     */
     boolean canBuy ();



}
