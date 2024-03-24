package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.map.POI;
import ca.mcmaster.se2aa4.island.team107.map.POI.TypePOI;
import ca.mcmaster.se2aa4.island.team107.position.Coordinate;

public class POITest {
    private final POI poi = new POI(TypePOI.CREEK, new Coordinate(7, 19), "1234567890");

    @Test
    public void testGetKind() {
        assertEquals(TypePOI.CREEK, poi.getKind());
    }

    @Test
    public void testGetLocation() {
        Coordinate coord = new Coordinate(7, 19);
        assertEquals(coord.getX(), poi.getLocation().getX());
        assertEquals(coord.getY(), poi.getLocation().getY());
    }

    @Test
    public void testGetID() {
        assertEquals("1234567890", poi.getID());
    }
}
