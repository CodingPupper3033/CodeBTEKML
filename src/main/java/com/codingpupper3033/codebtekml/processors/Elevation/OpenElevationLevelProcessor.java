package com.codingpupper3033.codebtekml.processors.Elevation;

import com.codingpupper3033.codebtekml.helpers.map.altitude.NoAltitudeException;
import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Uses the Open Elevation API to process altitudes.
 * @author Joshua Miller
 */
public class OpenElevationLevelProcessor extends ElevationLevelProcessor {
    public static final String URL = "https://api.open-elevation.com/api/v1/lookup?locations=%s"; // Path and format of the api url
    public static final String LOCATIONS_CONCAT = "|"; // How to combine multiple locations

    public static final String RESULTS_KEY = "results"; // JSON Key to get the results
    public static final String ELEVATION_KEY = "elevation"; // JSON Key to get the elevation

    // Max amount of locations to ask for per request
    public static final int MAX_COORDINATES_PER_REQUEST = 16;

    @Override
    public boolean setup(Coordinate[] coordinates) {
        return false;
    }

    /**
     * Finishes dealing with the coordinates as they may not be needed again.
     *
     * @param coordinates Coordinates to finish up with
     * @return
     */
    @Override
    public boolean finish(Coordinate[] coordinates) {
        return false;
    }

    /**
     * @param coordinate Coordinate to get the altitude of
     * @return elevation of the ground at the coordinate
     */
    @Override
    public double get(Coordinate coordinate) throws NoAltitudeException, IOException {
        return getAll(new Coordinate[]{coordinate})[0];
    }

    /**
     * @param coordinates Coordinates to get the altitudes of
     * @return elevations of the ground at the coordinates
     * @throws IOException
     */
    @Override
    public double[] getAll(Coordinate[] coordinates) throws IOException, NoAltitudeException {
        StringBuffer coordsBuffer = new StringBuffer(); // Buffer of the coordinates to request from the API

        for (int i = 0; i < coordinates.length; i++) { // Add all coordinates
            Coordinate coordinate = coordinates[i];

            coordsBuffer.append(coordinate.getLatitude());
            coordsBuffer.append(",");
            coordsBuffer.append(coordinate.getLongitude());

            if (i != coordinates.length-1) coordsBuffer.append(LOCATIONS_CONCAT); // No concat at the very end
        }

        URLConnection connection = new URL(String.format(URL, coordsBuffer)).openConnection(); // Ask nicely

        // Get streams of response
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // Read the response
        String jsonString = reader.readLine();

        // Cleanup
        reader.close();
        is.close();

        // Parse it
        Gson gson = new Gson();

        JsonObject input = gson.fromJson(jsonString, JsonObject.class);
        JsonArray results = (JsonArray) input.get(RESULTS_KEY);


        double[] out = new double[results.size()];

        int i = 0;
        for (JsonElement object : results) { // Add each location's element
            out[i] = object.getAsJsonObject().get(ELEVATION_KEY).getAsDouble();
            i++;
        }

        return out;
    }


    /**
     * Processes the ground level for all Coordinates in the queue
     * @return whether it was able to process the queue
     */
//        @Override
//        public boolean processCoordinateGroundLevelQueue() {
//            return processCoordinateGroundLevelQueueByPackets(MAX_COORDINATES_PER_REQUEST);
//        }
}
