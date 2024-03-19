package ca.mcmaster.se2aa4.island.team107;

import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.position.Direction;
import ca.mcmaster.se2aa4.island.team107.position.ListMap;
import ca.mcmaster.se2aa4.island.team107.position.Map;
import ca.mcmaster.se2aa4.island.team107.report.MissionReport;
import ca.mcmaster.se2aa4.island.team107.report.Report;
import ca.mcmaster.se2aa4.island.team107.search.GridSearch;
import ca.mcmaster.se2aa4.island.team107.search.Search;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private Search gridSearch;
    private Map map;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        this.map = new ListMap();
        Drone drone = new SimpleDrone(batteryLevel, Direction.fromSymbol(direction));
        this.gridSearch = new GridSearch(drone, map);
    }

    @Override
    public String takeDecision() {
        return gridSearch.performSearch();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n {}", response.toString(2));

        gridSearch.readResponse(response);
    }

    @Override
    public String deliverFinalReport() {
        Report report = new MissionReport(map);
        String finalReport = report.generateReport();
        logger.info(finalReport);
        return finalReport;
    }
}