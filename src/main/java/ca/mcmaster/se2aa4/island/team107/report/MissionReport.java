package ca.mcmaster.se2aa4.island.team107.report;

import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team107.position.Map;

public class MissionReport implements Report {

    private Map map;

    public MissionReport(Map map) {
        this.map = map;
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Emergency Site ID: ").append(emergencySiteID()).append("\n");
        report.append("Closest Creek ID: ").append(closestCreekID()).append("\n");
        return report.toString();
    }

    private String closestCreekID() {
        String creekID;

        try {
            creekID = map.getClosestCreekID();
        } catch (NoSuchElementException e) {
            creekID = "Unable to locate a creek.";
        }

        return creekID;
    }

    private String emergencySiteID() {
        String emergencySiteID;

        try {
            emergencySiteID = map.getEmergencySiteID();
        } catch (NoSuchElementException e) {
            emergencySiteID = "Unable to locate emergency site.";
        }

        return emergencySiteID;
    }
}
