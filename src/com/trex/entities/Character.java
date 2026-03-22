package com.trex.entities;

import com.trex.core.Collidable;
import java.awt.Image;
import java.awt.Rectangle;

/*****************************************************************************
 *                                                                           *
 * CLASS: Character                                                          *
 * DESCRIPTION: Represents the player character with jumping physics and logic. *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Inheritance: Extends from GameObject.                                   *
 * - Interface: Implements Collidable for hitboxes.                          *
 * - Overloading: Multiple constructors and jump() methods.                  *
 * - Overriding: Overrides update() and getBounds().                         *
 *                                                                           *
 ****************************************************************************/
public class Character extends GameObject implements Collidable {
    private double velocityY = 0;
    private double gravity = 0.5;
    private boolean isJumping = false;
    private final double groundY;

    public Character(double x, double y, int width, int height, Image image) {
        super(x, y, width, height, image);
        this.groundY = y;
    }

    public Character(double x, double y, Image image) {
        this(x, y, 90, 90, image); 
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
            velocityY = -12; 
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
        
        int xOffset = 30; 
        int yOffset = 30; 
        int reducedWidth = 60; 
        int reducedHeight = 35; 
        return new Rectangle((int)x + xOffset, (int)y + yOffset, width - reducedWidth, height - reducedHeight);
    }
}
