package org.kuchelmeister.engine.geometry.skybox;

import org.joml.Vector3f;
import org.kuchelmeister.engine.camera.Camera;
import org.kuchelmeister.engine.geometry.GraphicalObject;
import org.kuchelmeister.engine.geometry.primitive.Quadrilateral;
import org.kuchelmeister.engine.geometry.texture.CubemapTexture;
import org.kuchelmeister.engine.geometry.vertex.SkyboxVertex;
import org.kuchelmeister.solarsystem.camera.SkyboxCamera;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;

import shader.Shader;

public class Skybox extends GraphicalObject {
    public static final int VERTEX_FLOAT_COUNT = 6;
    private final CubemapTexture cubemap;

    public Skybox(final Camera camera, final float size, final String[] textures) {
        super(new Shader("cubemap.vs.glsl", "cubemap.fs.glsl"), camera);

        cubemap = new CubemapTexture(textures);

        final float halfSize = size / 2;

        final SkyboxVertex frontTopLeft = new SkyboxVertex(new Vector3f(-halfSize, halfSize, halfSize));
        final SkyboxVertex frontTopRight = new SkyboxVertex(new Vector3f(halfSize, halfSize, halfSize));
        final SkyboxVertex frontBottomLeft = new SkyboxVertex(new Vector3f(-halfSize, -halfSize, halfSize));
        final SkyboxVertex frontBottomRight = new SkyboxVertex(new Vector3f(halfSize, -halfSize, halfSize));

        final SkyboxVertex backTopLeft = new SkyboxVertex(new Vector3f(-halfSize, halfSize, -halfSize));
        final SkyboxVertex backTopRight = new SkyboxVertex(new Vector3f(halfSize, halfSize, -halfSize));
        final SkyboxVertex backBottomLeft = new SkyboxVertex(new Vector3f(-halfSize, -halfSize, -halfSize));
        final SkyboxVertex backBottomRight = new SkyboxVertex(new Vector3f(halfSize, -halfSize, -halfSize));

        // Front
        this.subGeometry.add(new Quadrilateral(frontTopLeft, frontTopRight, frontBottomRight, frontBottomLeft));
        // Left
        this.subGeometry.add(new Quadrilateral(frontBottomLeft, backBottomLeft, backTopLeft, frontTopLeft));
        // Top
        this.subGeometry.add(new Quadrilateral(frontTopLeft, backTopLeft, backTopRight, frontTopRight));

        // Back
        this.subGeometry.add(new Quadrilateral(backBottomRight, backTopRight, backTopLeft, backBottomLeft));
        // Down
        this.subGeometry.add(new Quadrilateral(backBottomLeft, frontBottomLeft, frontBottomRight, backBottomRight));
        // Right
        this.subGeometry.add(new Quadrilateral(backBottomRight, frontBottomRight, frontTopRight, backTopRight));

    }

    @Override
    public Camera getCamera() {
        final Camera skyboxCam = new SkyboxCamera(super.getCamera());
        return skyboxCam;
    }

    @Override
    public void intiVertexAttributes(final GL3 gl) {
        cubemap.loadFromFile(gl);

        final int STRIDE = VERTEX_FLOAT_COUNT * Buffers.SIZEOF_FLOAT;
        final int posOffset = 0;
        final int textureOffset = 3 * Buffers.SIZEOF_FLOAT;

        // position attribute
        gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, STRIDE, posOffset);
        gl.glEnableVertexAttribArray(0);
        // texture position attribute
        gl.glVertexAttribPointer(1, 3, GL3.GL_FLOAT, false, STRIDE,
                textureOffset);
        gl.glEnableVertexAttribArray(1);
    }

    @Override
    public void displayParametersANDUniforms(final GL3 gl) {
        cubemap.bind(gl);
        super.displayParametersANDUniforms(gl);
    }

    @Override
    public void afterDisplay(final GL3 gl) {
        gl.glEnable(GL3.GL_DEPTH_TEST);
    }

    @Override
    public void preDisplay(final GL3 gl) {
        gl.glDisable(GL3.GL_DEPTH_TEST);
    }
}
