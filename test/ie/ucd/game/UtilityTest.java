package ie.ucd.game;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UtilityTest {
    private Utility utilityNew;
    @BeforeEach
    void setUp() throws Exception {
        //In this I will create a new Utility
        utilityNew = new Utility("Water Works",4,200,20, new int[]{4,10} , null);
    }

    @Test
    void getRent() throws Exception {
        int[] expectedRent = new int[]{4,10};
        assertEquals("Expected value: "+expectedRent, "Actual value: " +utilityNew.getRent());
    }

    @Test
    void setRent() throws Exception {
        int[] expectedRent = new int[]{10,20};
        int[] overfillRent = new int[]{10,20,30,40};
        utilityNew.setRent(expectedRent);
        //FIXME @@ciarannolan perhaps could check could we overfill the array
        assertEquals(expectedRent, utilityNew.getRent(),"Checking Rent is {10,20}");
        //FIXME this should fail
        utilityNew.setRent(overfillRent);
        //FIXME check if this needs to be an expected or check that it does not change
        //Shouldnt be able to overfill rent
        assertNotEquals(expectedRent,utilityNew.getRent(),"Overfilling Rent Array");

    }

    @Test
    void buy() throws  Exception{
        Player player1 = new Player("John", "blue");
    }

    @AfterAll
    void tearDown() {
        utilityNew = null;
    }

}