package ca.mcmaster.se2aa4.island.team107;

public class Drone {
    private Integer batteryLevel;
    private Direction heading;
    private Coordinate location;

    public Drone(Integer batteryLevel, Direction heading) {
        this.batteryLevel = batteryLevel;
        this.heading = heading;
        this.location = new Coordinate(0, 0);
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void drainBattery(Integer cost) {
        this.batteryLevel -= cost;
    }

    public Direction getHeading() {
        return heading;
    }

    public int getX() {
        return this.location.getX();
    }

    public int getY() {
        return this.location.getY();
    }

    public void flyForward() {
        switch (this.heading) {
            case Direction.North:
                this.location.setY(getY() + 1);
                break;
            case Direction.East:
                this.location.setX(getX() + 1);
                break;
            case Direction.South:
                this.location.setY(getY() - 1);
                break;
            case Direction.West:
                this.location.setX(getX() - 1);
                break;
        }
    }

    public void turnRight() {
        switch (this.heading) {
            case Direction.North:
                this.location.setY(getY() + 1);
                this.location.setX(getX() + 1);
                break;
            case Direction.East:
                this.location.setY(getY() - 1);
                this.location.setX(getX() + 1);
                break;
            case Direction.South:
                this.location.setY(getY() - 1);
                this.location.setX(getX() - 1);
                break;
            case Direction.West:
                this.location.setY(getY() + 1);
                this.location.setX(getX() - 1);
                break;
        }
        heading = heading.getRight();
    }

    public void turnLeft() {
        switch (this.heading) {
            case Direction.North:
                this.location.setY(getY() + 1);
                this.location.setX(getX() - 1);
                break;
            case Direction.East:
                this.location.setY(getY() + 1);
                this.location.setX(getX() + 1);
                break;
            case Direction.South:
                this.location.setY(getY() - 1);
                this.location.setX(getX() + 1);
                break;
            case Direction.West:
                this.location.setY(getY() - 1);
                this.location.setX(getX() - 1);
                break;
        }
        heading = heading.getLeft();
    }

}
