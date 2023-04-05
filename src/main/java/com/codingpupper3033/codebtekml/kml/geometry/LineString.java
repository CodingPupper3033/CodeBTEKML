package com.codingpupper3033.codebtekml.kml.geometry;

import com.codingpupper3033.codebtekml.kml.attributes.AltitudeMode;
import com.codingpupper3033.codebtekml.kml.attributes.Coordinates;
import org.w3c.dom.Element;

/**
 * @author Joshua Miller
 */
public class LineString extends KMLGeometry {
    private final Coordinates coordinates;
    private final AltitudeMode altitudeMode;

    public LineString(Element element) {
        super(element);

        this.coordinates = getCoordinatesFromElement(element);
        this.altitudeMode = getAltitudeModeFromElement(element);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
