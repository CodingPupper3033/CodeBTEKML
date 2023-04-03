package com.codingpupper3033.codebtekml.kml.feature.container;

import com.codingpupper3033.codebtekml.kml.MalformedKMZException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Joshua Miller
 */
public class KMLDocument extends KMLContainer {
    /**
     * List of recognized KML file extensions
     */
    public static final String[] KML_EXTENSIONS = {"kml"};

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
     * To determine if it is a KMZ or KML file, it will see if the file extension is in the KML_EXTENSIONS. If so, it will be treated as a KML file; otherwise, it will be treated as a KMZ file
     * @param file File to parse
     */
    public static KMLDocument fromFile(File file) throws ParserConfigurationException, IOException, SAXException {
        for (int i = 0; i < KML_EXTENSIONS.length; i++) {
            if (file.getName().endsWith("." + KML_EXTENSIONS[i])) { // If it ends with a KMZ extension
                return fromKMLFile(file);
            }
        }

        // This will be treated as a KMZ file
        return fromKMZFile(file);
    }

    public static KMLDocument fromKMZFile(File file) throws IOException, ParserConfigurationException, SAXException {
        // Open Zip File
        ZipFile zipKMZFile = new ZipFile(file);

        // Loop though files to find main file
        Enumeration<? extends ZipEntry> entries = zipKMZFile.entries(); // Get top level entries in Zip File
        ZipEntry foundEntry = null;
        while (entries.hasMoreElements()) {
            ZipEntry currentEntry = entries.nextElement();
            if (currentEntry.isDirectory()) continue; // Will not recursively look

            for (int i = 0; i < KML_EXTENSIONS.length; i++) { // Does it end with one of the KML file extensions?
                // Not this extension
                if (!currentEntry.getName().endsWith("." + KML_EXTENSIONS[i])) continue;

                // Already found one
                if (foundEntry != null) throw new MalformedKMZException("Multiple main KML files found. Unable to choose."); // Can't have multiple main


                foundEntry = currentEntry;
            }
        }

        // Didn't find a main file
        if (foundEntry == null) throw new MalformedKMZException("No main KML file");

        // Convert to input stream
        InputStream stream = zipKMZFile.getInputStream(foundEntry);

        return KMLDocument.fromKMLInputStream(stream);
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

    public static KMLDocument fromKMLInputStream(InputStream stream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(stream);
        return new KMLDocument(document);
    }
}
