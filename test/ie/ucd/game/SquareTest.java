package ie.ucd.game;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
    private Square squareNew;
    @BeforeEach
    void setUp() {
        squareNew = new Property(5,"Green","Ealing Broadway");
    }

    @Test
    void getLocation() {
        int expectedLocation = 5;
        assertEquals(expectedLocation,squareNew.getLocation(),"Checking location as Square 5");
    }

    @Test
    void getBuyStatus() {
        boolean testStatus = true;
        assertEquals(testStatus,squareNew.getBuyStatus(),"Checking if True");
    }

    @Test
    void getName() {
        String nameTest = "Ealing Broadway";
        assertSame(nameTest,squareNew.getName(),"Checking name is correct");
        nameTest = "Piccadilly";
        assertNotSame(nameTest,squareNew.getName(),"Checking name is different");
    }

    @Test
    void getSquareType() {
        assertEquals(Square.SquareType.PROPERTY, squareNew.getSquareType(),"Checking Square type is property");
    }

    @Test
    void setSquareType() {
        squareNew.setSquareType(Square.SquareType.UTILITY);
        assertEquals(Square.SquareType.UTILITY, squareNew.getSquareType(),"Checking it can be set to UTILITY");
    }

    @Test
    void setLocation() {
        squareNew.setLocation(10);
        assertEquals(10,squareNew.getLocation(),"Checking Location is 10");
    }

    @Test
    void setBuyStatus() {
        squareNew.setBuyStatus(false);
        assertEquals(false, squareNew.getBuyStatus(), "Checking buy status can be set to false");
    }

    @Test
    void setName() {
        squareNew.setName("Park Lane");
        assertSame("Park Lane", squareNew.getName(),"Checking name can be changed");
    }

    @AfterAll
    void tearDown() {
        squareNew = null;
    }
}