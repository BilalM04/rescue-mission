package ca.mcmaster.se2aa4.island.team107;

enum TypePOI {
    CREEK,
    EMERGENCY_SITE
}

public class POI {
    private TypePOI kind;
    private int x, y;
    private String id;

    public POI(TypePOI kind, int x, int y, String id) {
        this.kind = kind;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public TypePOI getKind() {
        return this.kind;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public String getID() {
        return this.id;
    }
}
