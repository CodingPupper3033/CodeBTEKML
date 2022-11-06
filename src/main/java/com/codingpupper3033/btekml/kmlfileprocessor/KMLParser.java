package com.codingpupper3033.btekml.kmlfileprocessor;

import com.codingpupper3033.btekml.maphelpers.altitude.AltitudeMode;
import com.codingpupper3033.btekml.maphelpers.Coordinate;
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
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/***
 * Responsible for extracting data from a KML or KMZ file
 * @author Joshua Miller
 */
public class KMLParser {
    // KML File Typrd
    public static final String[] KML_FILE_NAMES = {"kml"};

    // Holds Coordinates
    public static final String COORDINATES_NODE_NAME = "coordinates";
    // Whether to tessellate
    public static final String ALTITUDE_MODE_NODE_NAME = "altitudeMode";

    // Placemark
    public static final String PLACEMARK_NODE_NAME = "Placemark";

    // Placemarks
    public static final String LINESTRING_NODE_NAME = "LineString";
    public static final String POINT_NODE_NAME = "Point";

    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    static public Document[] parse(File f) throws ParserConfigurationException, IOException, SAXException {
        for (int i = 0; i < KML_FILE_NAMES.length; i++) {
            if (f.getName().endsWith("." + KML_FILE_NAMES[i])) {
                Document[] out = new Document[1];
                out[1] = parseKML(f);
                return out;
            }
        }
        return parseKMZ(f);
    }

    static public Document parseKML(File f) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(f);
    }

    static public Document parseKML(InputSource is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(is);
    }

    static public Document parseKML(InputStream byteStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(byteStream);
    }

    static public Document[] parseKMZ(File f) throws ParserConfigurationException, IOException, SAXException {

        ZipFile zipFile = new ZipFile(f);

        ArrayList<Document> out = new ArrayList<>();

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) continue; // No directories

            boolean isKML = false;

            for (int i = 0; i < KML_FILE_NAMES.length; i++) {
                if (entry.getName().endsWith("." + KML_FILE_NAMES[i])) {
                    isKML = true;
                    break;
                }
            }
            if (!isKML) continue;

            InputStream inputStream = zipFile.getInputStream(entry);
            out.add(parseKML(inputStream));
        }

        Document[] outArray = new Document[out.size()];
        return out.toArray(outArray);
    }

    static public Coordinate[] getElementCoordinates(Element e) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        NodeList childNodes = e.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode.getNodeName().equals(COORDINATES_NODE_NAME)) { // Is the coordinate one
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

        Coordinate[] coordinatesArray = new Coordinate[coordinates.size()];

        return coordinates.toArray(coordinatesArray);
    }

    static public AltitudeMode getElementAltitudeMode(Element e) {
        NodeList childNodes = e.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode.getNodeName().equals(ALTITUDE_MODE_NODE_NAME)) { // Is the altitude mode one
                for (AltitudeMode mode : AltitudeMode.values()) {
                    if (childNode.getFirstChild().getNodeValue().equals(mode.getNodeName())) {
                        return mode;
                    }
                }
                return AltitudeMode.DEFAULT;
            }
        }
        return null;
    }

    static public Element[] getPlacemarkElements(Document document) {
        NodeList placemarkElementNodeList = document.getElementsByTagName(PLACEMARK_NODE_NAME);

        Element[] out = new Element[placemarkElementNodeList.getLength()];

        for (int i = 0; i < out.length; i++) {
            out[i] = (Element) placemarkElementNodeList.item(i);
        }

        return out;
    }
}
