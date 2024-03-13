package ca.mcmaster.se2aa4.island.team107.DronePhases;

import ca.mcmaster.se2aa4.island.team107.Drone.Controller;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FindIsland implements Phase {

    private enum State {
        FLY,
        ECHO_LEFT,
        ECHO_RIGHT,
        TURN_LEFT,
        TURN_RIGHT,
        GET_RANGE,
        FLY_TO_ISLAND
    }

    private final Logger logger = LogManager.getLogger();

    private Controller controller;

    private Direction direction;

    private boolean atIsland;
    private boolean uTurnLeft;

    private Integer flightsToIsland;

    private State state;

    
    public FindIsland(Controller controller, Direction initialDirection) {
        this.controller = controller;
        this.direction = initialDirection;
        this.atIsland = false;
        this.uTurnLeft = false;

        this.flightsToIsland = 0;
        this.state = State.FLY;
    }

    public String getDroneCommand() {
        switch (state) {
            case State.FLY:
                return controller.fly();

            case State.ECHO_LEFT:
                return controller.echo(direction.getLeft());

            case State.ECHO_RIGHT:
                return controller.echo(direction.getRight());

            case State.TURN_LEFT:
                direction = direction.getLeft();
                return controller.heading(direction);

            case State.TURN_RIGHT:
                direction = direction.getRight();
                return controller.heading(direction);

            case State.GET_RANGE:
                return controller.echo(direction);

            case State.FLY_TO_ISLAND:
                return controller.fly();

            default:
                logger.info("Uh oh, something bad happened here!");
                return controller.stop();
        }
    }

    public void processInfo(JSONObject info) {
        String echoStatus;

        switch (state) {
            case State.FLY:
                state = State.ECHO_LEFT;
                break;
            
            case State.ECHO_LEFT:
                echoStatus = info.getString("found");
                if (echoStatus.equals("GROUND")) {
                    state = State.TURN_LEFT;
                    uTurnLeft = false;
                } else {
                    state = State.ECHO_RIGHT;
                }
                break;

            case State.ECHO_RIGHT:
                echoStatus = info.getString("found");
                if (echoStatus.equals("GROUND")) {
                    state = State.TURN_RIGHT;
                    uTurnLeft = true;
                } else {
                    state = State.FLY;
                }
                break;

            case State.TURN_LEFT:
                state = State.GET_RANGE;
                break;
            
            case State.TURN_RIGHT:
                state = State.GET_RANGE;
                break;

            case State.GET_RANGE:
                flightsToIsland = info.getInt("range");
                state = State.FLY_TO_ISLAND;
                break;
            
            case State.FLY_TO_ISLAND:
                flightsToIsland -= 1;
                if (flightsToIsland <= 0)
                    atIsland = true;
                break;
        }
    }

    public Phase getNextPhase() {
        Phase scanPhase = new ScanLine(
            controller, direction, uTurnLeft
        );
        return scanPhase;
    }

    public boolean isFinished() {
        return atIsland;
    }

    public boolean isLastPhase() {
        return false;
    }
}
