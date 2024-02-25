package ca.mcmaster.se2aa4.island.team107;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Map {
    private final Logger logger = LogManager.getLogger();
    private List<POI> creeks;
    private List<POI> emergencySite;

    public Map() {
        this.creeks = new ArrayList<>();
        this.emergencySite = new ArrayList<>();
    }

    public void addCreek(POI creek) {
        if (creek.getKind() != TypePOI.CREEK) {
            throw new IllegalArgumentException();
        }
        creeks.add(creek);
    }

    public void addEmergencySite(POI site) {
        if (site.getKind() != TypePOI.EMERGENCY_SITE) {
            throw new IllegalArgumentException();
        }
        emergencySite.add(site);
    }

    public void closestInlet() {
        if (emergencySite.size() == 0) {
            throw new IllegalArgumentException();
        }
        double distance = 1000000;
        int x = 0;
        int y = 0;
        String id = "";
        Coordinate creek;
        Coordinate site = new Coordinate(emergencySite.get(0).x(), emergencySite.get(0).y());
        for (int i = 0; i < creeks.size(); i++) {
            creek = new Coordinate(creeks.get(i).x(), creeks.get(i).y());
            if (site.distanceTo(creek) < distance) {
                distance = site.distanceTo(creek);
                x = creek.getX();
                y = creek.getY();
                id = creeks.get(i).getID();
            }
        }
        logger.info("The distance between the closest creek and emergency site is: {}", distance);
        logger.info("The Emergency site is located at x: {} y: {}", site.getX(), site.getY());
        logger.info("The Creek is located at x: {} y: {} ID: {}", x, y, id);
    }

    public void printCreeks() {
        for (int i = 0; i < creeks.size(); i++) {
            POI temp = creeks.get(i);
            TypePOI test = temp.getKind();
            logger.info("ID: {}, Type: {}, Position X: {}, Position Y: {},", temp.getID(),
                    test, temp.x(), temp.y());
        }
    }

    public void printSites() {
        for (int i = 0; i < emergencySite.size(); i++) {
            POI temp = emergencySite.get(i);
            TypePOI test = temp.getKind();
            logger.info("ID: {}, Type: {}, Position X: {}, Position Y: {},", temp.getID(),
                    test, temp.x(), temp.y());
        }
    }
}
