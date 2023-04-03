package com.codingpupper3033.codebtekml.kml.feature.container;

import com.codingpupper3033.codebtekml.kml.feature.KMLFeature;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Abstract representation of a KML feature storing other elements
 * @author Joshua Miller
 */
public class KMLContainer extends KMLFeature {
    private final ArrayList<KMLContainer> containers;

    /**
     * Constructor from a DOM Element
     * TODO Get Placemarks
     * @param element DOM Element representing the feature
     */
    public KMLContainer(Element element) {
        super(element); // Get name and visibility

        containers = getContainers(element);
    }

    /**
     * Gets the list of Containers in this container
     * @param element Container holding sub-containers
     * @return ArrayList of all found containers
     */
    public static ArrayList<KMLContainer> getContainers(Element element) {
        // Go through all children
        ArrayList<KMLContainer> containers = new ArrayList<>();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);

            // Only Attribute Nodes
            if (currentNode.getNodeType() != Node.ELEMENT_NODE) continue;

            Element currentElement = (Element) currentNode;

            KMLContainer foundContainer = null;
            // Create specified container
            // There should only ever be one document, and the root node is a document. TLDR: Never need to make a docuement
            // Folders
            if (currentElement.getTagName().equals(TAG_FOLDER)) {
                foundContainer = new KMLFolder(currentElement);
            }

            if (foundContainer != null) containers.add(foundContainer);
        }

        return containers;
    }

    public ArrayList<KMLContainer> getContainers() {
        return containers;
    }
}
