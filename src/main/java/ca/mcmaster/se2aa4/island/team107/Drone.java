package ca.mcmaster.se2aa4.island.team107;

import org.json.JSONObject;

public class Drone {
    private Integer batteryLevel;
    private Direction heading;
    private int x;
    private int y;

    public Drone(Integer batteryLevel, Direction heading) {
        this.batteryLevel = batteryLevel;
        this.heading = heading;
        this.x = 0;
        this.y = 0;
    }

    public Integer getBatteryLevel() {
        return this.batteryLevel;
    }

    public Direction getHeading() {
        return this.heading;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public String fly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");

        switch (this.heading) {
            case Direction.North: 
                this.y++;
                break;
            case Direction.East:
                this.x++; 
                break;
            case Direction.South:
                this.y--;
                break;
            case Direction.West:
                this.x--;
                break;
        }

        return decision.toString();
    }

    public String heading(Direction dir) {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();
        
        decision.put("action", "heading");
        params.put("direction", dir.toString());
        decision.put("parameters", params);

        if (heading.getRight().equals(dir)) {
            this.turnRight();
        } else if (heading.getLeft().equals(dir)) {
            this.turnLeft();
        }

        return decision.toString();
    }

    private void turnRight() {
        switch (this.heading) {
            case Direction.North: 
                this.y++;
                this.x++;
                break;
            case Direction.East:
                this.y--;
                this.x++;
                break;
            case Direction.South:
                this.y--;
                this.x--;
                break;
            case Direction.West:
                this.y++;
                this.x--;
                break;
        }
        heading = heading.getRight();
    }

    private void turnLeft() {
        switch (this.heading) {
            case Direction.North: 
                this.y++;
                this.x--;
                break;
            case Direction.East:
                this.y++;
                this.x++;
                break;
            case Direction.South:
                this.y--;
                this.x++;
                break;
            case Direction.West:
                this.y--;
                this.x--;
                break;
        }
        heading = heading.getLeft();
    }

    public String echo(Direction dir) {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();
        
        decision.put("action", "echo");
        params.put("direction", dir.toString());
        decision.put("parameters", params);

        return decision.toString();
    }

    public String scan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        return decision.toString();
    }

    public String stop() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");
        return decision.toString();
    }

    public void updateDrone(JSONObject response) {
        Integer cost = response.getInt("cost");
        this.batteryLevel -= cost;
    }
}
