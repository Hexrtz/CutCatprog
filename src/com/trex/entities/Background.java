package com.trex.entities;

import java.awt.Graphics2D;
import java.awt.Image;

public class Background extends GameObject {
    private double scrollSpeed;
    private int screenWidth;

    public Background(double x, double y, int width, int height, Image image, double scrollSpeed, int screenWidth) {
        super(x, y, width, height, image);
        this.scrollSpeed = scrollSpeed;
        this.screenWidth = screenWidth; // Screen width to determine when to loop
    }

    @Override
    public void update() {
        x -= scrollSpeed;
        
        // When the first image goes completely off-screen to the left, 
        // reset its position to perfectly trail the second "ghost" image.
        if (x <= -width) {
            // Adjust to perfectly align, preventing gaps caused by uneven speed division
            x += width;
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (image != null) {
            // Draw original image
            g2d.drawImage(image, (int) x, (int) y, width, height, null);
            
            // Draw a second copy of it trailing immediately behind
            // As long as the background is scrolling, we need to draw a tile right next to it
            // Only draw if there's space on screen for the second image to be visible
            if (scrollSpeed > 0 && x + width < screenWidth) {
                g2d.drawImage(image, (int) x + width, (int) y, width, height, null);
            }
        }
    }
}
