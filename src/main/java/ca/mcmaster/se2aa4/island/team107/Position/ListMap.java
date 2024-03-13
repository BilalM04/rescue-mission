package ca.mcmaster.se2aa4.island.team107.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ListMap implements Map {
    private List<POI> creeks;
    private POI emergencySite;
    private POI homeBase;

    public ListMap() {
        this.creeks = new ArrayList<>();
        this.homeBase = new POI(POI.TypePOI.HOMEBASE, new Coordinate(0, 0), null);
    }

    public void addPOI(POI poi) {
        if (poi.getKind() == POI.TypePOI.CREEK) {
            creeks.add(poi);
        } else if (poi.getKind() == POI.TypePOI.EMERGENCY_SITE) {
            emergencySite = poi;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public POI getClosestCreek() {
        if (creeks.size() == 0 || emergencySite == null) {
            throw new NoSuchElementException("Cannot find closest creek: missing data");
        }

        POI closestCreek = null;
        Double shortestDistance = Double.MAX_VALUE;
        Coordinate emergencyLoc = emergencySite.getLocation();

        for (POI creek : creeks) {
            Coordinate creekLoc = creek.getLocation();
            Double currDistance = creekLoc.distanceTo(emergencyLoc);
            if (currDistance < shortestDistance) {
                shortestDistance = currDistance;
                closestCreek = creek;
            }
        }

        return closestCreek;
    }
}
