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

        assertEquals(decision.toString(), controller.fly());

        Coordinate loc = drone.getLocation();

        assertEquals(1, loc.getX());
        assertEquals(0, loc.getY());
        assertEquals(Direction.EAST, drone.getHeading());
    }

    @Test 
    public void testHeading() {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put("action", "heading");
        params.put("direction", "N");
        decision.put("parameters", params);

        assertEquals(decision.toString(), controller.heading(Direction.NORTH));

        Coordinate loc = drone.getLocation();

        assertEquals(1, loc.getX());
        assertEquals(1, loc.getY());
        assertEquals(Direction.NORTH, drone.getHeading());
    }

    @Test
    public void testEcho() {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put("action", "echo");
        params.put("direction", "W");
        decision.put("parameters", params);

        assertEquals(decision.toString(), controller.echo(Direction.WEST));
    }

    @Test
    public void testScan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");

        assertEquals(decision.toString(), controller.scan());
    }

    @Test
    public void testStop() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");

        assertEquals(decision.toString(), controller.stop());
    }
}
