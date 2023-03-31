package com.codingpupper3033.codebtekml.helpers.kml;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.ZipEntry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Joshua Miller
 */
public class KMZFileTest {
    @Test
    public void getMainFile() throws IOException {
        KMZFile file = new KMZFile("src/test/resources/Center.kmz");

        ZipEntry mainFile = file.getMainKMLZipEntry();
        assertEquals("doc.kml", mainFile.getName());
    }

    @Test
    public void getMainKMLDocument() throws IOException, ParserConfigurationException, SAXException {
        KMZFile file = new KMZFile("src/test/resources/Center.kmz");

        KMLDocument document = new KMLDocument(file);

        assertNotNull("Loaded KML Document Successfully", document);

    }
}