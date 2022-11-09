package com.codingpupper3033.codebtekml.helpers.map;

import org.w3c.dom.Element;

/**
 * Default placemark class.
 * Extend this class to make specific placemarks.
 * @author Joshua Miller
 */
public abstract class Placemark {
    /**
     * Should be overwritten
     * @param element the new placemark
     */
    public Placemark(Element element) {

    }

    /**
     * Gets all coordinates that this placemark uses, so we can pre-process them
     * @return all the Coordinates in this placemark
     */
    public abstract Coordinate[] getCoordinates();

    /**
     * Should draw the placemark into the game
     * @param blockName
     */
    public abstract void draw(String blockName);
}
