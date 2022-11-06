package com.codingpupper3033.btekml.maphelpers.placemark;

import com.codingpupper3033.btekml.kmlfileprocessor.KMLParser;
import com.codingpupper3033.btekml.mapdrawer.WorldEdit;
import com.codingpupper3033.btekml.maphelpers.Coordinate;
import com.codingpupper3033.btekml.maphelpers.Placemark;
import org.w3c.dom.Element;

import java.util.Arrays;

public class LineString extends Placemark {
    private final Coordinate[] coordinates;

    public LineString(Element element) {
        super(element);

        coordinates = KMLParser.getElementCoordinates(element);
    }

    @Override
    public void draw(String blockName) {
        for (int i = 1; i < coordinates.length; i++) {
            WorldEdit.line(coordinates[i-1],coordinates[i], blockName);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }
}
