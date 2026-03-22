package com.trex.entities;

import com.trex.core.Collidable;
import java.awt.Image;
import java.awt.Rectangle;

/*****************************************************************************
 *                                                                           *
 * CLASS: Obstacle                                                           *
 * DESCRIPTION: Abstract base class for all enemies and hazards.             *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Abstract Class: Generalizes obstacle behavior.                          *
 * - Inheritance: Extends from GameObject.                                   *
 * - Interface: Implements Collidable.                                       *
 *                                                                           *
 ****************************************************************************/
public abstract class Obstacle extends GameObject implements Collidable {
    protected double speed;

    public Obstacle(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image);
        this.speed = speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public Rectangle getBounds() {
        
        int xOffset = 25; 
        int yOffset = 25; 
        int reducedWidth = 45; 
        int reducedHeight = 35; 
        return new Rectangle((int)x + xOffset, (int)y + yOffset, width - reducedWidth, height - reducedHeight);
    }
}
