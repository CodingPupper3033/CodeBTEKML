package com.codingpupper3033.btekml.maphelpers.placemark;

import com.codingpupper3033.btekml.kmlfileprocessor.KMLParser;
import com.codingpupper3033.btekml.mapdrawer.WorldEdit;
import com.codingpupper3033.btekml.maphelpers.Coordinate;
import com.codingpupper3033.btekml.maphelpers.Placemark;
import org.w3c.dom.Element;

public class Point extends Placemark {
    private final Coordinate coordinate;

    public Point(Element element) {
        super(element);

        coordinate = KMLParser.getElementCoordinates(element)[0];
    }

    @Override
    public void draw(String blockName) {
        WorldEdit.set(coordinate, blockName);
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }
}
