package ca.mcmaster.se2aa4.island.team107;

public class POI {
    private String kind;
    private int x;
    private int y;

    public POI(String kind, int x, int y) {
        this.kind = kind;
        this.x = x;
        this.y = y;
    }

    public String getKind() {
        return this.kind;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }
}
