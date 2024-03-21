package ca.mcmaster.se2aa4.island.team107;

import java.util.List;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.phase.UTurn;
import ca.mcmaster.se2aa4.island.team107.position.Direction;


public class UTurnTest {
    private final Integer battery = 10000;
    private Direction dir = Direction.EAST;
    private Drone drone;
    private DroneController controller;
    private UTurn p1;

    private List<String> leftTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}"
    );

    private List<String> rightTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}"
    );

    private List<String> inwardLeftTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}"
    );


    @BeforeEach
    public void setup() {
        drone = new SimpleDrone(battery, dir);
        controller = new DroneController(drone);
    }

    private void runSequence(List<String> sequence) {
        String command;
        Iterator<String> expected = sequence.iterator();
        while (!p1.isFinished() || expected.hasNext()) {
            command = p1.getDroneCommand(controller);
            assertEquals(command, expected.next());
        }
    }

    @Test
    public void turnLeftTest() {
        p1 = new UTurn(dir, true, true);
        runSequence(leftTurnSequence);
    }
    
    @Test
    public void turnRightTest() {
        p1 = new UTurn(dir, false, true);
        runSequence(rightTurnSequence);
    }

    @Test
    public void turnInwardTest() {
        p1 = new UTurn(dir, true, false);
        runSequence(inwardLeftTurnSequence);
    }
}
