package ca.mcmaster.se2aa4.island.team107;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.map.ListMap;
import ca.mcmaster.se2aa4.island.team107.map.POI;
import ca.mcmaster.se2aa4.island.team107.map.POI.TypePOI;
import ca.mcmaster.se2aa4.island.team107.position.Coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapListTest {
    private ListMap map;
    private POI creek1, creek2, creek3, creek4, creek5, site;

    @BeforeEach
    public void setup() {
        map = new ListMap();
        creek1 = new POI(TypePOI.CREEK, new Coordinate(0, 0), "1");
        creek2 = new POI(TypePOI.CREEK, new Coordinate(0, 50), "2");
        creek3 = new POI(TypePOI.CREEK, new Coordinate(50, 0), "3");
        creek4 = new POI(TypePOI.CREEK, new Coordinate(50, 50), "4");
        creek5 = new POI(TypePOI.CREEK, new Coordinate(15, 15), "5");
        site = new POI(TypePOI.EMERGENCY_SITE, new Coordinate(25, 25), "site");
    }

    @Test
    public void testGetClosetCreek() {
        map.addPOI(creek1);
        map.addPOI(creek2);
        map.addPOI(creek3);
        map.addPOI(creek4);
        map.addPOI(creek5);
        map.addPOI(site);
        assertEquals("5", map.getClosestCreekID());
    }

    @Test
    public void testGetEmergencySite(){
        map.addPOI(site);
        assertEquals("site", map.getEmergencySiteID());
    }
}
