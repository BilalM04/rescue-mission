package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.position.ListMap;
import ca.mcmaster.se2aa4.island.team107.position.Map;
import ca.mcmaster.se2aa4.island.team107.position.POI;
import ca.mcmaster.se2aa4.island.team107.position.POI.TypePOI;
import ca.mcmaster.se2aa4.island.team107.report.MissionReport;
import ca.mcmaster.se2aa4.island.team107.report.Report;

public class MissionReportTest {
    private Map map;
    private Report report; 

    @BeforeEach
    public void setUp() {
        map = new ListMap();
        map.addPOI(new POI(TypePOI.CREEK, new Coordinate(0, 0), "creek"));
        map.addPOI(new POI(TypePOI.EMERGENCY_SITE, new Coordinate(0, 0), "site"));
        report = new MissionReport(map);
    }

    @Test
    public void testGenerateReport() {
        String result = "Emergency Site ID: site\nClosest Creek ID: creek\n";
        assertEquals(report.generateReport(), result); 
    }
}
