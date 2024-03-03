package ca.mcmaster.se2aa4.island.team107;

public interface Drone {
    
    public Integer getBatteryLevel();

    public void drainBattery(Integer cost);

    public Direction getHeading();

    public int getX();

    public int getY();

    public void flyForward();

    public void turnRight();

    public void turnLeft();
}
