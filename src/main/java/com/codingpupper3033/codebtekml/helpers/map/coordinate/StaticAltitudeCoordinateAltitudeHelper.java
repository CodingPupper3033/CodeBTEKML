package com.codingpupper3033.codebtekml.helpers.map.coordinate;

import com.codingpupper3033.codebtekml.kml.attributes.AltitudeMode;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

/**
 * @author Joshua Miller
 */
public class StaticAltitudeCoordinateAltitudeHelper extends CoordinateAltitudeHelper {
    public int staticAltitude;

    public StaticAltitudeCoordinateAltitudeHelper(int staticAltitude) {
        this.staticAltitude = staticAltitude;
    }

    @Override
    public void convertCoordinatesAltitudeMode(Coordinate[] coordinates, AltitudeMode convertTo) throws OperationNotSupportedException {
        if (Objects.requireNonNull(convertTo) == AltitudeMode.ABSOLUTE) {
            for (Coordinate coordinate : coordinates) {
                coordinate.setElevation(staticAltitude);
                coordinate.setAltitudeMode(AltitudeMode.ABSOLUTE);
            }
        } else {
            throw new OperationNotSupportedException();
        }
    }
}
