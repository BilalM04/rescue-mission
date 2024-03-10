package ca.mcmaster.se2aa4.island.team107.Position;

public class POI {

    public enum TypePOI {
        CREEK,
        EMERGENCY_SITE,
        HOMEBASE
    }

    private final TypePOI kind;
    private final Coordinate location;
    private final String id;

    public POI(TypePOI kind, Coordinate location, String id) {
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
