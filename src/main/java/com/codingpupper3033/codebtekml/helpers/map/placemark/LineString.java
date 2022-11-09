package com.codingpupper3033.codebtekml.helpers.map.placemark;

import com.codingpupper3033.codebtekml.helpers.kmlfile.KMLParser;
import com.codingpupper3033.codebtekml.helpers.map.Coordinate;
import com.codingpupper3033.codebtekml.helpers.map.Placemark;
import com.codingpupper3033.codebtekml.mapdrawer.MinecraftCommands;
import org.w3c.dom.Element;

import java.util.Arrays;

/**
 * Placemark that represents a set of connected points. Also, could be considered a path.
 * @author Joshua Miller
 */
public class LineString extends Placemark {
    private final Coordinate[] coordinates;

    public LineString(Element element) {
        coordinates = KMLParser.getElementCoordinates(element);
    }

    @Override
    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    @Override
    public void draw(String blockName) {
        for (int i = 1; i < coordinates.length; i++) {
            MinecraftCommands.line(coordinates[i-1],coordinates[i], blockName); // Use the World Edit line command
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }
}
