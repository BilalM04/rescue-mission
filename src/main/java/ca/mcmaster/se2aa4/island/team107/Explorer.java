package ca.mcmaster.se2aa4.island.team107;

import java.lang.Math;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private int battery;
    private int flyCount = 0;
    private String direction;
    private String prevDirection;

    private String prevLeftEcho;

    private boolean frontEcho;
    private boolean leftEcho;
    private boolean rightEcho;
    private boolean shouldTurn;
    private boolean atIsland;
    private boolean turnLeft;

    private boolean isComplete;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        this.battery = batteryLevel;
        this.direction = direction;
        this.prevDirection = direction;
        this.shouldTurn = false;
        this.atIsland = false;
        this.isComplete = false;
        this.turnLeft = false;
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        logger.info("Current heading: {}", direction);

        leftEcho = false;
        rightEcho = false;
        frontEcho = false;
        
        if (!prevDirection.equals(direction)) {
            String prev = prevDirection;
            prevDirection = direction;
            decision.put("action", "heading");
            params.put("direction", direction);
            decision.put("parameters", params);
            if (shouldTurn && atIsland) {
                if (leftOf(prev) == direction) {
                    direction = leftOf(direction);
                }
                else {
                    direction = rightOf(direction);
                }
                shouldTurn = false;
            }
            
        }
        else if (flyCount % 5 == 0) {
            decision.put("action", "fly");
            shouldTurn = true;
        }
        else if (flyCount % 5 == 1) {
            decision.put("action", "scan");
        }
        else if (flyCount % 5 == 2) {
            decision.put("action", "echo");
            params.put("direction", direction);
            decision.put("parameters", params);
            frontEcho = true;
        }
        else if (flyCount % 5 == 3) {
            decision.put("action", "echo");
            params.put("direction", leftOf(direction));
            decision.put("parameters", params);
            leftEcho = true;
        }
        else if (flyCount % 5 == 4) {
            decision.put("action", "echo");
            params.put("direction", rightOf(direction));
            decision.put("parameters", params);
            rightEcho = true;
        }

        flyCount++;
        
        if (battery < 100 || flyCount > 1000) {
            decision.put("action", "stop");
        }

        logger.info("** Decision: {}",decision.toString());
        
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));

        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);

        battery -= cost;

        logger.info("Battery level is {}", battery);

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);

        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        
        if (extraInfo.has("found")) {
            String echoStatus = extraInfo.get("found").toString();
            int range = (Integer)extraInfo.get("range");

            if (leftEcho) {
                prevLeftEcho = echoStatus;
            }

            if (echoStatus.equals("GROUND")) {
                if (range == 0 && !atIsland) {
                    atIsland = true;
                    turnLeft = prevLeftEcho.equals("GROUND");
                }
                if (frontEcho) {
                    shouldTurn = false;
                }

                if (shouldTurn) {
                    if (atIsland) {
                        direction = (turnLeft) ? leftOf(direction) : rightOf(direction);
                        turnLeft = !turnLeft;
                    } else {
                        String t = (leftEcho) ? leftOf(direction) : direction;
                        direction = (rightEcho) ? rightOf(direction) : t;
                    }
                }
            }
        }
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

    private String rightOf(String dir) {
        if (dir.equals("N")) return "E";
        if (dir.equals("S")) return "W";
        if (dir.equals("E")) return "S";
        if (dir.equals("W")) return "N";
        return "";
    }

    
    private String leftOf(String dir) {
        if (dir.equals("N")) return "W";
        if (dir.equals("S")) return "E";
        if (dir.equals("E")) return "N";
        if (dir.equals("W")) return "S";
        return "";
    }

}
