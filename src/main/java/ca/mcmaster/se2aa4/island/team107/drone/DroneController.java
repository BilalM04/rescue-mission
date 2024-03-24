package ca.mcmaster.se2aa4.island.team107.drone;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team107.position.Direction;

public class DroneController implements Controller {
    private Drone drone;
    private String decisionKey = "action";

    public DroneController(Drone drone) {
        this.drone = drone;
    }

    public String fly() {
        JSONObject decision = new JSONObject();
        decision.put(decisionKey, "fly");
        drone.flyForward();
        return decision.toString();
    }

    public String heading(Direction dir) {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put(decisionKey, "heading");
        params.put("direction", dir.getSymbol());
        decision.put("parameters", params);

        if (dir.equals(drone.getHeading().getRight())) {
            drone.turnRight();
        } else if (dir.equals(drone.getHeading().getLeft())) {
            drone.turnLeft();
        }

        return decision.toString();
    }

    public String echo(Direction dir) {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put(decisionKey, "echo");
        params.put("direction", dir.getSymbol());
        decision.put("parameters", params);

        return decision.toString();
    }

    public String scan() {
        JSONObject decision = new JSONObject();
        decision.put(decisionKey, "scan");
        return decision.toString();
    }

    public String stop() {
        JSONObject decision = new JSONObject();
        decision.put(decisionKey, "stop");
        return decision.toString();
    }
}