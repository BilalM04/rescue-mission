package ca.mcmaster.se2aa4.island.team107.phase;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

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

    private boolean atIsland;
    private boolean uTurnLeft;

    private Integer flightsToIsland;

    private State state;
    
    public FindIsland() {
        this.atIsland = false;
        this.uTurnLeft = false;
        this.flightsToIsland = 0;
        this.state = State.FLY;
    }

    public String getDroneCommand(Controller controller, Direction dir) {
        switch (state) {
            case State.FLY:
                return controller.fly();

            case State.ECHO_LEFT:
                return controller.echo(dir.getLeft());

            case State.ECHO_RIGHT:
                return controller.echo(dir.getRight());

            case State.TURN_LEFT:
                return controller.heading(dir.getLeft());

            case State.TURN_RIGHT:
                return controller.heading(dir.getRight());

            case State.GET_RANGE:
                return controller.echo(dir);

            case State.FLY_TO_ISLAND:
                return controller.fly();

            default:
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
                if (flightsToIsland <= 0) {
                    atIsland = true;
                }
                break;
        }
    }

    public Phase getNextPhase() {
        return new ScanLine(uTurnLeft);
    }

    public boolean isFinished() {
        return atIsland;
    }

    public boolean isLastPhase() {
        return false;
    }
}
