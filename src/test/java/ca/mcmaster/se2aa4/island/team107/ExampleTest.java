package ca.mcmaster.se2aa4.island.team107;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.Drone.Drone;
import ca.mcmaster.se2aa4.island.team107.Drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.Position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    @Test
    public void sampleTest() {

        Integer battery = 10000;
        Integer cost = 5;
        Direction dir = Direction.fromSymbol("E");
        Drone drone = new SimpleDrone(battery, dir);
        Coordinate coord = new Coordinate(0, 1);
        Coordinate coord1 = new Coordinate(1, 0);
        Coordinate coord2 = new Coordinate(2, -1);
        Coordinate coord3 = new Coordinate(3, -2);

        // Testing SimpleDrone class public methods
        // Test if getHeading works
        try {
            assertTrue(drone.getHeading().equals(Direction.EAST));
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testing if getBatteryLevel works
        try {
            assertFalse(drone.getBatteryLevel().equals(battery - 1));
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testing if DrainBattery method works
        try {
            drone.drainBattery(cost);
            assertTrue(drone.getBatteryLevel().equals(battery - cost));
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testing if drone has enough battery
        try {
            drone.drainBattery(9990);
            assertFalse(drone.notEnoughBattery(10, 10));
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // testing the getX and getY methods of the drone class
        try {
            assertEquals(drone.getX(), coord.getX());
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }
        try {
            assertNotEquals(drone.getY(), coord.getY());
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testing if flyForward works correctly
        try {
            drone.flyForward();
            assertEquals(drone.getHeading(), dir);
            assertEquals(drone.getX(), coord1.getX());
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testing if turnRight and turnLeft method works in SimpleDrone class
        try {
            drone.turnRight();
            assertEquals(drone.getHeading(), Direction.SOUTH);
            assertEquals(drone.getX(), coord2.getX());
            assertEquals(drone.getY(), coord2.getY());
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }
        try {
            drone.turnLeft();
            assertEquals(drone.getHeading(), dir);
            assertEquals(drone.getX(), coord3.getX());
            assertEquals(drone.getY(), coord3.getY());
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

    }

}
