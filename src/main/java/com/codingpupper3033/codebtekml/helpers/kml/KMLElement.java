package com.codingpupper3033.codebtekml.helpers.kml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Joshua Miller
 */
public class KMLElement {
    private final Element element;

    private enum NODE_NAMES {
        PLACEMARK("Placemark");

        public final String name;

        NODE_NAMES(String name) {
            this.name = name;
        }
    }

    public KMLElement(Element element) {
        this.element = element;
    }

    public KMLElement[] getPlacemarks() {
        NodeList placemarkElementNodeList = element.getElementsByTagName(NODE_NAMES.PLACEMARK.name); // Get all Placemark Elements

        KMLElement[] out = new KMLElement[placemarkElementNodeList.getLength()]; // To array

        for (int i = 0; i < out.length; i++) {
            out[i] = new KMLElement((Element) placemarkElementNodeList.item(i)); // Converts it to an element
        }

        return out;
    }
}
