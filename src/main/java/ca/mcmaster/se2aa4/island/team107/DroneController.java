package ca.mcmaster.se2aa4.island.team107;

import org.json.JSONObject;

public class DroneController {
    private Drone drone;

    public DroneController(Drone drone) {
        this.drone = drone;
    }

    public Integer getBatteryLevel() {
        return drone.getBatteryLevel();
    }

    public Direction getHeading(){
        return drone.getHeading() ;
    }

    public int getX(){
        return drone. getX();
    }

    public int getY(){
        return drone. getY();
    }

    public String fly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        drone.flyForward();
        return decision.toString();
    }


}

