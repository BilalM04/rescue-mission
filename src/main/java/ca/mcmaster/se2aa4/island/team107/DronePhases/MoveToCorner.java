package ca.mcmaster.se2aa4.island.team107.DronePhases;

import ca.mcmaster.se2aa4.island.team107.Drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

import org.json.JSONObject;


public class MoveToCorner implements Phase {

    private enum State {
        A, // echos left to find distance
        B, // echos right to find distance
        C, // turns to shortest distance direction
        D, // moves to corner
        E, // turns to face inwards
        F, // temporary state to perform scan to see if drone is in the corner of the svg
        G // stops drone
    }

    private DroneController controller;

    private Direction direction;

    private State state;

    private String command;
    private int distanceLeft;
    private int distanceRight;
    private int count = 0;
    private Direction finalDirection;
    private boolean hasReachedCorner;

    public MoveToCorner(DroneController controller, Direction initialDir) {
        this.controller = controller;
        this.direction = initialDir;
        this.state = State.A;
        this.finalDirection = initialDir;
        this.hasReachedCorner = false;
    }

    public String getDroneCommand() {

        switch (state) {
            case State.A:
                command = controller.echo(direction.getLeft());
                break;
            case State.B:
                command = controller.echo(direction.getRight());
                break;
            case State.C:
                if (distanceRight < distanceLeft) {
                    command = controller.heading(direction.getRight());
                    direction = direction.getRight();
                } else {
                    command = controller.heading(direction.getLeft());
                    direction = direction.getLeft();
                }
                break;
            case State.D:
                command = controller.fly();
                count++;
                break;
            case State.E:
                command = controller.heading(finalDirection);
                direction = finalDirection;
                break;
            case State.F:
                command = controller.scan();
                break;
            default:
                command = controller.scan();
                hasReachedCorner = true;
                break;
        }
        return command;
    }

    public void processInfo(JSONObject info) {
        switch (state) {
            case State.A:
                distanceLeft = info.getInt("range");
                state = State.B;
                break;
            case State.B:
                distanceRight = info.getInt("range");
                if (Math.min(distanceLeft, distanceRight) > 2) {
                    state = State.C;
                } else {
                    state = State.F;
                }
                break;
            case State.C:
                state = State.D;
                break;
            case State.D:
                if (count >= Math.min(distanceLeft, distanceRight) - 2) {
                    state = State.E;
                }
                break;
            case State.E:
                state = State.F;
                break;
            case State.F:
                state = State.G;
            default:
                return;
        }
    }

    public Phase getNextPhase() {
        Phase goToIsland = new FindIsland(controller, direction);
        return goToIsland;
    }

    public boolean isFinished() {
        return hasReachedCorner;
    }

    public boolean isLastPhase() {
        return false;
    }
}
