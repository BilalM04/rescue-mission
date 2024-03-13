package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.mcmaster.se2aa4.island.team107.Position.Coordinate;

public class CoordinateTest {
    private Coordinate coord4;
    private Coordinate coord5;

    @BeforeEach
    public void setUp() {
        coord4 = new Coordinate(0, 0);
        coord5 = new Coordinate(1, 49);
    }

    @Test
    public void testDistanceTo() {
        assertEquals(coord4.distanceTo(coord5), Math.sqrt(2402));
    }
}
