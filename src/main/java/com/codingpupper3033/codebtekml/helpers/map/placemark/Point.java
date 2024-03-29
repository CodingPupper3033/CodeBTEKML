package com.codingpupper3033.codebtekml.helpers.map.placemark;

import com.codingpupper3033.codebtekml.helpers.kml.KMLParser;
import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;
import com.codingpupper3033.codebtekml.mapdrawer.MinecraftCommands;
import org.w3c.dom.Element;

/**
 * Placemark that represents a point.
 * @author Joshua Miller
 */
public class Point extends Placemark {
    private final Coordinate coordinate;

    public Point(Element element) {
        coordinate = KMLParser.getElementCoordinates(element)[0]; // Only one coordinate as it is a point
    }

    @Override
    public Coordinate[] getCoordinates() {
        return new Coordinate[]{coordinate};
    }

    @Override
    public int getSubSections() {
        return 1;
    }

    @Override
    public void draw(String blockName, DrawPlacemarkSubsectionListener listener) {
        MinecraftCommands.set(coordinate, blockName); // Use World Edit set command as a point will just be a block
        if (listener != null) listener.subsectionDrawn(1, getSubSections());
    }

    @Override
    public void draw(String blockName) {
        draw(blockName, null);
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }
}
