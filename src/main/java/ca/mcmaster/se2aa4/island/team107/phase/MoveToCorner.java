package ca.mcmaster.se2aa4.island.team107.phase;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public class MoveToCorner implements Phase {

    private enum State {
        ECHO_LEFT, 
        ECHO_RIGHT, 
        TURN_TO_CORNER, 
        FLY_TO_CORNER, 
        TURN_INWARD, 
    }

    private final Logger logger = LogManager.getLogger();

    private Direction direction;
    private State state;

    private int distanceLeft;
    private int distanceRight;
    private int distanceTraveled = 0;
    private boolean hasReachedCorner;
    private boolean turnRight;

    public MoveToCorner(Direction initialDir) {
        this.direction = initialDir;
        this.state = State.ECHO_LEFT;
        this.hasReachedCorner = false;
    }

    public String getDroneCommand(Controller controller) {

        switch (state) {
            case State.ECHO_LEFT:
                return controller.echo(direction.getLeft());
                
            case State.ECHO_RIGHT:
                return controller.echo(direction.getRight());
                
            case State.TURN_TO_CORNER:
                if (distanceRight < distanceLeft) {
                    turnRight = false;
                    direction = direction.getRight();
                    return controller.heading(direction);
                } else {
                    turnRight = true;
                    direction = direction.getLeft();
                    return controller.heading(direction);
                }

            case State.FLY_TO_CORNER:
                distanceTraveled++;
                return controller.fly();
                
            case State.TURN_INWARD:
                if (turnRight) {
                    direction = direction.getRight();
                } else {
                    direction = direction.getLeft();
                }
                return controller.heading(direction);
                
            default:
                logger.info("Uh oh, something bad happened here!");
                return controller.stop();
        }
    }

    public void processInfo(JSONObject info) {
        switch (state) {
            case State.ECHO_LEFT:
                distanceLeft = info.getInt("range");
                state = State.ECHO_RIGHT;
                break;

            case State.ECHO_RIGHT:
                distanceRight = info.getInt("range");
                if (Math.min(distanceLeft, distanceRight) > 2) {
                    state = State.TURN_TO_CORNER;
                } else {
                    hasReachedCorner = true;
                }
                break;

            case State.TURN_TO_CORNER:
                state = State.FLY_TO_CORNER;
                break;

            case State.FLY_TO_CORNER:
                if (distanceTraveled >= Math.min(distanceLeft, distanceRight) - 2) {
                    state = State.TURN_INWARD;
                }
                break;

            case State.TURN_INWARD:
                hasReachedCorner = true;
                
        }
    }

    public Phase getNextPhase() {
        return new FindIsland(direction);
    }

    public boolean isFinished() {
        return hasReachedCorner;
    }

    public boolean isLastPhase() {
        return false;
    }
}
