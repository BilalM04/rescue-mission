package ca.mcmaster.se2aa4.island.team107.Drone;

import ca.mcmaster.se2aa4.island.team107.Position.Direction;

public interface Drone {
    
    public Integer getBatteryLevel();

    public void drainBattery(Integer cost);

    public boolean notEnoughBattery(Integer costFly, Integer costHeading);
    
    public Direction getHeading();

    public int getX();

    public int getY();

    public void flyForward();

    public void turnRight();

    public void turnLeft();
}
