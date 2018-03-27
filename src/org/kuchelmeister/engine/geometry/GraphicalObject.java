package org.kuchelmeister.engine.geometry;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.kuchelmeister.engine.camera.Camera;
import org.kuchelmeister.engine.geometry.primitive.Triangle;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.GLBuffers;

import shader.Shader;

public abstract class GraphicalObject {
    public static final int DRAWING_MODE = GL3.GL_TRIANGLES;

    private final StopWatch stopWatch;
    protected Shader shader;
    protected final List<Triangle> faces;
    protected final List<SubGeometry> subGeometry;

    private Matrix4f transformation;
    private Camera camera;

    private float[] floatArray;
    private int vaoId;

    public GraphicalObject(final Shader shader, final Camera camera) {
        this.shader = shader;
        this.subGeometry = new LinkedList<>();
        this.faces = new LinkedList<>();
        this.floatArray = new float[0];

        this.transformation = new Matrix4f();
        this.camera = camera;
        this.stopWatch = new StopWatch();
        this.stopWatch.start();
    }

    public void setPosition(final Vector3f pos) {
        this.transformation = transformation.setTranslation(pos);
    }

    public void setRotation(final float angleX, final float angleY, final float angleZ) {
        this.transformation.setRotationXYZ((float) Math.toRadians(angleX), (float) Math.toRadians(angleY),
                (float) Math.toRadians(angleZ));
    }

    public void rotate(final float angleDeg, final Vector3f axis) {
        this.transformation.rotate((float) Math.toRadians(angleDeg), axis.x, axis.y, axis.z);
    }

    public void scale(final Vector3f factor) {
        this.transformation.scale(factor.x, factor.y, factor.z);
    }

    public void translate(final Vector3f vec) {
        this.transformation.translate(vec.x, vec.y, vec.z);
    }

    public void generateFaces() {
        faces.clear();
        for (final SubGeometry sub : subGeometry) {
            faces.addAll(sub.getTriangles());
        }
    }

    public void setCamera(final Camera cam) {
        this.camera = cam;
    }

    public float[] getFloatArray() {
        if (this.floatArray.length <= 0) {
            generateFaces();
            floatArray = new float[0];
            for (final Triangle triangle : faces) {
                floatArray = ArrayUtils.addAll(floatArray, triangle.getVertices());
            }
        }
        return floatArray;
    }

    public void init(final GL3 gl) {

        // A simple temporary integer buffer to exchange data with the GPU
        final int vertexArrayObject[] = new int[1];
        // Create a VAO -- Vertex Array Object -- in the GPU's memory
        gl.glGenVertexArrays(1, IntBuffer.wrap(vertexArrayObject));
        vaoId = vertexArrayObject[0];

        // A simple temporary integer buffer to exchange data with the GPU
        final int vertexBufferObject[] = new int[1];
        // Create a buffer object in the GPU memory
        gl.glGenBuffers(1, IntBuffer.wrap(vertexBufferObject));

        // Bind our VAO to make it the active VAO in the OpenGL context
        gl.glBindVertexArray(vaoId);
        {
            // Make the buffer the active array buffer:
            // e.g. bind the newly created buffer object to the GL_ARRAY_BUFFER
            // context
            gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBufferObject[0]);
            {

                final FloatBuffer buffer = GLBuffers.newDirectFloatBuffer(this.getFloatArray());
                // allocate the required memory on the GPU and copy the data
                // from our vertexData-buffer into that memory

                gl.glBufferData(GL3.GL_ARRAY_BUFFER, this.getFloatArray().length * Buffers.SIZEOF_FLOAT, buffer,
                        GL3.GL_STATIC_DRAW);

                intiVertexAttributes(gl);

            }
            gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        }
        gl.glBindVertexArray(vaoId);

        shader.compile(gl);

    }

    public void setShader() {

    }

    public abstract void intiVertexAttributes(final GL3 gl);

    public void display(final GL3 gl) {

        preDisplay(gl);

        gl.glUseProgram(shader.getProgramId());
        {

            displayParametersANDUniforms(gl);

            gl.glBindVertexArray(vaoId);
            gl.glDrawArrays(GraphicalObject.DRAWING_MODE, 0, this.faces.size() * 3);

        }
        gl.glUseProgram(0);
        gl.glFlush();

        afterDisplay(gl);
    }

    public void afterDisplay(final GL3 gl) {
    }

    public void preDisplay(final GL3 gl) {
    }

    public void displayParametersANDUniforms(final GL3 gl) {
        final int transformationLocation = gl.glGetUniformLocation(this.getShader().getProgramId(), "ModelMatrix");
        if (transformationLocation != -1) {
            final float[] mat = new float[16];
            gl.glUniformMatrix4fv(transformationLocation, 1, false, transformation.get(mat), 0);
        }
        final int cameraLocation = gl.glGetUniformLocation(this.getShader().getProgramId(), "CameraMatrix");
        if (transformationLocation != -1) {
            final float[] mat = new float[16];
            gl.glUniformMatrix4fv(cameraLocation, 1, false, getCamera().getMatrix().get(mat), 0);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public Shader getShader() {
        return shader;
    }

    public final void update() {
        stopWatch.stop();
        updateLogic(stopWatch);
        stopWatch.reset();
        stopWatch.start();
    }

    public void updateLogic(final StopWatch sWatch) {

    }

}
