package com.codingpupper3033.codebtekml.kml.feature.container;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author Joshua Miller
 */
public class KMLDocument extends KMLContainer {
    /**
     * List of recognized KMZ file extensions
     */
    public static final String[] KMZ_EXTENSIONS = {"kmz"};

    // Tag names
    public static final String TAG_DOCUMENT = "document";

    /**
     * Constructor from a DOM Element representing the document node should be 'kml'
     * TODO Get Placemarks
     *
     * @param element DOM Element representing the kml document
     */
    public KMLDocument(Element element) {
        super((Element) element.getElementsByTagName(TAG_DOCUMENT).item(0)); // Gets the document from the file
    }

    /**
     * Constructor from a DOM Document representing the file.
     * The top node should be kml not document
     * @param document
     */
    public KMLDocument(Document document) {
        super(document.getDocumentElement());
    }

    /**
     * Gets a new KMLDocument from a file in either KMZ or KML format
     * To determine if it is a KMZ or KML file, it will see if the file extension is in the KMZ_EXTENSIONS. If so, it will be treated as a KMZ file, otherwise it will be treated as KML
     * @param file File to parse
     */
    public static KMLDocument fromFile(File file) throws ParserConfigurationException, IOException, SAXException {
        for (int i = 0; i < KMZ_EXTENSIONS.length; i++) {
            if (file.getName().endsWith("." + KMZ_EXTENSIONS[i])) { // If it ends with a KMZ extension
                return fromKMZFile(file);
            }
        }

        // This will be treated as a KML file
        return fromKMLFile(file);
    }

    public static KMLDocument fromKMZFile(File file) {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Creates a KML Document from a KMZ file.
     * @param file .kml file to process
     * @return KMLDocument from that file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static KMLDocument fromKMLFile(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(file);
        return new KMLDocument(document);
    }

    public static KMLDocument fromKMLInputSource(InputSource source) {
        throw new RuntimeException("Not implemented yet");
    }
}
