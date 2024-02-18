package ca.mcmaster.se2aa4.island.team107;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Map {
    private final Logger logger = LogManager.getLogger();
    List<POI> creeks;
    List<POI> emergencySite;

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
