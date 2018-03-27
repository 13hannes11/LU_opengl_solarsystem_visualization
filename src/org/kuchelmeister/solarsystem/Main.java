package org.kuchelmeister.solarsystem;

import org.kuchelmeister.engine.Scene;

public class Main {

    public static void main(final String[] args) {
        Scene myScene;

        myScene = new SolarSystemScene(800, 600, "Solar System Visualisation by Hannes Kuchelmeister!");
        myScene.run();

    }

}