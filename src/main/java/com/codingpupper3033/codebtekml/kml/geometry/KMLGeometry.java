package com.codingpupper3033.codebtekml.kml.geometry;

import com.codingpupper3033.codebtekml.kml.KMLElement;
import com.codingpupper3033.codebtekml.kml.attributes.AltitudeMode;
import com.codingpupper3033.codebtekml.kml.attributes.Coordinates;
import org.w3c.dom.Element;

/**
 * @author Joshua Miller
 */
public class KMLGeometry extends KMLElement {

    public KMLGeometry(Element element) {

    }

    public Coordinates getCoordinatesFromElement(Element element) {
        return new Coordinates((Element) element.getElementsByTagName(TAG_COORDINATES).item(0));
    }

    public AltitudeMode getAltitudeModeFromElement(Element element) {
        // Get Altitude Mode String
        String elementAltitudeModeString = element.getElementsByTagName(TAG_ALTITUDE_MODE).item(0).getTextContent().trim();
        AltitudeMode altitudeModeFound = null;
        // Find Altitude Mode String in list Altitude Mode ENUM
        for (AltitudeMode altitudeMode: AltitudeMode.values()) {
            if (altitudeMode.getNodeName().equals(elementAltitudeModeString)) {
                altitudeModeFound = altitudeMode;
                break;
            }
        }

        // Check if we found an altitude mode
        if (altitudeModeFound == null) {
            altitudeModeFound = AltitudeMode.DEFAULT;
        }

        return altitudeModeFound;
    }
}
