package ca.mcmaster.se2aa4.island.team107.DronePhases;

import ca.mcmaster.se2aa4.island.team107.Direction;
import ca.mcmaster.se2aa4.island.team107.DroneController;
import org.json.JSONObject;

public class FindIsland implements Phase {

    private DroneController controller;

    private Integer flyCount;

    private Direction direction;
    private Direction prevDirection;

    private boolean leftEcho;
    private boolean rightEcho;
    private boolean hasTurned;
    private boolean atIsland;

    private String prevLeftEcho;
    private String command;

    
    public FindIsland(DroneController controller, Direction initialDirection) {
        this.controller = controller;
        this.direction = initialDirection;
        this.prevDirection = initialDirection;
        this.flyCount = 0;
        this.hasTurned = false;
        this.atIsland = false;
    }

    public String getDroneCommand() {
        if (prevDirection != direction) {
            prevDirection = direction;
            command = controller.heading(direction);
        }
        else {
            command = getDroneRoutineSearch(flyCount);
            flyCount++;
        }

        return command;
    }

    public void processInfo(JSONObject info) {
        if (!info.has("found"))
            return;

        String echoStatus = info.getString("found");
        int range = info.getInt("range");

        if (leftEcho) {
            prevLeftEcho = echoStatus;
        }

        if (echoStatus.equals("GROUND")) {
            if (range == 0) {
                atIsland = true;
            }

            if (!hasTurned) {
                Direction t = (leftEcho) ? direction.getLeft() : direction;
                direction = (rightEcho) ? direction.getRight() : t;
                hasTurned = true;
            }
        }
    }

    public Phase getNextPhase() {
        Phase scanPhase = new ScanLine(
            controller, direction, prevLeftEcho.equals("GROUND")
        );
        return scanPhase;
    }

    public boolean isFinished() {
        return atIsland;
    }

    public boolean isLastPhase() {
        return false;
    }

    private String getDroneRoutineSearch(int count) {
        leftEcho = false;
        rightEcho = false;

        switch (count % 5) {
            case 0:
                return controller.fly();
            case 1:
                return controller.scan();
            case 2:
                return controller.echo(direction);
            case 3:
                leftEcho = true;
                return controller.echo(direction.getLeft());
            case 4:
                rightEcho = true;
                return controller.echo(direction.getRight());
        }

        return "";
    }
}
