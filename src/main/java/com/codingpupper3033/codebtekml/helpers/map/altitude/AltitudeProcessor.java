package com.codingpupper3033.codebtekml.helpers.map.altitude;

import com.codingpupper3033.codebtekml.helpers.map.Coordinate;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class that will process the altitude of a coordinate, based upon an AltitudeMode.
 * This is a blank Altitude, you should ideally use a subclass.
 * @see AltitudeMode
 * @author Joshua Miller
 */
public class AltitudeProcessor {
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

    /**
     * List of coordinates to process
     */
    public Queue<Coordinate> coordinateProcessorQueue = new LinkedList<>();

    /**
     * Default altitude method. Should not be used.
     * @param coordinate Coordinate containing the given altitude
     * @return
     * @throws IOException
     * @throws NoAltitudeException
     */
    public double getGroundLevel(Coordinate coordinate) throws IOException, NoAltitudeException {
        if (!enabled) throw new NoAltitudeException();
        return 0;
    }

    /**
     * Default altitude method. Should not be used.
     * @param coordinates Coordinates containing the given altitude
     * @return
     * @throws IOException
     * @throws NoAltitudeException
     */
    public double[] getGroundLevels(Coordinate[] coordinates) throws IOException, NoAltitudeException {
        if (!enabled) throw new NoAltitudeException();
        return new double[]{};
    }

    public void addCoordinateToProcessQueue(Coordinate coordinate) {
        coordinateProcessorQueue.add(coordinate);
    }

    public Queue<Coordinate> getCoordinateProcessorQueue() {
        return coordinateProcessorQueue;
    }

    /**
     * Empties the process coordinate queue
     *
     * @return
     */
    public boolean processCoordinateQueue() {

        return false;
    }
}
