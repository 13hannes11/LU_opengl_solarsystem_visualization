package org.kuchelmeister.engine.geometry.sphere;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.kuchelmeister.engine.camera.Camera;
import org.kuchelmeister.engine.geometry.OrdinaryGraphicalObject;
import org.kuchelmeister.engine.geometry.primitive.Triangle;
import org.kuchelmeister.engine.geometry.vertex.ObjectVertex;

public class Icosahedron extends OrdinaryGraphicalObject {
    private final Vector3f centre;

    public Icosahedron(final Vector3f centre, final float radius, final String texture) {
        this(null, centre, radius, texture);
    }

    public Icosahedron(final Camera camera, final Vector3f centrePos, final float radius, final String texture) {
        super(camera, texture);
        this.centre = centrePos;
        final float golden = (float) ((1 + Math.sqrt(5d)) / 2);
        final Vector3f[] points = new Vector3f[] {
                new Vector3f(-1, golden, 0f),
                new Vector3f(1, golden, 0f),
                new Vector3f(-1, -golden, 0f),
                new Vector3f(1, -golden, 0f),

                new Vector3f(0f, -1, golden),
                new Vector3f(0f, 1, golden),
                new Vector3f(0f, -1, -golden),
                new Vector3f(0f, 1, -golden),

                new Vector3f(golden, 0f, -1),
                new Vector3f(golden, 0f, 1),
                new Vector3f(-golden, 0f, -1),
                new Vector3f(-golden, 0f, 1),
        };
        final Vector2f[] texturePoints = new Vector2f[points.length];
        final Vector3f[] normals = new Vector3f[points.length];

        for (int i = 0; i < points.length; i++) {
            points[i] = points[i].normalize(radius);
            texturePoints[i] = Icosahedron.projectOnTexture(points[i], centre);
            normals[i] = points[i];
            points[i] = points[i].add(centre);
        }

        // Calculate Normals

        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[0], texturePoints[0], normals[0]),
                new ObjectVertex(points[11], texturePoints[11], normals[11]),
                new ObjectVertex(points[5], texturePoints[5], normals[5])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[0], texturePoints[0], normals[0]),
                new ObjectVertex(points[5], texturePoints[5], normals[5]),
                new ObjectVertex(points[1], texturePoints[1], normals[1])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[0], texturePoints[0], normals[0]),
                new ObjectVertex(points[1], texturePoints[1], normals[1]),
                new ObjectVertex(points[7], texturePoints[7], normals[7])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[0], texturePoints[0], normals[0]),
                new ObjectVertex(points[7], texturePoints[7], normals[7]),
                new ObjectVertex(points[10], texturePoints[10], normals[10])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[0], texturePoints[0], normals[0]),
                new ObjectVertex(points[10], texturePoints[10], normals[10]),
                new ObjectVertex(points[11], texturePoints[11], normals[11])));

        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[1], texturePoints[1], normals[1]),
                new ObjectVertex(points[5], texturePoints[5], normals[5]),
                new ObjectVertex(points[9], texturePoints[9], normals[9])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[5], texturePoints[5], normals[5]),
                new ObjectVertex(points[11], texturePoints[11], normals[11]),
                new ObjectVertex(points[4], texturePoints[4], normals[6])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[11], texturePoints[11], normals[11]),
                new ObjectVertex(points[10], texturePoints[10], normals[10]),
                new ObjectVertex(points[2], texturePoints[2], normals[2])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[10], texturePoints[10], normals[10]),
                new ObjectVertex(points[7], texturePoints[7], normals[7]),
                new ObjectVertex(points[6], texturePoints[6], normals[6])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[7], texturePoints[7], normals[7]),
                new ObjectVertex(points[1], texturePoints[1], normals[1]),
                new ObjectVertex(points[8], texturePoints[8], normals[8])));

        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[3], texturePoints[3], normals[3]),
                new ObjectVertex(points[9], texturePoints[9], normals[9]),
                new ObjectVertex(points[4], texturePoints[4], normals[4])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[3], texturePoints[3], normals[3]),
                new ObjectVertex(points[4], texturePoints[4], normals[4]),
                new ObjectVertex(points[2], texturePoints[2], normals[2])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[3], texturePoints[3], normals[3]),
                new ObjectVertex(points[2], texturePoints[2], normals[2]),
                new ObjectVertex(points[6], texturePoints[6], normals[6])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[3], texturePoints[3], normals[3]),
                new ObjectVertex(points[6], texturePoints[6], normals[6]),
                new ObjectVertex(points[8], texturePoints[8], normals[8])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[3], texturePoints[3], normals[3]),
                new ObjectVertex(points[8], texturePoints[8], normals[8]),
                new ObjectVertex(points[9], texturePoints[9], normals[9])));

        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[4], texturePoints[4], normals[4]),
                new ObjectVertex(points[9], texturePoints[9], normals[9]),
                new ObjectVertex(points[5], texturePoints[5], normals[5])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[2], texturePoints[2], normals[2]),
                new ObjectVertex(points[4], texturePoints[4], normals[4]),
                new ObjectVertex(points[11], texturePoints[11], normals[11])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[6], texturePoints[6], normals[6]),
                new ObjectVertex(points[2], texturePoints[2], normals[2]),
                new ObjectVertex(points[10], texturePoints[10], normals[10])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[8], texturePoints[8], normals[8]),
                new ObjectVertex(points[6], texturePoints[6], normals[6]),
                new ObjectVertex(points[7], texturePoints[7], normals[7])));
        this.subGeometry.add(new Triangle(
                new ObjectVertex(points[9], texturePoints[9], normals[9]),
                new ObjectVertex(points[8], texturePoints[8], normals[8]),
                new ObjectVertex(points[1], texturePoints[1], normals[1])));
    }

    public static Vector2f projectOnTexture(final Vector3f point, final Vector3f centre) {
        // https://stackoverflow.com/questions/5674149/3d-coordinates-on-a-sphere-to-latitude-and-longitude#5674243
        final double x = point.x - centre.x;
        final double y = point.y - centre.y;
        final double z = point.z - centre.z;

        final double r = Math.sqrt(x * x + y * y + z * z);

        final double theta = Math.acos(y / r);
        final double phi = Math.atan(x / z);

        final float lon = (float) ((180.0d - Math.toDegrees(phi)) / 360.0d);
        final float lat = (float) ((180.0d - Math.toDegrees(theta)) / 180.0d);

        // System.out.println("(" + x + ", " + y + ", " + z + ") => (" + lon +
        // ", " + lat + ")");
        return new Vector2f(lon, lat);
    }

}
