package ca.mcmaster.se2aa4.island.team107;

public enum Direction {
    North("N"), East("E"), South("S"), West("W");

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
                return North;
            case "S":
                return South;
            case "W":
                return West;
            case "E":
                return East;
            default:
                return null;
        }
    }
}
