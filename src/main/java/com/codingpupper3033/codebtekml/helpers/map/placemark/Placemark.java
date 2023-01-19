package com.codingpupper3033.codebtekml.helpers.map.placemark;

import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;
import com.codingpupper3033.codebtekml.helpers.map.placemark.DrawPlacemarkSubsectionListener;

/**
 * Default placemark class.
 * Extend this class to make specific placemarks.
 * @author Joshua Miller
 */
public abstract class Placemark {
    /**
     * Gets all coordinates that this placemark uses, so we can pre-process them
     * @return all the Coordinates in this placemark
     */
    public abstract Coordinate[] getCoordinates();

    /**
     * Returns how many subsections this placemark has when drawing
     * @return subsections
     */

    public abstract int getSubSections();

    /**
     * Should draw the placemark into the game and tell when subsection is made
     * @param blockName block to draw placemark with
     */
    public abstract void draw(String blockName, DrawPlacemarkSubsectionListener listener);

    /**
     * Should draw the placemark into the game
     * @param blockName block to draw placemark with
     */
    public abstract void draw(String blockName);
}
