package com.codingpupper3033.btekml.maphelpers;

import org.w3c.dom.Element;

public abstract class Placemark {
    public Placemark(Element element) {

    }

    public abstract void draw(String blockName);
}
