package ca.mcmaster.se2aa4.island.team107.search;

import org.json.JSONObject;

public interface Search {

    public String performSearch();

    public void readResponse(JSONObject response);
} 
