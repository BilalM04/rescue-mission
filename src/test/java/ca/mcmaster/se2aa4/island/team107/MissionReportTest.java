package ca.mcmaster.se2aa4.island.team107;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team107.map.ListMap;
import ca.mcmaster.se2aa4.island.team107.map.Map;
import ca.mcmaster.se2aa4.island.team107.position.Coordinate;
import ca.mcmaster.se2aa4.island.team107.map.POI;
import ca.mcmaster.se2aa4.island.team107.map.POI.TypePOI;
import ca.mcmaster.se2aa4.island.team107.report.MissionReport;
import ca.mcmaster.se2aa4.island.team107.report.Report;

public class MissionReportTest {
    private Map map;
    private Report report; 

    @BeforeEach
    public void setUp() {
        map = new ListMap();
    }

    @Test
    public void testGenerateReport() {
        map.addPOI(new POI(TypePOI.CREEK, new Coordinate(0, 0), "creek"));
        map.addPOI(new POI(TypePOI.EMERGENCY_SITE, new Coordinate(0, 0), "site"));
        report = new MissionReport();
        String result = "Emergency Site ID: site\nClosest Creek ID: creek\n";
        assertEquals(report.generateReport(map), result); 
    }

    @Test
    public void testGenerateReportNull() {
        report = new MissionReport();
        String result = "Emergency Site ID: Unable to locate emergency site.\nClosest Creek ID: Unable to locate a creek.\n";
        assertEquals(report.generateReport(map), result); 
    }
}
