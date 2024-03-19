package ca.mcmaster.se2aa4.island.team107.drone;

import ca.mcmaster.se2aa4.island.team107.position.Direction;

public interface Controller {

    String fly();

    String heading(Direction dir);

    String echo(Direction dir);

    String scan();

    String stop();
}