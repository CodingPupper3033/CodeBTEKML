package com.codingpupper3033.btekml.maphelpers.altitude;

import com.codingpupper3033.btekml.maphelpers.Coordinate;
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

public class OpenElevationAltitudeProcessor extends AltitudeProcessor {
    public static final String URL = "https://api.open-elevation.com/api/v1/lookup?locations=%s";
    public static final String LOCATIONS_CONCAT = "|";

    public static final String RESULTS_KEY = "results";
    public static final String ELEVATION_KEY = "elevation";

    @Override
    public double getAltitude(Coordinate coordinate) throws IOException {
        return getAltitudes(new Coordinate[]{coordinate})[0];
    }

    @Override
    public double[] getAltitudes(Coordinate[] coordinates) throws IOException {
        StringBuffer coordsBuffer = new StringBuffer();

        for (int i = 0; i < coordinates.length; i++) {
            Coordinate coordinate = coordinates[i];

            coordsBuffer.append(coordinate.getLatitude());
            coordsBuffer.append(",");
            coordsBuffer.append(coordinate.getLongitude());

            if (i != coordinates.length-1) coordsBuffer.append(LOCATIONS_CONCAT);
        }

        URLConnection connection = new URL(String.format(URL, coordsBuffer.toString())).openConnection();
        InputStream is = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String jsonString = reader.readLine();
        reader.close();
        is.close();

        Gson gson = new Gson();

        JsonObject input = gson.fromJson(jsonString, JsonObject.class);
        JsonArray results = (JsonArray) input.get(RESULTS_KEY);


        double[] out = new double[results.size()];

        int i = 0;
        for (JsonElement object : results) {
            out[i] = object.getAsJsonObject().get(ELEVATION_KEY).getAsDouble();
            i++;
        }


        return out;
    }
}
