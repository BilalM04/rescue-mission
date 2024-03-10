package ca.mcmaster.se2aa4.island.team107.DronePhases;

import ca.mcmaster.se2aa4.island.team107.Direction;
import ca.mcmaster.se2aa4.island.team107.DroneController;
import org.json.JSONObject;

public class ScanLine implements Phase {

    private final int FLY_MODE = 0;
    private final int SCAN_MODE = 1;
    private final int ECHO_FRONT = 2;

    private DroneController controller;

    private Direction direction;

    private boolean turnLeft;
    private boolean offIsland;
    private boolean hasMoved;
    private boolean moveOutwards;

    private Integer flyCount;
    private String command;


    public ScanLine(DroneController controller, 
                      Direction initialDirection, 
                      boolean turnLeft) {

        this.controller = controller;
        this.direction = initialDirection;
        this.turnLeft = turnLeft;

        this.offIsland = false;
        this.hasMoved = false;
        this.moveOutwards = false;
        this.flyCount = 0;
    }

    public String getDroneCommand() {
        command = getDroneRoutineScan(flyCount);
        flyCount++;
        return command;
    }

    public void processInfo(JSONObject info) {
        if (!info.has("found"))
            return;
        
        String echoStatus = info.getString("found");

        if (echoStatus.equals("OUT_OF_RANGE")) {
            offIsland = true;
            moveOutwards = info.getInt("range") >= 3;
        }
        else {
            hasMoved = true;
        }
    }

    public Phase getNextPhase() {
        Phase turnPhase = new UTurn(
            controller, direction, turnLeft, moveOutwards
        );
        return turnPhase;
    }

    public boolean isFinished() {
        return offIsland;
    }

    public boolean isLastPhase() {
        return offIsland && !hasMoved;
    }

    private String getDroneRoutineScan(int count) {
        int mode = count % 3;

        switch (mode) {
            case FLY_MODE:
                return controller.fly();
            case SCAN_MODE:
                return controller.scan();
            case ECHO_FRONT:
                return controller.echo(direction);
        }

        return "";
    }
}
