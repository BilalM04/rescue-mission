package ca.mcmaster.se2aa4.island.team107;

public enum Direction {
    NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

    private String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Direction getRight() {
        return values()[(this.ordinal() + 1) % 4];
    }

    public Direction getLeft() {
        return values()[(this.ordinal() - 1 + 4) % 4];
    }

    public static Direction fromSymbol(String s) {
        switch (s) {
            case "N":
                return NORTH;
            case "S":
                return EAST;
            case "W":
                return SOUTH;
            case "E":
                return WEST;
            default:
                return null;
        }
    }
}
