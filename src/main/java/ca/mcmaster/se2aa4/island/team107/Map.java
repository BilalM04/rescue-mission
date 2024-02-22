package ca.mcmaster.se2aa4.island.team107;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Map {
    private Drone drone;
    private List<PointOfInterest> creeks;
    private PointOfInterest emergencySite;
    private PointOfInterest homeBase;

    public Map(Drone drone) {
        this.creeks = new ArrayList<>();
        this.drone = drone;
        homeBase = new PointOfInterest(TypePOI.HOMEBASE, new Coordinate(0, 0), null);
    }

    public void addPOI(PointOfInterest POI) {
        if (POI.getKind() == TypePOI.CREEK) {
            creeks.add(POI);
        } else if (POI.getKind() == TypePOI.EMERGENCY_SITE) {
            emergencySite = POI;
        }
    }

    public PointOfInterest getClosestCreek() {
        if (creeks.size() == 0 || emergencySite == null) {
            throw new NoSuchElementException("Cannot find closest creek: missing data");
        }

        PointOfInterest closestCreek = null;
        Double shortestDistance = Double.MAX_VALUE;
        Coordinate emergencyLoc = emergencySite.getLocation();

        for (PointOfInterest creek : creeks) {
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
