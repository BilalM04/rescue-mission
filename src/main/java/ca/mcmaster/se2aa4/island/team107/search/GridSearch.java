package ca.mcmaster.se2aa4.island.team107.search;

import ca.mcmaster.se2aa4.island.team107.drone.*;
import ca.mcmaster.se2aa4.island.team107.phase.*;
import ca.mcmaster.se2aa4.island.team107.position.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GridSearch implements Search {
    private final Logger logger = LogManager.getLogger();

    private Drone drone;
    private Controller controller;
    private Map map;
    private Phase phase;

    public GridSearch(Drone drone, Map map) {
        this.drone = drone;
        this.map = map;
        this.controller = new DroneController(drone);
        this.phase = new MoveToCorner(controller, drone.getHeading());
    }

    public String performSearch() {
        Coordinate loc = drone.getLocation();
        logger.info("Position X: {}, Position Y: {}", loc.getX(), loc.getY());

        String command = "";

        if (phase.isLastPhase() || drone.getBatteryLevel() < 100) {
            command = controller.stop();
        } else {
            if (!phase.isFinished()) {
                command = phase.getDroneCommand();
            } else {
                phase = phase.getNextPhase();
                command = phase.getDroneCommand();
            }
        }

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

        phase.processInfo(extraInfo);

        if (extraInfo.has("creeks")) {
            JSONArray creeksFound = extraInfo.getJSONArray("creeks");
            if (!creeksFound.isEmpty()) {
                for (int i = 0; i < creeksFound.length(); i++) {
                    map.addPOI(new POI(POI.TypePOI.CREEK, drone.getLocation(), creeksFound.getString(0)));
                }
            }
        }

        if (extraInfo.has("sites")) {
            JSONArray sites = extraInfo.getJSONArray("sites");
            if (!sites.isEmpty()) {
                map.addPOI(new POI(POI.TypePOI.EMERGENCY_SITE, drone.getLocation(), sites.getString(0)));
            }
        }
    }
}