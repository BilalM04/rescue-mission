package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.position.Coordinate;

public class CoordinateTest {
    private Coordinate coordOne;
    private Coordinate coordTwo;
    private Coordinate coordThree;

    @BeforeEach
    public void setUp() {
        coordOne = new Coordinate(0, 0);
        coordTwo = new Coordinate(1, 49);
        coordThree = new Coordinate(1, 0);
    }

    @Test
    public void testDistanceToTrue() {
        assertEquals(coordOne.distanceTo(coordTwo), Math.sqrt(2402));
    }

    @Test
    public void testDistanceToFalse() {
        assertNotEquals(coordOne.distanceTo(coordThree), 0);
    }

    @Test
    public void testGetX() {
        assertEquals(1, coordThree.getX());
    }

    @Test
    public void testGetY() {
        assertEquals(49, coordTwo.getY());
    }

    @Test
    public void testSetX() {
        coordOne.setX(9);
        assertEquals(coordOne.getX(), 9);
    }

    @Test
    public void testSetY() {
        coordOne.setY(197);
        assertEquals(coordOne.getY(), 197);
    }
}
