package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.Drone.Drone;
import ca.mcmaster.se2aa4.island.team107.Drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.Position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

public class SimpleDroneTest {
    private final Integer battery = 10000;
    private final Integer cost = 5;
    private final Direction dir = Direction.EAST;
    private final Coordinate coord = new Coordinate(0, 1);
    private final Coordinate coord1 = new Coordinate(1, 0);
    private final Coordinate coord2 = new Coordinate(1, -1);
    private final Coordinate coord3 = new Coordinate(1, 1);
    private Drone drone;

    @BeforeEach
    public void setUp() {
        drone = new SimpleDrone(battery, dir);
    }

    @Test
    public void testGetHeading() {
        assertTrue(drone.getHeading().equals(Direction.EAST));
    }

    @Test
    public void testGetBateryLevel() {
        assertFalse(drone.getBatteryLevel().equals(battery - 1));
    }

    @Test
    public void testDrainBattery() {
        drone.drainBattery(cost);
        assertTrue(drone.getBatteryLevel().equals(battery - cost));
    }

    @Test
    public void testNotEnoughBattery() {
        drone.drainBattery(9990);
        assertTrue(drone.notEnoughBattery(10, 10));
    }

    @Test
    public void testGetX() {
        assertEquals(drone.getX(), coord.getX());
    }

    @Test
    public void testGetY() {
        assertNotEquals(drone.getY(), coord.getY());
    }

    @Test
    public void testFlyForward() {
        drone.flyForward();
        assertEquals(dir, drone.getHeading(), "Drone direction should remain unchanged after flying forward.");
        assertEquals(coord1.getX(), drone.getX(), "Drone X coordinate should match expected after flying forward.");
        assertEquals(coord1.getY(), drone.getY(), "Drone Y coordinate should match expected after flying forward.");
    }

    @Test
    public void testTurnRight() {
        drone.turnRight();
        assertEquals(drone.getHeading(), Direction.SOUTH);
        assertEquals(drone.getX(), coord2.getX());
        assertEquals(drone.getY(), coord2.getY());
    }

    @Test
    public void testTurnLeft() {
        drone.turnLeft();
        assertEquals(drone.getHeading(), Direction.NORTH);
        assertEquals(drone.getX(), coord3.getX());
        assertEquals(drone.getY(), coord3.getY());
    }
}
