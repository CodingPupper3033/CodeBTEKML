package com.codingpupper3033.codebtekml.kml.geometry;

import com.codingpupper3033.codebtekml.kml.attributes.AltitudeMode;
import com.codingpupper3033.codebtekml.kml.attributes.Coordinates;
import org.w3c.dom.Element;

/**
 * @author Joshua Miller
 */
public class Point extends KMLGeometry {
    private final Coordinates coordinates;
    private final AltitudeMode altitudeMode;

    public Point(Element element) {
        super(element);

        this.coordinates = getCoordinatesFromElement(element);
        this.altitudeMode = getAltitudeModeFromElement(element);
    }


}
