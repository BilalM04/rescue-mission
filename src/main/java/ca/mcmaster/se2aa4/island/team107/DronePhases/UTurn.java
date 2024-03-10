package ca.mcmaster.se2aa4.island.team107.DronePhases;

import ca.mcmaster.se2aa4.island.team107.Direction;
import ca.mcmaster.se2aa4.island.team107.DroneController;
import org.json.JSONObject;

public class UTurn implements Phase {

    private DroneController controller;

    private Direction direction;

    private boolean hasTurned;
    private boolean turnLeft;

    private Integer turnCount;
    private Integer FLY;
    private Integer TURN_OPPOSITE;

    private String command;
    

    public UTurn(DroneController controller, 
                 Direction dir, 
                 boolean turnLeft, 
                 boolean outward) {

        this.controller = controller;
        this.direction = dir;

        this.hasTurned = false;
        this.turnLeft = turnLeft;
        this.turnCount = 0;

        if (outward) {
            FLY = 3;
            TURN_OPPOSITE = 0;
        } 
        else {
            FLY = 1;
            TURN_OPPOSITE = 4;
        }
    }

    public String getDroneCommand() {
        if (turnCount == TURN_OPPOSITE) {
            command = turnCommand(!turnLeft);
        }
        else if (turnCount == FLY) {
            command = controller.fly();
        }
        else {
            command = turnCommand(turnLeft);
        }

        if (turnCount >= 4) {
            hasTurned = true;
        }

        turnCount++;

        return command;
    }

    public void processInfo(JSONObject info) { }


    public Phase getNextPhase() {
        Phase scanPhase = new ScanLine(
            controller, direction, !turnLeft
        );
        return scanPhase;
    }

    public boolean isFinished() {
        return hasTurned;
    }

    public boolean isLastPhase() {
        return false;
    }

    private String turnCommand(boolean dirLeft) {
        direction = (dirLeft) ? direction.getLeft() : direction.getRight();
        return controller.heading(direction);
    }
}
