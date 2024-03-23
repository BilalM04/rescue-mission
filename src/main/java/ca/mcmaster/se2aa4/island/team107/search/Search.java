package ca.mcmaster.se2aa4.island.team107.search;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team107.map.Map;

public interface Search {

    String performSearch();

    void readResponse(JSONObject response, Map map);
} 
