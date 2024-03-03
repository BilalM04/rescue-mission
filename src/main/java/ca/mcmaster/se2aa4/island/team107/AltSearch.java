package ca.mcmaster.se2aa4.island.team107;

import org.json.JSONObject;

public class AltSearch implements Search{

    private enum State {A, B, C, D, E, F, G}
    private State state;
    private Drone drone;
    private Map map;

    public AltSearch(Drone drone, Map map) {
        this.drone = drone;
        this.map = map;
        this.state = State.A;
    }

    public String performSearch() {
        return "";
    }

    public void readResponse(JSONObject response) {
        
    }
}
