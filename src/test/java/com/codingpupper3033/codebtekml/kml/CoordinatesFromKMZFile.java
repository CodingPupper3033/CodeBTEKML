package com.codingpupper3033.codebtekml.kml;

import com.codingpupper3033.codebtekml.kml.feature.KMLPlacemark;
import com.codingpupper3033.codebtekml.kml.feature.container.KMLDocument;
import com.codingpupper3033.codebtekml.kml.feature.container.KMLFolder;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author Joshua Miller
 */
public class CoordinatesFromKMZFile {
    @Test
    public void getCoordinateFromFile() throws ParserConfigurationException, IOException, SAXException {
        KMLDocument document = KMLDocument.fromFile(new File("src/test/resources/Center.kmz"));

        KMLFolder folder = (KMLFolder) document.getContainers().get(0);

        KMLPlacemark placemark = folder.getPlacemarks().get(0);

        System.out.println(placemark.geometry);


        assert true;
    }
}
