package com.codingpupper3033.codebtekml.helpers.map.placemark;

import com.codingpupper3033.codebtekml.helpers.kmlfile.KMLParser;
import com.codingpupper3033.codebtekml.helpers.map.Coordinate;
import com.codingpupper3033.codebtekml.helpers.map.Placemark;
import com.codingpupper3033.codebtekml.mapdrawer.MinecraftCommands;
import org.w3c.dom.Element;

/**
 * Placemark that represents a point.
 * @author Joshua Miller
 */
public class Point extends Placemark {
    private final Coordinate coordinate;

    public Point(Element element) {
        super(element);

        coordinate = KMLParser.getElementCoordinates(element)[0]; // Only one coordinate as it is a point
    }

    @Override
    public void draw(String blockName) {
        MinecraftCommands.set(coordinate, blockName); // Use World Edit set command as a point will just be a block
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }
}
