package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.mcmaster.se2aa4.island.team107.Position.Coordinate;

public class CoordinateTest {
    private Coordinate coordOne;
    private Coordinate coordTwo;

    @BeforeEach
    public void setUp() {
        coordOne = new Coordinate(0, 0);
        coordTwo = new Coordinate(1, 49);
    }

    @Test
    public void testDistanceTo() {
        assertEquals(coordOne.distanceTo(coordTwo), Math.sqrt(2402));
    }
}
