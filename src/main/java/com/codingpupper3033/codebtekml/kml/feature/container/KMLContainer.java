package com.codingpupper3033.codebtekml.kml.feature.container;

import com.codingpupper3033.codebtekml.kml.feature.KMLFeature;
import com.codingpupper3033.codebtekml.kml.feature.KMLPlacemark;
import javafx.util.Pair;
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
    private final ArrayList<KMLPlacemark> placemarks;

    /**
     * Constructor from a DOM Element
     * TODO Get Placemarks
     * @param element DOM Element representing the feature
     */
    public KMLContainer(Element element) {
        super(element); // Get name and visibility

        Pair<ArrayList<KMLContainer>, ArrayList<KMLPlacemark>> features = getFeatures(element);
        containers = features.getKey();
        placemarks = features.getValue();
    }

    /**
     * Gets the list of Containers in this container
     * @param element Container holding sub-containers
     * @return ArrayList of all found containers
     */
    public static ArrayList<KMLContainer> getContainers(Element element) {
        return getFeatures(element).getKey();
    }

    /**
     * Gets the list of Placemarks in this container
     * @param element Container holding placemarks
     * @return ArrayList of all found containers
     */
    public static ArrayList<KMLPlacemark> getPlacemarks(Element element) {
        return getFeatures(element).getValue();
    }


    /**
     * Gets the Containers and Placemarks separately, then return the lists
     * @param element Container elements
     * @return Pair of Containers and Placemarks
     */
    public static Pair<ArrayList<KMLContainer>, ArrayList<KMLPlacemark>> getFeatures(Element element) {
        ArrayList<KMLContainer> containers = new ArrayList<>();
        ArrayList<KMLPlacemark> placemarks = new ArrayList<>();

        // Go through all children
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);

            // Only Attribute Nodes
            if (currentNode.getNodeType() != Node.ELEMENT_NODE) continue;

            Element currentElement = (Element) currentNode;


            // There should only ever be one document, and the root node is a document. TLDR: Never need to make a docuement
            switch (currentElement.getTagName()) {
                // Containers
                case TAG_FOLDER: // Folders
                    containers.add(new KMLFolder(currentElement));
                    break;
                // Placemarks
                case TAG_PLACEMARK:
                    placemarks.add(new KMLPlacemark(currentElement));
                    break;
            }

        }

        return new Pair<>(containers, placemarks);
    }

    public ArrayList<KMLContainer> getContainers() {
        return containers;
    }

    public ArrayList<KMLPlacemark> getPlacemarks() {
        return placemarks;
    }
}
