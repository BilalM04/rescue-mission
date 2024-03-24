package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.phase.Phase;
import ca.mcmaster.se2aa4.island.team107.phase.ScanLine;
import ca.mcmaster.se2aa4.island.team107.phase.UTurn;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class ScanLineTest {
    private Drone drone;
    private Controller controller;
    private Phase phase;
    private final String echo = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";
    private final String scan = "{\"action\":\"scan\"}";
    private final String fly = "{\"action\":\"fly\"}";


    @BeforeEach
    public void setUp() {
        this.drone = new SimpleDrone(10000, Direction.EAST);
        this.controller = new DroneController(drone);
        this.phase = new ScanLine(true);
    }

    @Test
    public void testFly() {
        assertEquals(fly, phase.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testScan() {
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(null);
        assertEquals(scan, phase.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testEcho() {
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(null);
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(scanResponse());

        assertEquals(echo, phase.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testNextPhase() {
        Phase nextPhase = phase.getNextPhase();
        Phase correctPhase = new UTurn(true, true);
        assertEquals(correctPhase.getClass(), nextPhase.getClass());
    }

    @Test
    public void testFinished() {
        assertEquals(false, phase.isFinished());
    }

    @Test
    public void testLastPhase() {
        assertEquals(false, phase.isLastPhase());
    }

    private JSONObject scanResponse() {
        JSONObject response = new JSONObject();
        JSONArray biomes = new JSONArray(1);
        biomes.put("OCEAN");
        response.put("biomes", biomes);
        response.put("creeks", new JSONArray());
        response.put("sites", new JSONArray());
        return response;
    }
}
