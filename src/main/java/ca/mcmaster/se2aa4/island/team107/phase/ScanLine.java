package ca.mcmaster.se2aa4.island.team107.phase;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScanLine implements Phase {

    private enum State {
        FLY,
        SCAN,
        ECHO_FRONT
    }

    private final Logger logger = LogManager.getLogger();

    //private Controller controller;

    private Direction direction;

    private boolean turnLeft;
    private boolean offIsland;
    private boolean hasMoved;
    private boolean moveOutwards;

    private State state;


    public ScanLine(Direction initialDirection, 
                      boolean turnLeft) {

        //this.controller = controller;
        this.direction = initialDirection;
        this.turnLeft = turnLeft;

        this.offIsland = false;
        this.hasMoved = false;
        this.moveOutwards = false;
        this.state = State.FLY;
    }

    public String getDroneCommand(Controller controller, Direction dir) {
        switch (state) {
            case State.FLY:
                return controller.fly();
            
            case State.SCAN:
                return controller.scan();

            case State.ECHO_FRONT:
                return controller.echo(direction);
            
            default:
                logger.info("Uh oh, something bad happened here!");
                return controller.stop();
        }
    }

    public void processInfo(JSONObject info) {
        String echoStatus;

        switch (state) {
            case State.FLY:
                state = State.SCAN;
                break;
        
            case State.SCAN:
                if (isDroneOffLand(info)) {
                    state = State.ECHO_FRONT;
                }
                else {
                    state = State.FLY;
                    hasMoved = true;
                }
                break;
            
            case State.ECHO_FRONT:
                echoStatus = info.getString("found");
                if (echoStatus.equals("OUT_OF_RANGE")) {
                    offIsland = true;
                    moveOutwards = (info.getInt("range") >= 3);
                }
                else {
                    state = State.FLY;
                    hasMoved = true;
                }
                break;
        }
    }

    public Phase getNextPhase() {
        return new UTurn(direction, turnLeft, moveOutwards);
    }

    public boolean isFinished() {
        return offIsland;
    }

    public boolean isLastPhase() {
        return offIsland && !hasMoved;
    }

    private boolean isDroneOffLand(JSONObject info) {
        JSONArray biomes = info.getJSONArray("biomes");
        String currentBiome = biomes.getString(0);
        return (biomes.length() == 1) && currentBiome.equals("OCEAN");
    }
}
