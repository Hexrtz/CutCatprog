package com.trex.entities;

import java.awt.Graphics2D;
import java.awt.Image;

/*****************************************************************************
 *                                                                           *
 * CLASS: Background                                                         *
 * DESCRIPTION: Manages scrolling background layers like sky and ground.     *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Inheritance: Extends from GameObject.                                   *
 * - Overriding: Overrides update() and draw() for infinite scrolling.       *
 *                                                                           *
 ****************************************************************************/
public class Background extends GameObject {
    private double scrollSpeed;
    private int screenWidth;

    public Background(double x, double y, int width, int height, Image image, double scrollSpeed, int screenWidth) {
        super(x, y, width, height, image);
        this.scrollSpeed = scrollSpeed;
        this.screenWidth = screenWidth; 
    }

    public void setScrollSpeed(double scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    @Override
    public void update() {
        x -= scrollSpeed;

        if (x <= -width) {
            
            x += width;
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (image != null) {
            
            g2d.drawImage(image, (int) x, (int) y, width, height, null);

            if (scrollSpeed > 0 && x + width < screenWidth) {
                g2d.drawImage(image, (int) x + width, (int) y, width, height, null);
            }
        }
    }
}
