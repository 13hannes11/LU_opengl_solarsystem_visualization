package org.kuchelmeister.solarsystem.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.kuchelmeister.solarsystem.geometry.Universe;

public class UniverseKeyListener implements KeyListener {
    Universe universe;

    public UniverseKeyListener(final Universe universe) {
        this.universe = universe;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        final float stepsPosition = 0.1f;
        final float stepsRotation = 10.0f;
        if (!universe.isPaused()) {
            if (e.getKeyCode() == KeyEvent.VK_PLUS) {
                // Faster time scale
                universe.doubleTimeScale();
                System.out.println("Time Scale Doubled to: " + universe.getTimeScale());
            } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                // Slower timescale
                universe.halfTimeScale();
                System.out.println("Time scale halfed to: " + universe.getTimeScale());
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                // Reverse timescale
                universe.reverseTime();
                System.out.println("Time scale halfed to: " + universe.getTimeScale());
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Pause
            universe.tooglePause();
            System.out.println("Toggledpause:" + universe.getTimeScale());
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

}
