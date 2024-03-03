package ca.mcmaster.se2aa4.island.team107;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private enum State {
        A,
        B
    }

    private Drone drone;
    private Search gridSearch;
    private Search cornerSearch;
    private Map map;
    private State state;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        this.map = new ListMap();
        this.drone = new SimpleDrone(batteryLevel, Direction.fromSymbol(direction));
        this.gridSearch = new GridSearch(drone, map);
        this.cornerSearch = new CornerSearch(drone);
        this.state = State.A;
    }

    @Override
    public String takeDecision() {
        String command = "";

        if (state == State.A) {
            command = cornerSearch.performSearch();
            if (command.equals("end")) {
                state = State.B;
            }
        }

        if (state == State.B) {
            command = gridSearch.performSearch();
        }

        return command;
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));

        if (state == State.A) {
            cornerSearch.readResponse(response);
        } else {
            gridSearch.readResponse(response);
        }
    }

    @Override
    public String deliverFinalReport() {
        POI creek = map.getClosestCreek();
        logger.info("** Closest creek: " + creek.getID());

        String result = "The closest creek is id: " + creek.getID();
        
        return result;
    }
}
