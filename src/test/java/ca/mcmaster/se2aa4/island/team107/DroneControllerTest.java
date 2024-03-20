package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class DroneControllerTest {
    private Drone drone;
    private Controller controller;

    @BeforeEach
    public void setUp() {
        this.drone = new SimpleDrone(10000, Direction.EAST);
        this.controller = new DroneController(drone);
    }

    @Test
    public void testFly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");

        assertEquals(controller.fly(), decision.toString());

        Coordinate loc = drone.getLocation();

        assertEquals(loc.getX(), 1);
        assertEquals(loc.getY(), 0);
        assertEquals(drone.getHeading(), Direction.EAST);
    }

    @Test 
    public void testHeading() {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put("action", "heading");
        params.put("direction", "N");
        decision.put("parameters", params);

        assertEquals(controller.heading(Direction.NORTH), decision.toString());

        Coordinate loc = drone.getLocation();

        assertEquals(loc.getX(), 1);
        assertEquals(loc.getY(), 1);
        assertEquals(drone.getHeading(), Direction.NORTH);
    }

    @Test
    public void testEcho() {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put("action", "echo");
        params.put("direction", "W");
        decision.put("parameters", params);

        assertEquals(controller.echo(Direction.WEST), decision.toString());
    }

    @Test
    public void testScan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");

        assertEquals(controller.scan(), decision.toString());
    }

    @Test
    public void testStop() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");

        assertEquals(controller.stop(), decision.toString());
    }
}
