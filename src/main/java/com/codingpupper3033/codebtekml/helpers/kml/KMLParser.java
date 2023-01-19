package com.codingpupper3033.codebtekml.helpers.kml;

import com.codingpupper3033.codebtekml.helpers.map.coordinate.Coordinate;
import com.codingpupper3033.codebtekml.helpers.map.altitude.AltitudeMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/***
 * Responsible for extracting data from a KML or KMZ file
 * @author Joshua Miller
 */
public class KMLParser {
    // KML File Type
    public static final String[] KML_FILE_NAMES = {"kml"}; // only KML, not also kmz

    // Holds Coordinates
    public static final String COORDINATES_NODE_NAME = "coordinates";
    // Whether to tessellate
    public static final String ALTITUDE_MODE_NODE_NAME = "altitudeMode";

    // Placemark
    public static final String PLACEMARK_NODE_NAME = "Placemark";

    // Placemarks
    public static final String LINESTRING_NODE_NAME = "LineString";
    public static final String POINT_NODE_NAME = "Point";

    private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();


    /**
     * Parse kml or kmz file into DOM Documents.
     *
     * @param f the file to parse
     * @return the documents found in the file
     * @throws ParserConfigurationException parser configuration exception
     * @throws IOException                  io exception
     * @throws SAXException                 sax exception
     */
    static public Document[] parse(File f) throws ParserConfigurationException, IOException, SAXException {
        for (int i = 0; i < KML_FILE_NAMES.length; i++) {
            if (f.getName().endsWith("." + KML_FILE_NAMES[i])) {
                Document[] out = new Document[1];
                out[0] = parseKML(f);
                return out;
            }
        }
        return parseKMZ(f);
    }

    /**
     * Parse only kml document.
     *
     * @param f the kml file to parse
     * @return the document in the kml file
     * @throws ParserConfigurationException the parser configuration exception
     * @throws IOException                  the io exception
     * @throws SAXException                 the sax exception
     */
    static public Document parseKML(File f) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(f);
    }

    /**
     * Parse only kml document.
     *
     * @param is the input source
     * @return the document in the input source
     * @throws ParserConfigurationException the parser configuration exception
     * @throws IOException                  the io exception
     * @throws SAXException                 the sax exception
     */
    static public Document parseKML(InputSource is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(is);
    }

    /**
     * Parse only kml document.
     *
     * @param byteStream the input stream
     * @return the document in the byte stream
     * @throws ParserConfigurationException the parser configuration exception
     * @throws IOException                  the io exception
     * @throws SAXException                 the sax exception
     */
    static public Document parseKML(InputStream byteStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(byteStream);
    }

    /**
     * Parse only kmZ document.
     *
     * @param f the KMZ file to parse
     * @return the document from the file
     * @throws ParserConfigurationException the parser configuration exception
     * @throws IOException                  the io exception
     * @throws SAXException                 the sax exception
     */
    static public Document[] parseKMZ(File f) throws ParserConfigurationException, IOException, SAXException {
        KMZFile kmzFile = new KMZFile(f);
        return new Document[] {parseKML(kmzFile.getMainKMLZInputStream())};
    }

    /**
     * Gets the list of coordinates from a kml element containing a coordinate element.
     * @param e Element to search
     * @return Coordinates from the element
     */
    static public Coordinate[] getElementCoordinates(Element e) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        NodeList childNodes = e.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode.getNodeName().equals(COORDINATES_NODE_NAME)) { // Is this the coordinate one
                // Get string
                String coordinatesString = childNode.getFirstChild().getNodeValue();

                // Get Altitude mode
                AltitudeMode altitudeMode = getElementAltitudeMode(e);

                // Add coordinates
                for (String pointString : coordinatesString.trim().split("\n| ")) {
                    if (pointString.trim().isEmpty()) continue;
                    String[] positionStrings = pointString.split(",");
                    coordinates.add(new Coordinate(Double.parseDouble(positionStrings[1]), Double.parseDouble(positionStrings[0]), Double.parseDouble(positionStrings[2]), altitudeMode));
                }
            }
        }

        Coordinate[] coordinatesArray = new Coordinate[coordinates.size()]; // Convert arraylist to array as we now know the size

        return coordinates.toArray(coordinatesArray);
    }

    /**
     * Gets the altitude mode of an element
     *
     * @param e the element to check
     * @return the Altitude mode of the element
     */
    static public AltitudeMode getElementAltitudeMode(Element e) {
        NodeList childNodes = e.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode.getNodeName().equals(ALTITUDE_MODE_NODE_NAME)) { // Is the altitude mode one
                for (AltitudeMode mode : AltitudeMode.values()) { // Get the one with the same name
                    if (childNode.getFirstChild().getNodeValue().equals(mode.getNodeName())) {
                        return mode;
                    }
                }
                return AltitudeMode.DEFAULT; // couldn't find one, use default
            }
        }
        return null;
    }

    /**
     * Gets all placemark elements from a document.
     *
     * @param document the document
     * @return the placemark elements
     */
    static public Element[] getPlacemarkElements(Document document) {
        NodeList placemarkElementNodeList = document.getElementsByTagName(PLACEMARK_NODE_NAME); // Get all Placemark Elements


        Element[] out = new Element[placemarkElementNodeList.getLength()]; // To array

        for (int i = 0; i < out.length; i++) {
            out[i] = (Element) placemarkElementNodeList.item(i); // Converts it to an element
        }

        return out;
    }
}
