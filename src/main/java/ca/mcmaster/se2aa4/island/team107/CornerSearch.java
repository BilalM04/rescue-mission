package ca.mcmaster.se2aa4.island.team107;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class CornerSearch implements Search{

    private final Logger logger = LogManager.getLogger();

    private enum State {
        A, // echos left to find distance
        B, // echos right to find distance
        C, // turns to shortest distance direction
        D, // moves to corner
        E, // turns to face inwards
        F, // temporary state to perform scan to see if drone is in the corner of the svg
        G // stops drone
    }
    private State state;
    private Drone drone;
    private DroneController controller;

    private int distanceLeft;
    private int distanceRight;
    private int count = 0;
    private Direction finalHeading;

    public CornerSearch(Drone drone) {
        this.drone = drone;
        this.state = State.A;
        this.controller = new DroneController(drone);
        this.finalHeading = drone.getHeading();
    }

    public String performSearch() {
        String command = "";
        Direction heading = drone.getHeading();

        switch (state) {
            case State.A:
                command = controller.echo(heading.getLeft());
                break;
            case State.B:
                command = controller.echo(heading.getRight());
                break;
            case State.C:
                if (distanceRight < distanceLeft) {
                    command = controller.heading(heading.getRight());
                } else {
                    command = controller.heading(heading.getLeft());
                }
                break;
            case State.D:
                command = controller.fly();
                count++;
                break;
            case State.E:
                command = controller.heading(finalHeading);
                break;
            case State.F:
                command = controller.scan();
                break;
            default:
                command = controller.stop();
                logger.info("Drone coords: " + drone.getX() + ", " + drone.getY());
                logger.info("Drone Battery: " + drone.getBatteryLevel());
                logger.info("Drone heading: " + drone.getHeading().toString());
                break;
        }

        logger.info("Command sent: " + command);

        return command;
    }

    public void readResponse(JSONObject response) {
        JSONObject extras = response.getJSONObject("extras");
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);

        drone.drainBattery(cost);

        switch (state) {
            case State.A:
                distanceLeft = extras.getInt("range");
                state = State.B;
                break;
            case State.B:
                distanceRight = extras.getInt("range");
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
                if (count >= Math.min(distanceLeft, distanceRight) - 3) {
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
}
