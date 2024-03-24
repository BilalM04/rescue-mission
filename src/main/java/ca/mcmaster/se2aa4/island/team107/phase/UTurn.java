package ca.mcmaster.se2aa4.island.team107.phase;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

import org.json.JSONObject;

public class UTurn implements Phase {

    private boolean hasTurned;
    private boolean turnLeft;

    private Integer turnCount;
    private Integer flyDistance;
    private Integer turnOpposite;

    public UTurn(boolean turnLeft, boolean outward) {
        this.hasTurned = false;
        this.turnLeft = turnLeft;
        this.turnCount = 0;

        if (outward) {
            flyDistance = 3;
            turnOpposite = 0;
        } 
        else {
            flyDistance = 1;
            turnOpposite = 4;
        }
    }

    public String getDroneCommand(Controller controller, Direction dir) {
        String command;

        if (turnCount.equals(turnOpposite)) {
            command = turnCommand(controller, !turnLeft, dir);
        }
        else if (turnCount.equals(flyDistance)) {
            command = controller.fly();
        }
        else {
            command = turnCommand(controller, turnLeft, dir);
        }

        if (turnCount >= 4) {
            hasTurned = true;
        }

        turnCount++;

        return command;
    }

    public void processInfo(JSONObject info) { 
        // Phase does need to process any information from JSON response. Drone U-turn logic is fixed.
    }

    public Phase getNextPhase() {
        return new ScanLine(!turnLeft);
    }

    public boolean isFinished() {
        return hasTurned;
    }

    public boolean isLastPhase() {
        return false;
    }

    private String turnCommand(Controller controller, boolean dirLeft, Direction direction) {
        direction = dirLeft ? direction.getLeft() : direction.getRight();
        return controller.heading(direction);
    }
}
