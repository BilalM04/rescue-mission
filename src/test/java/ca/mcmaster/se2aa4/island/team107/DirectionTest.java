package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

public class DirectionTest {
    private final Direction dir = Direction.EAST;

    @Test
    public void testGetRight() {
        Direction dirR = dir.getRight();
        assertEquals(dirR, Direction.SOUTH);
    }

    @Test
    public void testGetLeft() {
        Direction dirL = dir.getLeft();
        assertEquals(dirL, Direction.NORTH);
    }

    @Test
    public void testGetSymbol() {
        assertEquals(dir.getSymbol().toUpperCase(), "E");
    }
}
