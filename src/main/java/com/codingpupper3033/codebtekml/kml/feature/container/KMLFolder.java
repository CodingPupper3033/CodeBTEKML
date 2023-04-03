package com.codingpupper3033.codebtekml.kml.feature.container;

import org.w3c.dom.Element;

/**
 * @author Joshua Miller
 */
public class KMLFolder extends KMLContainer {
    /**
     * Constructor from a DOM Folder Element
     * @param element DOM Element representing the feature
     */
    public KMLFolder(Element element) {
        super(element);
    }
}
