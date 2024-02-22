package ca.mcmaster.se2aa4.island.team107;

enum TypePOI {
    CREEK, 
    EMERGENCY_SITE, 
    HOMEBASE
}

public class PointOfInterest {
    private final TypePOI kind;
    private Coordinate location;
    private String id;

    public PointOfInterest(TypePOI kind, Coordinate location, String id) {
        this.kind = kind;
        this.location = location;
        this.id = id;
    }

    public TypePOI getKind() {
        return this.kind;
    }

    public Coordinate getLocation() {
        return this.location;
    }

    public String getID() {
        return this.id;
    }
}
