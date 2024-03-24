package ca.mcmaster.se2aa4.island.team107;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.phase.FindIsland;
import ca.mcmaster.se2aa4.island.team107.phase.Phase;
import ca.mcmaster.se2aa4.island.team107.phase.ScanLine;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class FindIslandTest {
    private final Integer battery = 10000;
    private Direction dir = Direction.EAST;
    private Drone drone;
    private DroneController controller;
    private FindIsland p1;

    private final String echoLeft = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
    private final String echoRight = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}";
    private final String turn = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}";
    private final String fly = "{\"action\":\"fly\"}";

    @BeforeEach
    public void setup() {
        drone = new SimpleDrone(battery, dir);
        controller = new DroneController(drone);
        p1 = new FindIsland();
    }

    @Test
    public void testStateFly() {
        assertEquals(fly, p1.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testEchoLeft() {
        p1.getDroneCommand(controller, drone.getHeading());
        p1.processInfo(null);
        
        assertEquals(echoLeft, p1.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testEchoRight() {
        p1.getDroneCommand(controller, drone.getHeading());
        p1.processInfo(null);
        p1.getDroneCommand(controller, drone.getHeading());
        p1.processInfo(echoResponse(0, "OUT_OF_RANGE"));
        
        assertEquals(echoRight, p1.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testTurn() {
        p1.getDroneCommand(controller, drone.getHeading());
        p1.processInfo(null);
        p1.getDroneCommand(controller, drone.getHeading());
        p1.processInfo(echoResponse(0, "OUT_OF_RANGE"));
        p1.getDroneCommand(controller, drone.getHeading());
        p1.processInfo(echoResponse(35, "GROUND"));
        
        assertEquals(turn, p1.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testNextPhase() {
        Phase nextPhase = p1.getNextPhase();
        Phase correctPhase = new ScanLine(true);
        assertEquals(correctPhase.getClass(), nextPhase.getClass());
    }

    @Test
    public void testFinished() {
        assertEquals(false, p1.isFinished());
    }

    @Test
    public void testLastPhase() {
        assertEquals(false, p1.isLastPhase());
    }

    private JSONObject echoResponse(int range, String found) {
        JSONObject response = new JSONObject();
        response.put("range", range);
        response.put("found",found);
        return response;
    }
}
