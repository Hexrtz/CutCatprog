package com.trex.entities;

import com.trex.core.Collidable;
import java.awt.Image;
import java.awt.Rectangle;

public class Character extends GameObject implements Collidable {
    private double velocityY = 0;
    private double gravity = 0.5;
    private boolean isJumping = false;
    private final double groundY;

    // INCREASED SIZE: from 64x64/50x50 to 80x80
    public Character(double x, double y, int width, int height, Image image) {
        super(x, y, width, height, image);
        this.groundY = y;
    }

    public Character(double x, double y, Image image) {
        this(x, y, 90, 90, image); // Larger default size
    }

    @Override
    public void update() {
        velocityY += gravity;
        y += velocityY;

        if (y >= groundY) {
            y = groundY;
            isJumping = false;
            velocityY = 0;
        }
    }

    public void jump() {
        if (!isJumping) {
            velocityY = -12; // Slightly higher jump to accommodate larger size
            isJumping = true;
        }
    }

    public void jump(double customForce) {
        if (!isJumping) {
            velocityY = -customForce;
            isJumping = true;
        }
    }

    @Override
    public Rectangle getBounds() {
        // Shurnk hitbox drastically to match ONLY the physical body of the girl, ignoring the fluffy skirts and rabbit ears
        int xOffset = 30; // Push right
        int yOffset = 30; // Push down
        int reducedWidth = 60; // Cut off sides
        int reducedHeight = 35; // Cut off bottom and top
        return new Rectangle((int)x + xOffset, (int)y + yOffset, width - reducedWidth, height - reducedHeight);
    }
}
