package com.codingpupper3033.codebtekml.helpers.map.coordinate;

import com.codingpupper3033.codebtekml.helpers.map.altitude.AltitudeMode;
import com.codingpupper3033.codebtekml.helpers.map.altitude.GroundLevelProcessor;
import com.codingpupper3033.codebtekml.helpers.map.altitude.NoAltitudeException;

import java.io.IOException;

/**
 * Stores a coordinate in the world
 * @author Joshua Miller
 */
public class Coordinate {
    private final double lon;
    private final double lat;
    private double elv;

    private Double groundLevel;
    
    private AltitudeMode altitudeMode;

    /**
     * Instantiates a new Coordinate.
     *
     * @param lat          the latitude
     * @param lon          the longitude
     * @param elv          the elevation
     * @param altitudeMode the altitude mode
     * @see AltitudeMode
     */
    public Coordinate(double lat, double lon, double elv, AltitudeMode altitudeMode) {
        this.lon = lon;
        this.lat = lat;
        this.elv = elv;
        this.altitudeMode = altitudeMode;
    }

    public double getLongitude() {
        return lon;
    }

    public double getLatitude() {
        return lat;
    }

    public double getElevation() {
        return elv;
    }

    public void setElevation(int elevation) { elv = elevation; }

    public AltitudeMode getAltitudeMode() {
        return altitudeMode;
    }

    public void setAltitudeMode(AltitudeMode altitudeMode) {
        this.altitudeMode = altitudeMode;
    }

    public void setGroundLevel(Double groundLevel) {
        this.groundLevel = groundLevel;
    }

    @Override
    public String toString() {
        return "[" + lat +
                ", " + lon +
                ", " + elv +
                ", " + altitudeMode.getNodeName() +
                ", " + groundLevel +
                ']';
    }

    /**
     * Gets the altitude of a point if stored, or calculating it if not yet calculated
     * @return Standard altitude
     * @throws NoAltitudeException
     * @throws IOException
     */
    public double getAltitude() throws NoAltitudeException, IOException {
        switch (altitudeMode) {
            case ABSOLUTE:
                return getElevation();
            case RELATIVE_TO_GROUND:
            case DEFAULT:
                if (groundLevel == null) {
                    groundLevel = GroundLevelProcessor.defaultProcessor.getGroundLevel(this);
                }
                return groundLevel + getElevation();
            default:
                throw (new NoAltitudeException());
        }
    }

    /**
     * Returns whether the altitude is known, or needs to be calculated
     * @return if the altitude is known
     */
    public boolean isAltitudeProcessed() {
        return groundLevel != null;
    }
}
