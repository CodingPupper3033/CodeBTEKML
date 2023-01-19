package com.codingpupper3033.codebtekml.processors;

import com.codingpupper3033.codebtekml.helpers.map.altitude.NoAltitudeException;
import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;

import java.io.IOException;

/**
 * @author Joshua Miller
 */
public abstract class LevelProcessor {
    /**
     * Prepares a set of coordinates to be requested later on. Should do most of the pre=processing for coordinates.
     * @param coordinates Coordinates to pre-process
     * @return
     */
    public abstract boolean setup(Coordinate[] coordinates);

    /**
     * Finishes dealing with the coordinates as they may not be needed again.
     * @param coordinates Coordinates to finish up with
     * @return
     */

    public abstract boolean finish(Coordinate[] coordinates);

    public abstract double get(Coordinate coordinate) throws IOException, NoAltitudeException;
    public abstract double[] getAll(Coordinate[] coordinates) throws IOException, NoAltitudeException;

}
