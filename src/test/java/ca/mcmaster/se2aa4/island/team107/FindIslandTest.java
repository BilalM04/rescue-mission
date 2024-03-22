package ca.mcmaster.se2aa4.island.team107;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.phase.FindIsland;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class FindIslandTest {
    private final Integer battery = 10000;
    private Direction dir = Direction.EAST;
    private Drone drone;
    private DroneController controller;
    private FindIsland p1;

    @BeforeEach
    public void setup() {
        drone = new SimpleDrone(battery, dir);
        controller = new DroneController(drone);
        p1 = new FindIsland(dir);
    }

    @Test
    public void testStateFly() {
        assertEquals(p1.getDroneCommand(controller, drone.getHeading()), controller.fly());
    }

    // @Test
    // public void testStateEchoLeft() {
    //     p1.getDroneCommand();
    //     p1.processInfo(null);
    // }

}
