package ca.mcmaster.se2aa4.island.team107.phase;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team107.drone.Controller;

public interface Phase {

    String getDroneCommand(Controller controller);

    void processInfo(JSONObject info);

    Phase getNextPhase();

    boolean isFinished();

    boolean isLastPhase();
}
