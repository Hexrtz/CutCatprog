package com.trex.entities;

import com.trex.core.Renderable;
import java.awt.Graphics2D;
import java.awt.Image;

/*****************************************************************************
 *                                                                           *
 * CLASS: GameObject                                                         *
 * DESCRIPTION: The base abstract class for all physical entities in the game. *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Abstract Class: Defines an abstract blueprint for entities.             *
 * - Interface: Implements Renderable for drawing mechanics.                 *
 * - Encapsulation: Uses protected modifiers for position variables.         *
 *                                                                           *
 ****************************************************************************/
public abstract class GameObject implements Renderable {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected Image image;

    public GameObject(double x, double y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public abstract void update();

    @Override
    public void draw(Graphics2D g2d) {
        if (image != null) {
            g2d.drawImage(image, (int) x, (int) y, width, height, null);
        }
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
