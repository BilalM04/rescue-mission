package ca.mcmaster.se2aa4.island.team107;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.Drone.Drone;
import ca.mcmaster.se2aa4.island.team107.Drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.Position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;
import ca.mcmaster.se2aa4.island.team107.Position.ListMap;
import ca.mcmaster.se2aa4.island.team107.Position.POI;
import ca.mcmaster.se2aa4.island.team107.Position.POI.TypePOI;

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

        Coordinate coord4 = new Coordinate(0, 0);
        Coordinate coord5 = new Coordinate(1, 49);

        POI creek1 = new POI(TypePOI.CREEK, new Coordinate(0, 0), "1");
        POI creek2 = new POI(TypePOI.CREEK, new Coordinate(0, 50), "2");
        POI creek3 = new POI(TypePOI.CREEK, new Coordinate(50, 0), "3");
        POI creek4 = new POI(TypePOI.CREEK, new Coordinate(50, 50), "4");
        POI creek5 = new POI(TypePOI.CREEK, new Coordinate(15, 15), "5");
        POI site = new POI(TypePOI.EMERGENCY_SITE, new Coordinate(25, 25), "site");
        ListMap map = new ListMap();

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

        // Testing all public methods part of the ListMap class
        // Testing the getClosestCreek method in ListMap class
        try {
            map.addPOI(creek1);
            map.addPOI(creek2);
            map.addPOI(creek3);
            map.addPOI(creek4);
            map.addPOI(creek5);
            map.addPOI(site);
            // POI closest = map.getClosestCreek();
            assertEquals(map.getClosestCreek().getID(), "5");
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testimg all public methods part of the Direction class
        // Testing the getRight and getLeft methods part of the Direction Enum class
        try {
            Direction dirR = dir.getRight();
            assertEquals(dirR, Direction.SOUTH);
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }
        try {
            Direction dirL = dir.getLeft();
            assertEquals(dirL, Direction.NORTH);
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testing the getSymbol method part of the Direction Enum class
        try {
            assertEquals(dir.getSymbol().toUpperCase(), "E");
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

        // Testing the distanceTo method in the Coordinate class
        try {
            assertEquals(coord4.distanceTo(coord5), Math.sqrt(2402));
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }
    }

}
