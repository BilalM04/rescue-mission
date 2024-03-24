package ca.mcmaster.se2aa4.island.team107.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.map.Map;
import ca.mcmaster.se2aa4.island.team107.map.POI;
import ca.mcmaster.se2aa4.island.team107.map.POI.TypePOI;
import ca.mcmaster.se2aa4.island.team107.phase.MoveToCorner;
import ca.mcmaster.se2aa4.island.team107.phase.Phase;

public class GridSearch implements Search {
    private final Logger logger = LogManager.getLogger();

    private Drone drone;
    private Controller controller;
    private Phase phase;

    public GridSearch(Drone drone) {
        this.drone = drone;
        this.controller = new DroneController(drone);
        this.phase = new MoveToCorner();
    }

    public String performSearch() {
        String command = "";

        if (phase.isLastPhase() || drone.notEnoughBattery()) {
            command = controller.stop();
        } else {
            if (!phase.isFinished()) {
                command = phase.getDroneCommand(controller, drone.getHeading());
            } else {
                phase = phase.getNextPhase();
                command = phase.getDroneCommand(controller, drone.getHeading());
            }
        }

        return command;
    }

    public void readResponse(JSONObject response, Map map) {
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
                    map.addPOI(new POI(TypePOI.CREEK, drone.getLocation(), creeksFound.getString(0)));
                }
            }
        }

        if (extraInfo.has("sites")) {
            JSONArray sites = extraInfo.getJSONArray("sites");
            if (!sites.isEmpty()) {
                map.addPOI(new POI(TypePOI.EMERGENCY_SITE, drone.getLocation(), sites.getString(0)));
            }
        }
    }
}