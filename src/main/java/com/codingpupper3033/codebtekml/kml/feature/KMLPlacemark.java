package com.codingpupper3033.codebtekml.kml.feature;

import com.codingpupper3033.codebtekml.kml.geometry.KMLGeometry;
import com.codingpupper3033.codebtekml.kml.geometry.LineString;
import com.codingpupper3033.codebtekml.kml.geometry.Point;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua Miller
 */
public class KMLPlacemark extends KMLFeature {
    public final Element element; // Temp lets me get data from it before I store it nicely
    public final KMLGeometry geometry;

    /**
     * Constructor from a DOM Element
     * TODO Get Geometry and stuffs
     *
     * @param element DOM Element representing the feature
     */
    public KMLPlacemark(Element element) {
        super(element);

        this.element = element;
        this.geometry = getGeometryFromElement(element);
    }

    public static KMLGeometry getGeometryFromElement(Element element) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            if (child.getNodeType() != Node.ELEMENT_NODE) { // Not correct node type
                continue;
            }

            Element elementChild = (Element) child;

            switch (elementChild.getNodeName()) {
                case TAG_POINT:
                    return new Point(elementChild);
                case TAG_LINE_STRING:
                    return new LineString(elementChild);
            }
        }

        return null;
    }
}
