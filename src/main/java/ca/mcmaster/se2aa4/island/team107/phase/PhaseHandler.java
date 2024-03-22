package ca.mcmaster.se2aa4.island.team107.phase;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.drone.Drone;

public interface PhaseHandler {
    
    String performAction();

    void changeState(JSONObject info);
}
