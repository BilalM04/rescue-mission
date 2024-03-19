package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.position.POI;
import ca.mcmaster.se2aa4.island.team107.position.POI.TypePOI;

public class POITest {
    private final POI poi = new POI(TypePOI.CREEK, new Coordinate(7, 19), "1234567890");

    @Test
    public void testGetKind() {
        assertEquals(poi.getKind(), TypePOI.CREEK);
    }

    @Test
    public void testGetLocation() {
        Coordinate coord = new Coordinate(7, 19);
        assertEquals(poi.getLocation().getX(), coord.getX());
        assertEquals(poi.getLocation().getY(), coord.getY());
    }

    @Test
    public void testGetID() {
        assertEquals(poi.getID(), "1234567890");
    }
}
