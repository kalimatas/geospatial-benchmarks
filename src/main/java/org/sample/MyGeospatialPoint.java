package org.sample;

import com.eatthepath.jeospatial.GeospatialPoint;
import com.eatthepath.jeospatial.util.SimpleGeospatialPoint;

class MyGeospatialPoint extends SimpleGeospatialPoint {
    private int id;

    MyGeospatialPoint(double lat, double lon) {
        super(lat, lon);
    }

    //MyGeospatialPoint(GeospatialPoint geospatialPoint) {
    //    super(geospatialPoint);
    //}
    //
    //MyGeospatialPoint(double lat, double lon, int id) {
    //    super(lat, lon);
    //    this.id = id;
    //}

    int getId() {
        return id;
    }
}
