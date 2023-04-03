package com.codingpupper3033.codebtekml.kml;

import com.codingpupper3033.codebtekml.helpers.kml.KMZFile;
import com.codingpupper3033.codebtekml.kml.feature.container.KMLDocument;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Joshua Miller
 */
public class KMLElementsTests {
    public Element getTestKMLContainer() throws IOException, ParserConfigurationException, SAXException {
        KMZFile file = new KMZFile("src/test/resources/Center.kmz");

        com.codingpupper3033.codebtekml.helpers.kml.KMLDocument document = new com.codingpupper3033.codebtekml.helpers.kml.KMLDocument(file); // Old implementation. Used till new one is implemented

        return (Element) document.getDocument().getDocumentElement().getElementsByTagName("Document").item(0);
    }
    @Test
    public void canReadKMLContainer() throws IOException, ParserConfigurationException, SAXException {
        Element document = getTestKMLContainer();

        com.codingpupper3033.codebtekml.kml.feature.container.KMLDocument testContainer = new com.codingpupper3033.codebtekml.kml.feature.container.KMLDocument(document);

        assertEquals("Able to get correct name", "Center.kmz", testContainer.getName());
        assertTrue("Able to get visibility", testContainer.isVisible());
    }

    @Test
    public void newDocumentFromKML() throws ParserConfigurationException, IOException, SAXException {
        KMLDocument document = KMLDocument.fromFile(new File("src/test/resources/doc.kml"));

        assertEquals("Able to get correct name", "Center.kmz", document.getName());
    }

    @Test
    public void newDocumentFromKMZ() throws ParserConfigurationException, IOException, SAXException {
        KMLDocument document = KMLDocument.fromFile(new File("src/test/resources/Center.kmz"));

        assertEquals("Able to get correct name", "Center.kmz", document.getName());
    }


}