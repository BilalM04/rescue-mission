package ca.mcmaster.se2aa4.island.team107.Drone;

import ca.mcmaster.se2aa4.island.team107.Position.Direction;

public interface Controller {

    public String fly();

    public String heading(Direction dir);

    public String echo(Direction dir);
    
    public String scan();

    public String stop();
}
