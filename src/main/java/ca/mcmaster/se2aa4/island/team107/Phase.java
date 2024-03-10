package ca.mcmaster.se2aa4.island.team107;

public interface Phase {

    public Phase getNextPhase();

    public String getDroneCommand();

    public void process(String response);

    public boolean isFinished();

    public boolean isLastPhase();

}
