package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class DirectionTest {
    private final Direction dir = Direction.EAST;

    @Test
    public void testGetRight() {
        Direction dirR = dir.getRight();
        assertEquals(Direction.SOUTH, dirR);
    }

    @Test
    public void testGetLeft() {
        Direction dirL = dir.getLeft();
        assertEquals(Direction.NORTH, dirL);
    }

    @Test
    public void testGetSymbol() {
        assertEquals("E", dir.getSymbol());
    }
}
