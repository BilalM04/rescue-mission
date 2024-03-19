package ca.mcmaster.se2aa4.island.team107;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    private final Coordinate coord = new Coordinate(0, 0);
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
        assertEquals(Direction.EAST, drone.getHeading());
    }

    @Test
    public void testGetBateryLevel() {
        assertEquals(battery, drone.getBatteryLevel());
    }

    @Test
    public void testDrainBattery() {
        drone.drainBattery(cost);
        assertTrue(drone.getBatteryLevel().equals(battery - cost));
    }

    @Test
    public void testNotEnoughBattery() {
        drone.drainBattery(9999);
        assertTrue(drone.notEnoughBattery(10, 10));
    }

    @Test
    public void testGetLocation() {
        Coordinate droneCoord = drone.getLocation();
        assertEquals(droneCoord.getX(), coord.getX());
        assertEquals(droneCoord.getY(), coord.getY());
    }

    @Test
    public void testFlyForward() {
        drone.flyForward();
        assertEquals(dir, drone.getHeading(), "Drone direction should remain unchanged after flying forward.");
        Coordinate droneCoord = drone.getLocation();
        assertEquals(coord1.getX(), droneCoord.getX());
        assertEquals(coord1.getY(), droneCoord.getY());
    }

    @Test
    public void testTurnRight() {
        drone.turnRight();
        assertEquals(Direction.SOUTH, drone.getHeading());
        Coordinate droneCoord = drone.getLocation();
        assertEquals(droneCoord.getX(), coord2.getX());
        assertEquals(droneCoord.getY(), coord2.getY());
    }

    @Test
    public void testTurnLeft() {
        drone.turnLeft();
        assertEquals(Direction.NORTH, drone.getHeading());
        Coordinate droneCoord = drone.getLocation();
        assertEquals(droneCoord.getX(), coord3.getY());
    }
}
