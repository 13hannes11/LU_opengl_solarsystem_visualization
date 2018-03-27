package org.kuchelmeister.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.kuchelmeister.engine.camera.Camera;

public class CameraKeyListener implements KeyListener {
    Camera camera;

    public CameraKeyListener(final Camera camera) {
        this.camera = camera;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        final float stepsPosition = 0.1f;
        final float stepsRotation = 10.0f;
        System.out.println("Key Pressed: " + e.getKeyChar() + " | " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Rotate up
            camera.offsetOrientation(stepsRotation, 0.0f);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Rotate down
            camera.offsetOrientation(-stepsRotation, 0.0f);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Rotate left
            camera.offsetOrientation(0.0f, stepsRotation);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Rotate right
            camera.offsetOrientation(0.0f, -stepsRotation);
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            // GO Left
            camera.offsetPosition(camera.rightVec().mul(-stepsPosition));
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            // Go Right
            camera.offsetPosition(camera.rightVec().mul(stepsPosition));
            ;

        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            // GO Forward
            camera.offsetPosition(camera.forwardVec().mul(stepsPosition));
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            // Go Backward
            camera.offsetPosition(camera.forwardVec().mul(-stepsPosition));
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            // GO UP
            camera.offsetPosition(camera.upVec().mul(stepsPosition));
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            // Go Down
            camera.offsetPosition(camera.upVec().mul(-stepsPosition));
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

}
