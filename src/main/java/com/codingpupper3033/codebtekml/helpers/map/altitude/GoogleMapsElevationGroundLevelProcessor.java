package com.codingpupper3033.codebtekml.helpers.map.altitude;

import com.codingpupper3033.codebtekml.CodeBTEKMLMod;
import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Uses Google Elevation API to process altitudes.
 * @author Joshua Miller
 */
public class GoogleMapsElevationGroundLevelProcessor extends GroundLevelProcessor {
    public static final String URL = "https://maps.googleapis.com/maps/api/elevation/json?locations=%s&key=%s"; // Path and format of the api url
    public static final String LOCATIONS_CONCAT = "|"; // How to combine multiple locations

    public static final String RESULTS_KEY = "results"; // JSON Key to get the results
    public static final String ELEVATION_KEY = "elevation"; // JSON Key to get the elevation

    // Max amount of locations to ask for per request
    public static final int MAX_COORDINATES_PER_REQUEST = 512;

    private static String getAPIKey() throws IOException {
        ResourceLocation resourceLocation = new ResourceLocation(CodeBTEKMLMod.MODID, "api_keys/google_maps_api.key");
        InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.readLine();
    }


    /**
     * @param coordinate Coordinate to get the altitude of
     * @return elevation of the ground at the coordinate
     * @throws IOException
     */
    @Override
    public double getGroundLevel(Coordinate coordinate) throws IOException, NoAltitudeException {
        return getGroundLevels(new Coordinate[]{coordinate})[0];
    }

    /**
     * @param coordinates Coordinates to get the altitudes of
     * @return elevations of the ground at the coordinates
     * @throws IOException
     */
    @Override
    public double[] getGroundLevels(Coordinate[] coordinates) throws IOException, NoAltitudeException {
        super.getGroundLevels(coordinates);

        StringBuffer coordsBuffer = new StringBuffer(); // Buffer of the coordinates to request from the API

        for (int i = 0; i < coordinates.length; i++) { // Add all coordinates
            Coordinate coordinate = coordinates[i];

            coordsBuffer.append(coordinate.getLatitude());
            coordsBuffer.append(",");
            coordsBuffer.append(coordinate.getLongitude());

            if (i != coordinates.length-1) coordsBuffer.append(LOCATIONS_CONCAT); // No concat at the very end
        }

        URL url = new URL(String.format(URL, coordsBuffer, getAPIKey()));

        URLConnection connection = url.openConnection(); // Ask nicely

        // Get streams of response
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // Read the response
        StringBuffer jsonBuffer = new StringBuffer();

        while (reader.ready()) {
            jsonBuffer.append(reader.readLine());
            jsonBuffer.append('\n');
        }

        String jsonString = jsonBuffer.toString().trim();

        // Cleanup
        reader.close();
        is.close();

        // Parse it
        Gson gson = new Gson();

        JsonObject input = gson.fromJson(jsonString, JsonObject.class);
        JsonArray results = (JsonArray) input.get(RESULTS_KEY);

        System.out.println(results);

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
    @Override
    public boolean processCoordinateGroundLevelQueue() {
        super.processCoordinateGroundLevelQueue();
        return processCoordinateGroundLevelQueueByPackets(MAX_COORDINATES_PER_REQUEST);
    }
}
