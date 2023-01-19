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

    public KMLDocument(Document document) {
        this.document = document;
    }

    public KMLDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        this(documentBuilderFactory.newDocumentBuilder().parse(file));
    }

    public KMLDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        this(documentBuilderFactory.newDocumentBuilder().parse(inputStream));
    }

    public KMLDocument(KMZFile kmzFile) throws IOException, ParserConfigurationException, SAXException {
        this(kmzFile.getMainKMLZInputStream());
    }
}
