package com.codingpupper3033.codebtekml.helpers.map.placemark;

import com.codingpupper3033.codebtekml.helpers.kml.KMLParser;
import com.codingpupper3033.codebtekml.helpers.map.altitude.GroundLevelProcessor;
import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;
import com.codingpupper3033.codebtekml.mapdrawer.MinecraftCommands;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
            Collections.addAll(out, getPlacemarks(document));
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
     * Draws all placemarks from the array, and returns when one is drawn
     * @param placemarks Placemarks to draw
     * @param blockName block name to draw with
     */
    public static void drawPlacemarks(Placemark[] placemarks, String blockName, DrawListener listener) {
        MinecraftCommands.currentSelMode = null;
        int i = 0;
        for (Placemark placemark : placemarks) {
            i++;
            int finalI = i;
            placemark.draw(blockName, (subsectionNumber, total) -> {
                if (listener != null) listener.subsectionDrawn(subsectionNumber, total, finalI, placemarks.length);
            });
        }
    }

    /**
     * Draws all placemarks from the array
     * @param placemarks Placemarks to draw
     * @param blockName block name to draw with
     */
    public static void drawPlacemarks(Placemark[] placemarks, String blockName) {
        drawPlacemarks(placemarks,blockName,null);
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

    public static Coordinate[] getCoordinatesFromPlacemarks(Placemark[] placemarks) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        for (Placemark placemark: placemarks) {
            Collections.addAll(coordinates, placemark.getCoordinates());
        }

        Coordinate[] out = new Coordinate[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            out[i] = coordinates.get(i);
        }

        return out;
    }

    public static void processPlacemarks(Placemark[] placemarks) {
        GroundLevelProcessor.defaultProcessor.addCoordinatesToProcessQueue(getCoordinatesFromPlacemarks(placemarks));
        GroundLevelProcessor.defaultProcessor.processCoordinateGroundLevelQueue();
    }
}
