package com.codingpupper3033.codebtekml.helpers.map.altitude;

import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;
import com.codingpupper3033.codebtekml.kml.attributes.AltitudeMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class that will process the altitude of a coordinate, based upon an AltitudeMode.
 * This is a blank Altitude, you should ideally use a subclass.
 * @see AltitudeMode
 * @author Joshua Miller
 */
public class GroundLevelProcessor {
    /**
     * Default Ground Level Processor
     * Currently it uses Open Elevation, as there appears to be no way to get ground level in java.
     * The downside is that it requires internet access to make API requests.
     */
    public static GroundLevelProcessor defaultProcessor = new GoogleMapsElevationGroundLevelProcessor();

    /**
     * Can we use this?
     */
    public boolean enabled = true;

    /**
     * List of coordinates to process
     */
    private final Queue<Coordinate> coordinateGroundLevelProcessorQueue = new LinkedList<>();
    /**
     * List of coordinates processed ground level already
     */
    private final Queue<Coordinate> coordinateGroundLevelFinishedQueue = new LinkedList<>();

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
        coordinateGroundLevelProcessorQueue.add(coordinate);
    }

    public void addCoordinatesToProcessQueue(Coordinate[] coordinates) {
        coordinateGroundLevelProcessorQueue.addAll(Arrays.asList(coordinates));
    }

    public void addCoordinateToFinishQueue(Coordinate coordinates) {
        coordinateGroundLevelFinishedQueue.add(coordinates);
    }

    public void addCoordinatesToFinishQueue(Coordinate[] coordinates) {
        coordinateGroundLevelFinishedQueue.addAll(Arrays.asList(coordinates));
    }

    public Queue<Coordinate> getCoordinateGroundLevelProcessorQueue() {
        return coordinateGroundLevelProcessorQueue;
    }

    /**
     * Empties the process coordinate queue
     *
     * @return Whether it succeeded
     */
    public boolean processCoordinateGroundLevelQueue() {
        return false;
    }

    /**
     * Processes the Coordinate Queue by splitting requests into "packets"
     * @param max_coordinates_per_request how many coordinates to request at a time
     * @return
     */
    public boolean processCoordinateGroundLevelQueueByPackets(int max_coordinates_per_request) {
        while (!getCoordinateGroundLevelProcessorQueue().isEmpty()) { // Keep working until all out
            Coordinate[] setOfLocationsToProcess = new Coordinate[Math.min(max_coordinates_per_request, getCoordinateGroundLevelProcessorQueue().size())]; // Make it only as big as needed
            int i = 0;
            while (!getCoordinateGroundLevelProcessorQueue().isEmpty() && i < setOfLocationsToProcess.length) { // Only do the max size at a time
                setOfLocationsToProcess[i] = getCoordinateGroundLevelProcessorQueue().poll();
                i++;
            }

            // Process this set
            try {
                double[] groundLevels = getGroundLevels(setOfLocationsToProcess);

                for (int j = 0; j < setOfLocationsToProcess.length; j++) { // Add the result to the coordinate
                    setOfLocationsToProcess[j].setGroundLevel(groundLevels[j]);
                }

                addCoordinatesToFinishQueue(setOfLocationsToProcess);

                return true;
            } catch (IOException e) {
                return false;
            } catch (NoAltitudeException e) {
                return false;
            }
        }

        return false;
    }
}
