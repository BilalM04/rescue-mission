package ca.mcmaster.se2aa4.island.team107.report;

import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team107.map.Map;

public class MissionReport implements Report {

    public String generateReport(Map map) {
        StringBuilder report = new StringBuilder();
        report.append("Emergency Site ID: ").append(emergencySiteID(map)).append("\n");
        report.append("Closest Creek ID: ").append(closestCreekID(map)).append("\n");
        return report.toString();
    }

    private String closestCreekID(Map map) {
        String creekID;

        try {
            creekID = map.getClosestCreekID();
        } catch (NoSuchElementException e) {
            creekID = "Unable to locate a creek.";
        }

        return creekID;
    }

    private String emergencySiteID(Map map) {
        String emergencySiteID;

        try {
            emergencySiteID = map.getEmergencySiteID();
        } catch (NoSuchElementException e) {
            emergencySiteID = "Unable to locate emergency site.";
        }

        return emergencySiteID;
    }
}
