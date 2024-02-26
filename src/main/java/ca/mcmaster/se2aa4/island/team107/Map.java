package ca.mcmaster.se2aa4.island.team107;

import java.util.ArrayList;
import java.util.List;

public class Map {
    List<POI> creeks;
    List<POI> emergencySites;

    public Map() {
        this.creeks = new ArrayList<>();
        this.emergencySites = new ArrayList<>();
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

        emergencySites.add(site);
    }
}
