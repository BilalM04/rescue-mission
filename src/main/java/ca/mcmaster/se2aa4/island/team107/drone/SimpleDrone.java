package ca.mcmaster.se2aa4.island.team107.drone;

import ca.mcmaster.se2aa4.island.team107.position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class SimpleDrone implements Drone {

    private Integer batteryLevel;
    private Direction heading;
    private Coordinate location;
    private Integer costFly = 5;
    private Integer costHeading = 10;

    public SimpleDrone(Integer batteryLevel, Direction heading) {
        this.batteryLevel = batteryLevel;
        this.heading = heading;
        this.location = new Coordinate(0, 0);
    }

    public Integer getBatteryLevel() {
        return this.batteryLevel;
    }

    public void drainBattery(Integer cost) {
        this.batteryLevel -= cost;
    }

    public boolean notEnoughBattery() {
        return ((Math.abs(location.getX()) * costFly) + (Math.abs(location.getY()) * costFly)
                + costHeading) > getBatteryLevel();
    }

    public Direction getHeading() {
        return this.heading;
    }

    public Coordinate getLocation() {
        return new Coordinate(location.getX(), location.getY());
    }

    public void flyForward() {
        switch (this.heading) {
            case Direction.NORTH:
                location.setY(location.getY() + 1);
                break;
            case Direction.EAST:
                location.setX(location.getX() + 1);
                break;
            case Direction.SOUTH:
                location.setY(location.getY() - 1);
                break;
            case Direction.WEST:
                location.setX(location.getX() - 1);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public void turnRight() {
        switch (this.heading) {
            case Direction.NORTH:
                location.setY(location.getY() + 1);
                location.setX(location.getX() + 1);
                break;
            case Direction.EAST:
                location.setY(location.getY() - 1);
                location.setX(location.getX() + 1);
                break;
            case Direction.SOUTH:
                location.setY(location.getY() - 1);
                location.setX(location.getX() - 1);
                break;
            case Direction.WEST:
                location.setY(location.getY() + 1);
                location.setX(location.getX() - 1);
                break;
            default:
                throw new IllegalStateException();
        }
        heading = heading.getRight();
    }

    public void turnLeft() {
        switch (this.heading) {
            case Direction.NORTH:
                location.setY(location.getY() + 1);
                location.setX(location.getX() - 1);
                break;
            case Direction.EAST:
                location.setY(location.getY() + 1);
                location.setX(location.getX() + 1);
                break;
            case Direction.SOUTH:
                location.setY(location.getY() - 1);
                location.setX(location.getX() + 1);
                break;
            case Direction.WEST:
                location.setY(location.getY() - 1);
                location.setX(location.getX() - 1);
                break;
            default:
                throw new IllegalStateException();
        }
        heading = heading.getLeft();
    }
}