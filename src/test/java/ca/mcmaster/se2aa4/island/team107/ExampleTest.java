package ca.mcmaster.se2aa4.island.team107;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.Drone.Drone;
import ca.mcmaster.se2aa4.island.team107.Drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    @Test
    public void sampleTest() {

        Drone drone = new SimpleDrone(10000, Direction.fromSymbol("E"));

        try {
            assertTrue(drone.getHeading().equals(Direction.EAST));
            System.out.println("PASSED");
        } catch (AssertionError ae) {
            System.out.println("FAILED");
        }

    }

}
