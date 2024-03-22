package ca.mcmaster.se2aa4.island.team107.phase;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;
import ca.mcmaster.se2aa4.island.team107.drone.Drone;
import ca.mcmaster.se2aa4.island.team107.drone.DroneController;
import ca.mcmaster.se2aa4.island.team107.drone.SimpleDrone;

public class GridSearchHandler implements PhaseHandler{
    private Phase phase;
    private Drone drone;
    private Controller controller;

    public GridSearchHandler() {
        this.phase = new MoveToCorner(null);
        this.drone = new SimpleDrone(null, null);
        this.controller = new DroneController(drone);
    }
    
    public String performAction() {
        return "";
    }

    public void changeState(JSONObject info) {

    }
}
