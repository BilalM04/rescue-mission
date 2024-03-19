package ca.mcmaster.se2aa4.island.team107.phase;

import org.json.JSONObject;

public interface Phase {

    public String getDroneCommand();

    public void processInfo(JSONObject info);

    public Phase getNextPhase();

    public boolean isFinished();

    public boolean isLastPhase();

}
