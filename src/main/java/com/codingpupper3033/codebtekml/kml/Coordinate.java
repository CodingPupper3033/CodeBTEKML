package com.codingpupper3033.codebtekml.kml;

import java.awt.geom.Point2D;

/**
 * Stores the latitude, longitude, altitude mode, and associated height
 * @author Joshua Miller
 */
public class Coordinate { // Extends Point2D.Double for the hashing ability!
    private final Point2D.Double point;
    private final double height;

    public Coordinate(double latitude, double longitude, double height) {
        this.point = new Point2D.Double(latitude, longitude);
        this.height = height;
    }

    public double getLatitude() {
        return point.getX();
    }

    public double getLongitude() {
        return point.getY();
    }

    /**
     * Compares <b>only</b> the Latitude and Longitude of another Coordinate.
     * Note, this method does not care about height.
     * @param obj an object to be compared to
     * @return whether the Latitude and Longitude are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate){ // Has to be a Coordinate to be equal
            Coordinate objCoordinate = (Coordinate)obj;
            // Also has to be the same lat and lon, no care for height
            return objCoordinate.getLatitude() == getLatitude() && objCoordinate.getLongitude() == getLongitude();
        }

        return false;
    }

    /**
     * @return Returns the hash code of the point.
     */
    @Override
    public int hashCode() {
        return point.hashCode();
    }
}
