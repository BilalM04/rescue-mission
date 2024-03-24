package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.phase.FindIsland;
import ca.mcmaster.se2aa4.island.team107.phase.MoveToCorner;
import ca.mcmaster.se2aa4.island.team107.phase.Phase;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class MoveToCornerTest {
    private Drone drone;
    private Controller controller;
    private Phase phase;
    private final String echoLeft = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
    private final String echoRight = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}";
    private final String turn = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}";
    private final String fly = "{\"action\":\"fly\"}";


    @BeforeEach
    public void setUp() {
        this.drone = new SimpleDrone(10000, Direction.EAST);
        this.controller = new DroneController(drone);
        this.phase = new MoveToCorner();
    }

    @Test
    public void testEchoLeftState() {
        assertEquals(echoLeft, phase.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testEchoRightState() {
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(echoResponse(10));

        assertEquals(echoRight, phase.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testTurnState() {
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(echoResponse(10));
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(echoResponse(27));

        assertEquals(turn, phase.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testFlyState() {
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(echoResponse(10));
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(echoResponse(27));
        phase.getDroneCommand(controller, drone.getHeading());
        phase.processInfo(null);

        assertEquals(fly, phase.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testNextPhase() {
        Phase nextPhase = phase.getNextPhase();
        Phase correctPhase = new FindIsland();
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

    private JSONObject echoResponse(int range) {
        JSONObject response = new JSONObject();
        response.put("range", range);
        response.put("found","OUT_OF_RANGE");
        return response;
    }
}
