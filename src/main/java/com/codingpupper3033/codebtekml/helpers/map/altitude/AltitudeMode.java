package com.codingpupper3033.codebtekml.helpers.map.altitude;

/**
 * Modes to read the altitude from a kml file
 * @see <a href="https://developers.google.com/kml/documentation/altitudemode">KML Altitude Mode Documentation</a>
 * @author Joshua Miller
 */
public enum AltitudeMode {
    ABSOLUTE("absolute"),
    CLAMP_TO_GROUND("clampToGround"),
    CLAMP_TO_SEA_FLOOR("clampToSeaFloor"),
    RELATIVE_TO_GROUND("relativeToGround"),
    RELATIVE_TO_SEA_FLOOR("relativeToSeaFloor"),
    DEFAULT(CLAMP_TO_GROUND.nodeName);

    private final String nodeName;

    /**
     * @param nodeName How it appears in KML
     */
    AltitudeMode(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }
}
