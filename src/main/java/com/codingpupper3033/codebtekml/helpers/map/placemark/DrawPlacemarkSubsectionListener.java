package com.codingpupper3033.codebtekml.helpers.map.placemark;

/**
 * Listener to receive an update on how many placemarks have been drawn
 * @author Joshua Miller
 */
public interface DrawPlacemarkSubsectionListener {
    void subsectionDrawn(int subsectionNumber, int total);
}
