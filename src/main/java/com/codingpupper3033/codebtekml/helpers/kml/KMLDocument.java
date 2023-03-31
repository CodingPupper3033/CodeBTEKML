package com.codingpupper3033.codebtekml.helpers.kml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Joshua Miller
 */
public class KMLDocument {
    private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    Document document;

    /**
     * Constructor from a main DOM Document object
     * @param document Main Document node
     */
    public KMLDocument(Document document) {
        this.document = document;
    }

    /**
     * Constructor from a KML (formatted) file
     * Note: This constructor does not support KMZ files
     * @param file the KML formatted file
     * @throws ParserConfigurationException the parser configuration exception
     * @throws IOException                  the io exception
     * @throws SAXException                 the sax exception
     */
    public KMLDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        this(documentBuilderFactory.newDocumentBuilder().parse(file));
    }

    /**
     * Constructor from an input stream from a KML file
     * @param inputStream Input stream from a KML file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public KMLDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        this(documentBuilderFactory.newDocumentBuilder().parse(inputStream));
    }

    /**
     * Parses the main kml file from a kmz file
     * @param kmzFile File to retrieve the main file from
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public KMLDocument(KMZFile kmzFile) throws IOException, ParserConfigurationException, SAXException {
        this(kmzFile.getMainKMLZInputStream());
    }

    /**
     * Gets the DOM Document object
     * @return
     */
    public Document getDocument() {
        return document;
    }
}
