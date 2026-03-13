package com.trex.entities;

import java.awt.Image;

public class Rabbit extends Obstacle {
    
    // INCREASED SIZE from 45x45 to 60x60 (This will be called from GameScreen)
    public Rabbit(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image, speed);
    }

    @Override
    public void update() {
        x -= speed;
    }
}
