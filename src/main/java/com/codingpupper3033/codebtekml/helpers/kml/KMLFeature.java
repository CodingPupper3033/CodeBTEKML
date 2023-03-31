package com.codingpupper3033.codebtekml.helpers.kml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Joshua Miller
 */
public class KMLFeature {
    private final Element element;

    private enum NODE_NAMES {
        PLACEMARK("Placemark");

        public final String name;

        NODE_NAMES(String name) {
            this.name = name;
        }
    }

    public KMLFeature(Element element) {
        this.element = element;
    }

    public KMLFeature[] getPlacemarks() {
        NodeList placemarkElementNodeList = element.getElementsByTagName(NODE_NAMES.PLACEMARK.name); // Get all Placemark Elements

        KMLFeature[] out = new KMLFeature[placemarkElementNodeList.getLength()]; // To array

        for (int i = 0; i < out.length; i++) {
            out[i] = new KMLFeature((Element) placemarkElementNodeList.item(i)); // Converts it to an element
        }

        return out;
    }
}
