package com.codingpupper3033.codebtekml.kml.feature.container;

import com.codingpupper3033.codebtekml.kml.feature.KMLFeature;
import org.w3c.dom.Element;

/**
 * Abstract representation of a KML feature storing other elements
 * @author Joshua Miller
 */
public class KMLContainer extends KMLFeature {
    /**
     * Constructor from a DOM Element
     * TODO Get Placemarks
     * @param element DOM Element representing the feature
     */
    public KMLContainer(Element element) {
        super(element); // Get name and visibility
    }
}
