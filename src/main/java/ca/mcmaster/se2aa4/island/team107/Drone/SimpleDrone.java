package ca.mcmaster.se2aa4.island.team107.Drone;

import ca.mcmaster.se2aa4.island.team107.Position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.Position.Direction;

public class SimpleDrone implements Drone {

    private Integer batteryLevel;
    private Direction heading;
    private Coordinate location;

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

    public boolean notEnoughBattery(Integer costFly, Integer costHeading) {
        if (((Math.abs(getX()) * costFly) + (Math.abs(getY()) * costFly) + costHeading) > getBatteryLevel()) {
            return false;
        }
        return true;
    }

    public Direction getHeading() {
        return this.heading;
    }

    public int getX() {
        return this.location.getX();
    }

    public int getY() {
        return this.location.getY();
    }

    public void flyForward() {
        switch (this.heading) {
            case Direction.NORTH:
                location.setY(getY() + 1);
                break;
            case Direction.EAST:
                location.setX(getX() + 1);
                break;
            case Direction.SOUTH:
                location.setY(getY() - 1);
                break;
            case Direction.WEST:
                location.setX(getX() - 1);
                break;
        }
    }

    public void turnRight() {
        switch (this.heading) {
            case Direction.NORTH:
                location.setY(getY() + 1);
                location.setX(getX() + 1);
                break;
            case Direction.EAST:
                location.setY(getY() - 1);
                location.setX(getX() + 1);
                break;
            case Direction.SOUTH:
                location.setY(getY() - 1);
                location.setX(getX() - 1);
                break;
            case Direction.WEST:
                location.setY(getY() + 1);
                location.setX(getX() - 1);
                break;
        }
        heading = heading.getRight();
    }

    public void turnLeft() {
        switch (this.heading) {
            case Direction.NORTH:
                location.setY(getY() + 1);
                location.setX(getX() - 1);
                break;
            case Direction.EAST:
                location.setY(getY() + 1);
                location.setX(getX() + 1);
                break;
            case Direction.SOUTH:
                location.setY(getY() - 1);
                location.setX(getX() + 1);
                break;
            case Direction.WEST:
                location.setY(getY() - 1);
                location.setX(getX() - 1);
                break;
        }
        heading = heading.getLeft();
    }
}
