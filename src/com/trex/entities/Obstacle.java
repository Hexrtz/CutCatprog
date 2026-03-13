package com.trex.entities;

import com.trex.core.Collidable;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Obstacle extends GameObject implements Collidable {
    protected double speed;

    public Obstacle(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image);
        this.speed = speed;
    }

    @Override
    public Rectangle getBounds() {
        // Shurnk hitbox drastically to match ONLY the physical body of the rabbit
        int xOffset = 25; // Push right
        int yOffset = 25; // Push down
        int reducedWidth = 45; // Cut off sides
        int reducedHeight = 35; // Cut off bottom and top
        return new Rectangle((int)x + xOffset, (int)y + yOffset, width - reducedWidth, height - reducedHeight);
    }
}
