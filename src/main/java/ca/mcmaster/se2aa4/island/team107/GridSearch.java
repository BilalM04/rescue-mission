package ca.mcmaster.se2aa4.island.team107;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GridSearch implements Search {
    private final Logger logger = LogManager.getLogger();

    private Drone drone;
    private DroneController controller;
    private Map map;

    private int flyCount = 0;
    private int turnCount = 0;
    private Direction direction;
    private Direction prevDirection;

    private String prevLeftEcho;

    private boolean frontEcho;
    private boolean leftEcho;
    private boolean rightEcho;
    private boolean shouldTurn;
    private boolean atIsland;
    private boolean turnLeft;
    private boolean checkIsland;
    private boolean checkInitially;
    private boolean turnBeforeScan;
    private boolean uturn;

    private boolean isComplete;

    public GridSearch(Drone drone, Map map) {
        this.drone = drone;
        this.map = map;
        this.controller = new DroneController(drone);
        this.prevDirection = drone.getHeading();
        this.direction = drone.getHeading();
        
        this.shouldTurn = false;
        this.atIsland = false;
        this.isComplete = false;
        this.turnLeft = false;
        this.checkIsland = false;

        this.checkInitially = true;
        this.turnBeforeScan = false;
        this.uturn = false;
    }

    public String performSearch() {
        logger.info("Current heading: {}, Previous: {}", direction, prevDirection);
        logger.info("Position X: {}, Position Y: {}", drone.getX(), drone.getY());
        
        String command = "";
        
        if (prevDirection != direction) {
            if (turnCount != 3) {
                prevDirection = direction;
                command = controller.heading(direction);
            }
            
            if (uturn) {
                if (turnCount == 3) {
                    command = controller.fly();
                } else if (turnLeft) {
                    direction = direction.getLeft();
                }
                else {
                    direction = direction.getRight();
                }
                if (turnCount++ >= 3) {
                    checkIsland = true;
                    uturn = false;
                    turnCount = 0;
                    turnLeft = !turnLeft;
                }
            }
        }
        else {
            if (!atIsland) {
                command = getDroneRoutineSearch(flyCount);
            }
            else {
                command = getDroneRoutineScan(flyCount);
            }
            flyCount++;
        }
        
        if (drone.getBatteryLevel() < 100 || isComplete) {
            command = controller.stop();
        }

        logger.info("** Decision: {}", command);
        
        return command;
    }

    public void readResponse(JSONObject response) {
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);

        drone.drainBattery(cost);

        logger.info("Battery level is {}", drone.getBatteryLevel());

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);

        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        if (extraInfo.has("creeks")) {
            JSONArray creeksFound = (JSONArray)extraInfo.getJSONArray("creeks");
            if (!creeksFound.isEmpty()) {
                // addCreeks(creeksFound);
            }
        }
        
        if (extraInfo.has("found")) {
            String echoStatus = extraInfo.getString("found");
            int range = extraInfo.getInt("range");

            if (leftEcho) {
                prevLeftEcho = echoStatus;
            }

            if (frontEcho && checkIsland) {
                checkIsland = false;
                isComplete = echoStatus.equals("OUT_OF_RANGE");
            }

            if (echoStatus.equals("GROUND")) {
                if (checkInitially) {
                    checkInitially = false;
                    turnBeforeScan = true;
                }

                if (range == 0 && !atIsland) {
                    atIsland = true;
                    turnLeft = prevLeftEcho.equals("GROUND");
                    // scan in other direction if land already in range
                    if (turnBeforeScan) {
                        direction = direction.getLeft();
                        turnLeft = false;
                    }
                }
                if (frontEcho) {
                    shouldTurn = false;
                }

                if (shouldTurn && !atIsland) {
                    Direction t = (leftEcho) ? direction.getLeft() : direction;
                    direction = (rightEcho) ? direction.getRight() : t;
                }
            }
            else if (atIsland && frontEcho) {
                direction = (turnLeft) ? direction.getRight() : direction.getLeft();
                uturn = true;
                // TEMPORARY FIX
                // In the event that wide turns lose drone signal, stop immediately
                // TODO: Modify turn path if at edge of map
                if (range < 3) {
                    isComplete = true;
                }
            }
        }
    }

    private String getDroneRoutineSearch(int count) {
        frontEcho = false;
        leftEcho = false;
        rightEcho = false;

        switch (count % 5) {
            case 0:
                shouldTurn = true;
                return controller.fly();
            case 1:
                return controller.scan();
            case 2:
                frontEcho = true;
                return controller.echo(direction);
            case 3:
                leftEcho = true;
                return controller.echo(direction.getLeft());
            case 4:
                rightEcho = true;
                checkInitially = false;
                return controller.echo(direction.getRight());
        }

        return "";
    }

    private String getDroneRoutineScan(int count) {
        frontEcho = false;

        switch (count % 3) {
            case 0:
                return controller.fly();
            case 1:
                return controller.scan();
            case 2:
                frontEcho = true;
                return controller.echo(direction);
        }

        return "";
    }
}
