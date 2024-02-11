package ca.mcmaster.se2aa4.island.team107;

public enum Direction {
    North('N'), East('E'), South('S'), West('W');

    private char symbol;

    Direction(char symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return this.symbol + "";
    }

    public Direction getRight() {
        return values()[(this.ordinal() + 1) % 4];
    }

    public Direction getLeft() {
        return values()[(this.ordinal() - 1 + 4) % 4];
    }
}
