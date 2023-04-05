package com.codingpupper3033.codebtekml.kml.data;

import java.awt.geom.Point2D;

/**
 * Stores the latitude, longitude and altitude
 * @author Joshua Miller
 */
public class Coordinate {
    private final Point2D.Double point; // Stored in a Point2D for the ability to hash
    private final double altitude; // Altitude from the file

    /**
     * Constructor from the values of a coordinate
     * @param latitude
     * @param longitude
     * @param altitude
     */
    public Coordinate(double latitude, double longitude, double altitude) {
        this.point = new Point2D.Double(latitude, longitude);
        this.altitude = altitude;
    }

    /**
     * Constructs a coordinate from a string representation like how it would be stored in KML
     * @param coordinateData
     */
    public Coordinate(String coordinateData) {
        String[] splitData = coordinateData.split(",");
        double[] latLonAlt = new double[3];

        // Trim all numbers
        for (int i = 0; i < latLonAlt.length; i++) {
            latLonAlt[i] = Double.parseDouble(splitData[i].trim());
        }

        this.point = new Point2D.Double(latLonAlt[0], latLonAlt[1]);
        this.altitude = latLonAlt[2];
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

    @Override
    public String toString() {
        return "(" + point +
                ", altitude=" + altitude +
                ')';
    }
}
