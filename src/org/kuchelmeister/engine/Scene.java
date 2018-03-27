package org.kuchelmeister.engine;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.lang3.time.StopWatch;
import org.kuchelmeister.engine.camera.Camera;
import org.kuchelmeister.engine.geometry.GraphicalObject;
import org.kuchelmeister.engine.geometry.skybox.Skybox;
import org.kuchelmeister.engine.input.CameraKeyListener;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Scene extends JFrame implements GLEventListener {
    private final StopWatch stopWatch;
    private static final long serialVersionUID = 1L;

    protected final Camera camera;
    private Skybox skybox;
    private final List<GraphicalObject> graphicsObjects;

    private final GLCanvas canvas;
    private final FPSAnimator animator;

    private final CameraKeyListener kListener;

    public GLCanvas getCanvas() {
        return canvas;
    }

    public Scene(final int width, final int height, final String title) {
        super(title);

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        final GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);

        canvas = new GLCanvas(capabilities);
        this.getContentPane().add(canvas);
        camera = new Camera();
        kListener = new CameraKeyListener(camera);
        canvas.addKeyListener(kListener);
        canvas.addGLEventListener(this);

        canvas.setAutoSwapBufferMode(false);

        this.getContentPane().add(canvas);

        this.graphicsObjects = new LinkedList<>();
        animator = new FPSAnimator(canvas, 60, true);
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    public void addGraphicalObject(final GraphicalObject gObject) {
        gObject.setCamera(camera);
        graphicsObjects.add(gObject);
    }

    public void setSkybox(final String[] textures) {
        this.skybox = new Skybox(this.camera, 10.0f, textures);
    }

    @Override
    public void init(final GLAutoDrawable drawable) {

        System.out.println("INFO: init()");
        System.setProperty("sun.awt.noerasebackground", "true");
        System.setProperty("sun.java2d.noddraw", "true");
        final GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        if (skybox != null) {
            skybox.init(gl);
        }
        for (final GraphicalObject graphicalObject : graphicsObjects) {
            graphicalObject.init(gl);
        }
    }

    public void run() {
        this.setVisible(true);
        animator.start();
    }

    public void updateObj() {

        for (final GraphicalObject graphicalObject : graphicsObjects) {
            graphicalObject.update();
        }
    }

    @Override
    public void display(final GLAutoDrawable drawable) {
        updateObj();

        // System.out.println("INFO: display()");

        final GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        if (skybox != null) {
            skybox.display(gl);
        }
        for (final GraphicalObject graphicalObject : graphicsObjects) {
            graphicalObject.display(gl);
        }
        gl.glFlush();
        canvas.swapBuffers();
    }

    @Override
    public void dispose(final GLAutoDrawable drawable) {
        System.out.println("INFO: dispose()");
    }

    @Override
    public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {
        System.out.printf("INFO: reshape(%d, %d, %d, %d)\n", x, y, width, height);
        // windowHeight = height;
    }

}
