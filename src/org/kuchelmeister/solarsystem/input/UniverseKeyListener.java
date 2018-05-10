package org.kuchelmeister.solarsystem.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.kuchelmeister.engine.camera.Camera;
import org.kuchelmeister.solarsystem.geometry.SphericalSpaceObject;
import org.kuchelmeister.solarsystem.geometry.Universe;

public class UniverseKeyListener implements KeyListener {
	Universe universe;
	Camera camera;
	SphericalSpaceObject[] spaceObjects;

	public UniverseKeyListener(final Universe universe, final Camera camera) {
		this.universe = universe;
		this.spaceObjects = new SphericalSpaceObject[10];
		this.camera = camera;
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
		// Jumping to the position
		if (KeyEvent.VK_0 <= e.getKeyCode() && e.getKeyCode() <= KeyEvent.VK_9) {
			final int position = e.getKeyCode() - KeyEvent.VK_0;
			final SphericalSpaceObject sObject = spaceObjects[position];
			if (sObject != null) {
				// TODO: get position and move camera to that position
				// lookingDirection * radius + sObject.getPosition()
				this.camera.setPosition(sObject.getPosition());
			}
		}
	}

	public void setKeySphericalSpaceObject(final SphericalSpaceObject object, final int numberKey) {
		assert 0 <= numberKey && numberKey <= 9;
		spaceObjects[numberKey] = object;
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
