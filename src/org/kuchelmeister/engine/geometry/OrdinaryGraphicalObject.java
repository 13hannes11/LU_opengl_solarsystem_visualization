package org.kuchelmeister.engine.geometry;

import org.joml.Vector3f;
import org.kuchelmeister.engine.camera.Camera;
import org.kuchelmeister.engine.geometry.texture.Texture;
import org.kuchelmeister.engine.lighting.GlobalLights;
import org.kuchelmeister.engine.lighting.Material;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;

import shader.Shader;

public abstract class OrdinaryGraphicalObject extends GraphicalObject {
    public static final int VERTEX_FLOAT_COUNT = 8;
    private final Texture texture;
    private final GlobalLights globalLights;

    public GlobalLights getGlobalLights() {
        return globalLights;
    }

    private Material material;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(final Material material) {
        this.material = material;
    }

    public OrdinaryGraphicalObject(final Camera camera, final String textureFile) {
        super(new Shader("object.vs.glsl", "object.fs.glsl"), camera);
        this.texture = new Texture(textureFile);

        this.globalLights = GlobalLights.getInstance();
        this.material = Material.getDefault();
    }

    @Override
    public void intiVertexAttributes(final GL3 gl) {
        gl.glEnable(GL3.GL_DEPTH_TEST);
        texture.loadFromFile(gl);

        final int STRIDE = VERTEX_FLOAT_COUNT * Buffers.SIZEOF_FLOAT;
        final int posOffset = 0;
        final int textureOffset = 3 * Buffers.SIZEOF_FLOAT;
        final int normalOffset = 5 * Buffers.SIZEOF_FLOAT;
        // position attribute
        gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, STRIDE, posOffset);
        gl.glEnableVertexAttribArray(0);
        // texture position attribute
        gl.glVertexAttribPointer(1, 2, GL3.GL_FLOAT, false, STRIDE,
                textureOffset);
        gl.glEnableVertexAttribArray(1);
        // normal vectors
        gl.glVertexAttribPointer(2, 3, GL3.GL_FLOAT, false, STRIDE,
                normalOffset);
        gl.glEnableVertexAttribArray(2);

    }

    @Override
    public void displayParametersANDUniforms(final GL3 gl) {
        texture.bind(gl);
        super.displayParametersANDUniforms(gl);
        final int eyePosLocation = gl.glGetUniformLocation(this.getShader().getProgramId(), "EyePos");
        if (eyePosLocation != -1) {
            final Vector3f pos = this.getCamera().getPosition();
            gl.glUniform3fv(eyePosLocation, 1, new float[] {pos.x, pos.y, pos.z}, 0);
        }
        globalLights.glSetUniforms(gl, this.getShader());
        material.glSetUniforms(gl, this.getShader());
    }

}
