package ca.mcmaster.se2aa4.island.team107.DronePhases;

import ca.mcmaster.se2aa4.island.team107.Drone.Controller;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

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

    private Controller controller;
    private Direction direction;
    private Direction finalDirection;
    private State state;

    private int distanceLeft;
    private int distanceRight;
    private int distanceTraveled = 0;
    private boolean hasReachedCorner;

    public MoveToCorner(Controller controller, Direction initialDir) {
        this.controller = controller;
        this.direction = initialDir;
        this.state = State.ECHO_LEFT;
        this.finalDirection = initialDir;
        this.hasReachedCorner = false;
    }

    public String getDroneCommand() {

        switch (state) {
            case State.ECHO_LEFT:
                return controller.echo(direction.getLeft());
                
            case State.ECHO_RIGHT:
                return controller.echo(direction.getRight());
                
            case State.TURN_TO_CORNER:
                if (distanceRight < distanceLeft) {
                    direction = direction.getRight();
                    return controller.heading(direction);
                } else {
                    direction = direction.getLeft();
                    return controller.heading(direction);
                }

            case State.FLY_TO_CORNER:
                distanceTraveled++;
                return controller.fly();
                
            case State.TURN_INWARD:
                direction = finalDirection;
                return controller.heading(finalDirection);
                
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
        return new FindIsland(controller, direction);
    }

    public boolean isFinished() {
        return hasReachedCorner;
    }

    public boolean isLastPhase() {
        return false;
    }
}
