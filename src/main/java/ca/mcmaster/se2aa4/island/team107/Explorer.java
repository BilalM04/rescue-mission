package ca.mcmaster.se2aa4.island.team107;

import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;
import ca.mcmaster.se2aa4.island.team107.map.ListMap;
import ca.mcmaster.se2aa4.island.team107.map.Map;
import ca.mcmaster.se2aa4.island.team107.position.Direction;
import ca.mcmaster.se2aa4.island.team107.report.MissionReport;
import ca.mcmaster.se2aa4.island.team107.report.Report;
import ca.mcmaster.se2aa4.island.team107.search.GridSearch;
import ca.mcmaster.se2aa4.island.team107.search.Search;

import java.io.StringReader;

import eu.ace_design.island.bot.IExplorerRaid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private Search gridSearch;
    private Map map;
    private Report report;

    @Override
    public void initialize(String s) {
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");

        this.map = new ListMap();
        Drone drone = new SimpleDrone(batteryLevel, Direction.fromSymbol(direction));
        this.gridSearch = new GridSearch(drone);
        this.report = new MissionReport();
    }

    @Override
    public String takeDecision() {
        String command = gridSearch.performSearch();
        logger.info("Command: {}", command);
        return command;
    }

    @Override
    public void acknowledgeResults(String s) {
        logger.info("Response: {}", s);
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        gridSearch.readResponse(response, map);
    }

    @Override
    public String deliverFinalReport() {
        String finalReport = report.generateReport(map);
        logger.info(finalReport);
        return finalReport;
    }
}