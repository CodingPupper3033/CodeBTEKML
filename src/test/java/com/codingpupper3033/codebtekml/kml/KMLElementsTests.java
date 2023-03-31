package com.codingpupper3033.codebtekml.kml;

import com.codingpupper3033.codebtekml.helpers.kml.KMLDocument;
import com.codingpupper3033.codebtekml.helpers.kml.KMZFile;
import com.codingpupper3033.codebtekml.kml.feature.container.KMLContainer;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Joshua Miller
 */
public class KMLElementsTests {
    public Element getTestKMLContainer() throws IOException, ParserConfigurationException, SAXException {
        KMZFile file = new KMZFile("src/test/resources/Center.kmz");

        KMLDocument document = new KMLDocument(file); // Old implementation. Used till new one is implemented

        return (Element) document.getDocument().getDocumentElement().getElementsByTagName("Document").item(0);
    }
    @Test
    public void canReadKMLContainer() throws IOException, ParserConfigurationException, SAXException {
        Element document = getTestKMLContainer();

        KMLContainer testContainer = new KMLContainer(document);

        assertEquals("Able to get correct name", "Center.kmz", testContainer.getName());
        assertTrue("Able to get visibility", testContainer.isVisible());
    }
}