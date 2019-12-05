package squares;


//import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import squares.Property;
import squares.Square;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
    private Square squareNew;
    @BeforeEach
    void setUp() {
        squareNew = new Property(5,"Green","Ealing Broadway");
    }

    @Test
    void testGetLocation() {
        int expectedLocation = 5;
        assertEquals(expectedLocation,squareNew.getLocation(),"Checking location as Square 5");
    }

    @Test
    void testGetBuyStatus() {
        boolean testStatus = true;
        assertEquals(testStatus,squareNew.getBuyStatus(),"Checking if True");
    }

    @Test
    void testGetName() {
        String nameTest = "Ealing Broadway";
        assertSame(nameTest,squareNew.getName(),"Checking name is correct");
        nameTest = "Piccadilly";
        assertNotSame(nameTest,squareNew.getName(),"Checking name is different");
    }

    @Test
    void testGetSquareType() {
        assertEquals(Square.SquareType.PROPERTY, squareNew.getSquareType(),"Checking Square type is property");
    }

    @Test
    void testSetSquareType() {
        squareNew.setSquareType(Square.SquareType.UTILITY);
        assertEquals(Square.SquareType.UTILITY, squareNew.getSquareType(),"Checking it can be set to UTILITY");
    }

    @Test
    void testSetLocation() {
        squareNew.setLocation(10);
        assertEquals(10,squareNew.getLocation(),"Checking Location is 10");
    }

    @Test
    void testSetBuyStatus() {
        squareNew.setBuyStatus(false);
        assertEquals(false, squareNew.getBuyStatus(), "Checking buy status can be set to false");
    }

    @Test
    void testSetName() {
        squareNew.setName("Park Lane");
        assertSame("Park Lane", squareNew.getName(),"Checking name can be changed");
    }

    @AfterEach
    void tearDown() {
        squareNew = null;
    }
}
