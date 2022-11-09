package com.codingpupper3033.codebtekml.helpers.map.altitude;

import com.codingpupper3033.codebtekml.helpers.map.Coordinate;

import java.io.IOException;

/**
 * Class that will process the altitude of a coordinate, based upon an AltitudeMode.
 * This is a blank Altitude, you should ideally use a subclass.
 * @see AltitudeMode
 * @author Joshua Miller
 */
public class AltitudeProcessor {
    /**
     * Default altitude method. Should not be used.
     * @param coordinate Coordinate containing the given altitude
     * @return
     * @throws IOException
     * @throws NoAltitudeException
     */
    public double getAltitude(Coordinate coordinate) throws IOException, NoAltitudeException {
        if (!enabled) throw new NoAltitudeException();
        return 0;
    };

    /**
     * Default altitude method. Should not be used.
     * @param coordinates Coordinates containing the given altitude
     * @return
     * @throws IOException
     * @throws NoAltitudeException
     */
    public double[] getAltitudes(Coordinate[] coordinates) throws IOException, NoAltitudeException {
        if (!enabled) throw new NoAltitudeException();
        return new double[]{};
    };

    /**
     * Default Altitude Processor
     * Currently it uses Open Elevation, as there appears to be no way to get ground level in java.
     * The downside is that it requires internet access to make API requests.
     */
    public static AltitudeProcessor defaultProcessor = new OpenElevationAltitudeProcessor();

    /**
     * Can we use this?
     */
    public boolean enabled = true;
}
