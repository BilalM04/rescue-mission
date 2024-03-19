package ca.mcmaster.se2aa4.island.team107.drone;

import ca.mcmaster.se2aa4.island.team107.position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.position.Direction;

public interface Drone {

    Integer getBatteryLevel();

    void drainBattery(Integer cost);

    boolean notEnoughBattery(Integer costFly, Integer costHeading);

    Direction getHeading();

    Coordinate getLocation();

    void flyForward();

    void turnRight();

    void turnLeft();
}