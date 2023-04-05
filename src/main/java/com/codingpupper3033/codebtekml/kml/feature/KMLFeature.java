package com.codingpupper3033.codebtekml.kml.feature;

import com.codingpupper3033.codebtekml.kml.KMLElement;
import org.w3c.dom.Element;

/**
 * Represents an abstract KML Element that has a name and visibility
 * @author Joshua Miller
 */
public class KMLFeature extends KMLElement {
    private String name;
    private boolean visible;

    /**
     * Constructor from a DOM Element
     * This only retrieves the name and visibility
     * @param element DOM Element representing the feature
     */
    protected KMLFeature(Element element) {
        Element nameElement = (Element) element.getElementsByTagName(TAG_NAME).item(0); // Should only ever have one
        name = (nameElement == null) ? "": nameElement.getTextContent();

        Element visibleElement = (Element) element.getElementsByTagName(TAG_VISIBILITY).item(0); // Should only ever have one
        visible = visibleElement == null || visibleElement.getNodeValue().equals("1"); // Only 1 is a valid option for true in booleans. If no tag, assume true
    }

    /**
     * Constructor from a name and visibility
     * @param name name of feature
     * @param visible visibility of feature
     */
    protected KMLFeature(String name, boolean visible) {
        this.name = name;
        this.visible = visible;
    }

    /**
     * Constructor from name. Assumes visibility
     * @param name Name of the feature
     */
    protected KMLFeature(String name) {
        this.name = name;
        this.visible = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
