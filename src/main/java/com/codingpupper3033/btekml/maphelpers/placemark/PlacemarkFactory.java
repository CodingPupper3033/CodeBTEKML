package com.codingpupper3033.btekml.maphelpers.placemark;

import com.codingpupper3033.btekml.kmlfileprocessor.KMLParser;
import com.codingpupper3033.btekml.maphelpers.Placemark;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlacemarkFactory {
    public static Placemark getPlacemark(Element element) {
        // Loop Through Child nodes
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
            Element currentChildElement = ((Element) childNodes.item(i));
            switch (currentChildElement.getTagName()) {
                case "LineString":
                    return new LineString(currentChildElement);
                case "Point":
                    return new Point(currentChildElement);
            }
        }

        return null;
    }

    public static Placemark[] getPlacemarks(Document document) {
        Element[] placemarkElements = KMLParser.getPlacemarkElements(document);

        Placemark[] placemarks = new Placemark[placemarkElements.length];

        for (int i = 0; i < placemarkElements.length; i++) {
            Element placemarkElement = placemarkElements[i];
            placemarks[i] = (PlacemarkFactory.getPlacemark(placemarkElement));
        }

        return placemarks;
    }

    public static Placemark[] getPlacemarks(Document[] documents) {
        ArrayList<Placemark> out = new ArrayList<>();

        for (Document document : documents) {
            for (Placemark placemark : getPlacemarks(document)) {
                out.add(placemark);
            }
        }

        Placemark[] placemarks = new Placemark[out.size()];
        return out.toArray(placemarks);
    }

    public static Placemark[] getPlacemarks(File f) throws ParserConfigurationException, IOException, SAXException {
        return getPlacemarks(KMLParser.parse(f));
    }

    public static void drawPlacemarks(Document[] documents, String blockName) {
        for (Placemark placemark : getPlacemarks(documents)) {
            placemark.draw(blockName);
        }
    }

    public static void drawPlacemarks(File f, String blockName) throws ParserConfigurationException, IOException, SAXException {
        drawPlacemarks(KMLParser.parse(f), blockName);
    }
}
