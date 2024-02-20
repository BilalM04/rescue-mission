package ca.mcmaster.se2aa4.island.team107;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GridSearch implements Search {
    private final Logger logger = LogManager.getLogger();

    private Drone drone;
    private DroneController controller;

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

    public GridSearch(Drone drone) {
        this.drone = drone;
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

        leftEcho = false;
        rightEcho = false;
        frontEcho = false;
        
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
        else if (flyCount % 5 == 0) {
            command = controller.fly();
            shouldTurn = true;
        }
        else if (flyCount % 5 == 1) {
            command = controller.scan();
        }
        else if (flyCount % 5 == 2) {
            command = controller.echo(direction);
            frontEcho = true;
        }
        else if (flyCount % 5 == 3) {
            command = controller.echo(direction.getLeft());
            leftEcho = true;
        }
        else if (flyCount % 5 == 4) {
            command = controller.echo(direction.getRight());
            rightEcho = true;
            checkInitially = false;
        }

        flyCount++;
        
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
            }
        }
    }
}
