package org.kuchelmeister.engine.geometry;

import java.util.List;

import org.kuchelmeister.engine.geometry.primitive.Triangle;

public interface SubGeometry {
    public List<Triangle> getTriangles();
}
