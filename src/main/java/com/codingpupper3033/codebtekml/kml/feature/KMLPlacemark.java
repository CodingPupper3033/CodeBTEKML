package com.codingpupper3033.codebtekml.kml.feature;

import org.w3c.dom.Element;

/**
 *
 * @author Joshua Miller
 */
public class KMLPlacemark extends KMLFeature {
    /**
     * Constructor from a DOM Element
     * TODO Get Geometry and stuffs
     *
     * @param element DOM Element representing the feature
     */
    public KMLPlacemark(Element element) {
        super(element);
    }
}
