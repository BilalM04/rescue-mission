package ca.mcmaster.se2aa4.island.team107;

import org.json.JSONObject;

public class DroneController {
    private Drone drone;

    public DroneController(Drone drone) {
        this.drone = drone;
    }

    public String fly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        drone.flyForward();
        return decision.toString();
    }

    public String heading(Direction dir) {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();
        
        decision.put("action", "heading");
        params.put("direction", dir.getSymbol());
        decision.put("parameters", params);

        Direction currHeading = drone.getHeading();

        if (dir.equals(currHeading.getRight())) {
            drone.turnRight();
        } else if (dir.equals(currHeading.getLeft())) {
            drone.turnLeft();
        }

        return decision.toString();
    }

    public String echo(Direction dir) {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();
        
        decision.put("action", "echo");
        params.put("direction", dir.getSymbol());
        decision.put("parameters", params);

        return decision.toString();
    }

    public String scan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        return decision.toString();
    }

    public String stop() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");
        return decision.toString();
    }
}
