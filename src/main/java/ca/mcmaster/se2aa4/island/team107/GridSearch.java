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
        this.map = map;
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

        String command = "";

        // This if block get's executed when the prevDir is different from current Dir
        if (prevDirection != direction) {
            if (turnCount != 3) {
                prevDirection = direction;
                // turn the drone to the direction heading
                command = controller.heading(direction);
            }

            if (uturn) {
                if (turnCount == 3) {
                    command = controller.fly();
                } else if (turnLeft) {
                    direction = direction.getLeft();
                } else {
                    direction = direction.getRight();
                }
                if (turnCount++ >= 3) {
                    checkIsland = true;
                    uturn = false;
                    turnCount = 0;
                    turnLeft = !turnLeft;
                }
            }
        } else {
            if (!atIsland) {
                command = getDroneRoutineSearch(flyCount);
            } else {
                command = getDroneRoutineScan(flyCount);
            }
            flyCount++;
        }

        // // This if block get's executed when the drone should turn again and is on
        // the
        // // island already, meaning
        // // it has just finished an ith sweep (col or row) of the island then this
        // code
        // // allows the drone to turn again a full 180 to sweep the next row or col.
        // if (shouldTurn && atIsland) {
        // // If the prevDir's left heading is equal to the drones current heading then
        // // turn left once more to face 180 degrees to sweep next row or col.
        // // As an Ex if my drone was initially facing S and then it turns E, so
        // // prevDirection is S and direction is E
        // // and since the left of south is also E, the drone's next final direction
        // would
        // // be N since left of E is N.
        // if (prevDirection.getLeft() == direction) {
        // direction = direction.getLeft();
        // // Do the opposite for this else condition
        // } else {
        // direction = direction.getRight();
        // }
        // checkIsland = true;
        // shouldTurn = false;
        // // Else block get's executed to set the prevDir to the same dir as current
        // // heading. So in the same example above the prevDir and direction will both
        // be
        // // N.
        // } else {
        // prevDirection = direction;
        // }
        // } else if (flyCount % 5 == 0) {
        // command = controller.fly();
        // shouldTurn = true;
        // } else if (flyCount % 5 == 1) {
        // command = controller.scan();
        // } else if (flyCount % 5 == 2) {
        // command = controller.echo(direction);
        // frontEcho = true;
        // } else if (flyCount % 5 == 3) {
        // command = controller.echo(direction.getLeft());
        // leftEcho = true;
        // } else if (flyCount % 5 == 4) {
        // command = controller.echo(direction.getRight());
        // rightEcho = true;
        // checkInitially = false;
        // }

        // flyCount++;

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
            JSONArray creeksFound = (JSONArray) extraInfo.getJSONArray("creeks");
            if (!creeksFound.isEmpty()) {
                map.addCreek(new POI(TypePOI.CREEK, drone.getX(), drone.getY(), creeksFound.get(0).toString()));
            }
        }

        if (extraInfo.has("sites")) {
            JSONArray siteFound = (JSONArray) extraInfo.getJSONArray("sites");
            if (!siteFound.isEmpty()) {
                map.addEmergencySite(
                        new POI(TypePOI.EMERGENCY_SITE, drone.getX(), drone.getY(), siteFound.get(0).toString()));
            }
        }

        if (extraInfo.has("found")) {
            String echoStatus = extraInfo.getString("found");
            int range = extraInfo.getInt("range");

            // Everytime you do a leftEcho save the previous leftEcho in the temp variable
            // prevLeftEcho.
            if (leftEcho) {
                prevLeftEcho = echoStatus;
            }
            // This stops the drone, sinc it has reached the end of the sweeps
            if (frontEcho && checkIsland) {
                checkIsland = false;
                isComplete = echoStatus.equals("OUT_OF_RANGE");
            }

            // If GROUND is found on either front, left or right echo run this piece of
            // code.
            if (echoStatus.equals("GROUND")) {
                if (checkInitially) {
                    checkInitially = false;
                    turnBeforeScan = true;
                }

                // This if block get's executed when the drone reaches the beach
                if (range == 0 && !atIsland) {
                    atIsland = true;
                    // if the previous left echo also found GROUND set the turnleft command to true
                    // since the drone will eventually have to turn left once it reaches the
                    // opposite end point of the coastline.
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

                // This if block get's executed when the Drone should turn and isn't a the
                // island yet meaning it will turn towards the edge of a coastline and head
                // towards that direction.
                if (shouldTurn && !atIsland) {
                    Direction t = (leftEcho) ? direction.getLeft() : direction;
                    direction = (rightEcho) ? direction.getRight() : t;
                }
                // This else if condition get's executed once the drone reaches the opposite
                // side of the coastline and if towards
                // the drones left direction there is land then turn.
                // The reason why this get's executed is since the front echo outputs
                // OUT_OF_RANGE the else if condition is executed
                // and since the drone is atIsland == true anf frontEcho is true the drone will
                // turn left, and set turnLeft flag to false.
            } else if (atIsland && frontEcho) {
                direction = (turnLeft) ? direction.getRight() : direction.getLeft();
                uturn = true;
                if (range < 3) {
                    isComplete = true;
                }
            }
        }
    }

    public void report() {
        map.printCreeks();
        map.printSites();
        map.closestInlet();
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
