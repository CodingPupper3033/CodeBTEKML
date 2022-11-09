package com.codingpupper3033.codebtekml.helpers.map.placemark;

import com.codingpupper3033.codebtekml.helpers.kmlfile.KMLParser;
import com.codingpupper3033.codebtekml.helpers.map.Placemark;
import com.codingpupper3033.codebtekml.helpers.map.altitude.AltitudeProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Creates Placemarks Based upon type
 * @author Joshua Miller
 */
public class PlacemarkFactory {
    /**
     * Gets Placemark based upon the given element
     * @param element Element to get sub-placemark of
     * @return Placemark
     */
    public static Placemark getPlacemark(Element element) {
        // Loop Through Child nodes
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue; // Has to be an element node

            Element currentChildElement = ((Element) childNodes.item(i));
            switch (currentChildElement.getTagName()) { // Figure out type of element
                case "LineString":
                    return new LineString(currentChildElement);
                case "Point":
                    return new Point(currentChildElement);
            }
        }

        return null; // Unable to determine the type of the placemark
    }

    /**
     * Gets all placemarks from the document
     * @param document Document to get placemarks from
     * @return List of Placemarks
     */
    public static Placemark[] getPlacemarks(Document document) {
        Element[] placemarkElements = KMLParser.getPlacemarkElements(document);

        Placemark[] placemarks = new Placemark[placemarkElements.length];

        for (int i = 0; i < placemarkElements.length; i++) { // Add all placemarks
            Element placemarkElement = placemarkElements[i];
            placemarks[i] = (PlacemarkFactory.getPlacemark(placemarkElement));
        }

        return placemarks;
    }

    /**
     * Gets all placemarks from the documents
     * @param documents Documents to get placemarks from
     * @return List of Placemarks in all documents
     */
    public static Placemark[] getPlacemarks(Document[] documents) {
        ArrayList<Placemark> out = new ArrayList<>();

        for (Document document : documents) { // Loop through all documents
            for (Placemark placemark : getPlacemarks(document)) { // add all placemarks (this way as getPlacemarks returns an array not list)
                out.add(placemark);
            }
        }

        Placemark[] placemarks = new Placemark[out.size()]; // yeet to array
        return out.toArray(placemarks);
    }

    /**
     * Parse all placemarks from a file
     * @param f file to parse
     * @return list of placemarks in the file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Placemark[] getPlacemarks(File f) throws ParserConfigurationException, IOException, SAXException {
        return getPlacemarks(KMLParser.parse(f));
    }

    /**
     * Draws all placemarks from the array
     * @param placemarks Placemarks to draw
     * @param blockName block name to draw with
     */
    public static void drawPlacemarks(Placemark[] placemarks, String blockName) {
        for (Placemark placemark : placemarks) {
            placemark.draw(blockName);
        }
    }

    /**
     * Draw all placemarks in the document with specified block name
     * @param documents documents to draw
     * @param blockName block name to draw with
     */
    public static void drawPlacemarks(Document[] documents, String blockName) {
         drawPlacemarks(getPlacemarks(documents), blockName);
    }

    /**
     * Draws all placemarks in a file
     * @param f file
     * @param blockName block name
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void drawPlacemarks(File f, String blockName) throws ParserConfigurationException, IOException, SAXException {
        drawPlacemarks(KMLParser.parse(f), blockName);
    }

    public static void proccessPlacemarks(Placemark[] placemarks) {
        for (Placemark placemark: placemarks) {
            AltitudeProcessor.defaultProcessor.addCoordinatesToProcessQueue(placemark.getCoordinates());
        }
        AltitudeProcessor.defaultProcessor.processCoordinateQueue();
    }
}
