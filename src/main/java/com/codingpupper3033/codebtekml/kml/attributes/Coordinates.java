package com.codingpupper3033.codebtekml.kml.attributes;

import com.codingpupper3033.codebtekml.kml.data.Coordinate;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * @author Joshua Miller
 */
public class Coordinates extends ArrayList<Coordinate> {
    /**
     * Constructor from a DOM coordinate element
     * @param element Dom Element representing the coordinate
     */
    public Coordinates(Element element) {
        // All Coordinates
        String allCoordinatesData = element.getTextContent().trim();
        for (String coordinateData: allCoordinatesData.split(" ")) {
            coordinateData = coordinateData.trim(); // This specific coordinate

            add(new Coordinate(coordinateData));
        }
    }
}
