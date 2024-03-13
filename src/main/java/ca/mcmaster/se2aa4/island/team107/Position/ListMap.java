package ca.mcmaster.se2aa4.island.team107.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team107.Position.POI.TypePOI;

public class ListMap implements Map {
    private final List<POI> creeks;
    private POI emergencySite;

    public ListMap() {
        this.creeks = new ArrayList<>();
    }

    public void addPOI(POI poi) {
        if (poi.getKind() == TypePOI.CREEK) {
            creeks.add(poi);
        } else if (poi.getKind() == TypePOI.EMERGENCY_SITE) {
            emergencySite = poi;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getEmergencySiteID() {
        if (emergencySite == null) {
            throw new NoSuchElementException("Cannot find emergency site: missing data");
        }

        return this.emergencySite.getID();
    }

    public String getClosestCreekID() {
        if (creeks.size() == 0) {
            throw new NoSuchElementException("Cannot find closest creek: missing data");
        }

        POI closestCreek = null;
        Double shortestDistance = Double.MAX_VALUE;
        Coordinate emergencyLoc;

        if (emergencySite != null) {
            emergencyLoc = emergencySite.getLocation();
        } else {
            emergencyLoc = new Coordinate(0, 0);
        }

        for (POI creek : creeks) {
            Coordinate creekLoc = creek.getLocation();
            Double currDistance = creekLoc.distanceTo(emergencyLoc);
            if (currDistance < shortestDistance) {
                shortestDistance = currDistance;
                closestCreek = creek;
            }
        }

        return closestCreek.getID();
    }
}
