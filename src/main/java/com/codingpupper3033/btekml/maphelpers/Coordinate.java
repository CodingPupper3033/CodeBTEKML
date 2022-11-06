package com.codingpupper3033.btekml.maphelpers;

import com.codingpupper3033.btekml.maphelpers.altitude.AltitudeMode;
import com.codingpupper3033.btekml.maphelpers.altitude.AltitudeProcessor;
import com.codingpupper3033.btekml.maphelpers.altitude.NoAltitudeException;

import java.io.IOException;

public class Coordinate {
    private final double lon;
    private final double lat;
    private final double elv;

    private Double groundLevel;
    
    private final AltitudeMode altitudeMode;

    public double getLongitude() {
        return lon;
    }

    public double getLatitude() {
        return lat;
    }

    public double getElevation() {
        return elv;
    }

    public AltitudeMode getAltitudeMode() {
        return altitudeMode;
    }


    public Coordinate(double lat, double lon, double elv, AltitudeMode altitudeMode) {
        this.lon = lon;
        this.lat = lat;
        this.elv = elv;
        this.altitudeMode = altitudeMode;
    }

    @Override
    public String toString() {
        return "[" + lat +
                ", " + lon +
                ", " + elv +
                ", " + altitudeMode.getNodeName() +
                ']';
    }

    public double getAltitude() throws NoAltitudeException, IOException {
        switch (altitudeMode) {
            case ABSOLUTE:
                return getElevation();
            case RELATIVE_TO_GROUND:
            case DEFAULT:
                if (groundLevel == null) {
                    groundLevel = AltitudeProcessor.defaultProcessor.getAltitude(this);
                }
                return groundLevel + getElevation();
            default:
                throw (new NoAltitudeException());
        }
    }
}
