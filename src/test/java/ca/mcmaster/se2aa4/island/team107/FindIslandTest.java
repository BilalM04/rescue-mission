package ca.mcmaster.se2aa4.island.team107;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.Drone.Drone;
import ca.mcmaster.se2aa4.island.team107.Drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.Drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.DronePhases.FindIsland;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

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
        p1 = new FindIsland(controller, dir);
    }

    @Test
    public void testGetDroneCommand() {
        // assertEquals(p1.getDroneCommand(), "{\"action\":\"fly\"}");
    }

}
