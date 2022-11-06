package com.codingpupper3033.btekml.maphelpers.altitude;

public enum AltitudeMode {
    ABSOLUTE("absolute"),
    CLAMP_TO_GROUND("clampToGround"),
    CLAMP_TO_SEA_FLOOR("clampToSeaFloor"),
    RELATIVE_TO_GROUND("relativeToGround"),
    RELATIVE_TO_SEA_FLOOR("relativeToSeaFloor"),
    DEFAULT(CLAMP_TO_GROUND.nodeName);

    private final String nodeName;

    AltitudeMode(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }
}
