package com.codingpupper3033.btekml.maphelpers.altitude;

import com.codingpupper3033.btekml.maphelpers.Coordinate;

import java.io.IOException;

public class AltitudeProcessor {
    public double getAltitude(Coordinate coordinate) throws IOException, NoAltitudeException {
        if (!enabled) throw new NoAltitudeException();
        return 0;
    };

    public double[] getAltitudes(Coordinate[] coordinates) throws IOException, NoAltitudeException {
        if (!enabled) throw new NoAltitudeException();
        return new double[]{};
    };

    public static AltitudeProcessor defaultProcessor = new OpenElevationAltitudeProcessor();
    public boolean enabled = true;
}
