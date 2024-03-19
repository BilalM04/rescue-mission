package ca.mcmaster.se2aa4.island.team107.phase;

import org.json.JSONObject;

public interface Phase {

    String getDroneCommand();

    void processInfo(JSONObject info);

    Phase getNextPhase();

    boolean isFinished();

    boolean isLastPhase();
}
