package com.trex.entities;

import java.awt.Image;
import java.awt.Graphics2D;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Bird                                                                                *
 *  DESCRIPTION: ศัตรูชนิดนกที่บินอยู่บนอากาศ                                                        *
 *                                                                                             *
 **********************************************************************************************/
public class Bird extends Obstacle {
    private int frame = 0;
    private int animationTick = 0;
    private int animationThreshold = 10;
    
    public Bird(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image, speed);
    }

    public void setAnimationSpeed(double speedBonus) {
        this.animationThreshold = Math.max(4, 10 - (int)(speedBonus));
    }

    @Override
    public void update() {
        x -= speed;
        animationTick++;
        if (animationTick >= animationThreshold) {
            frame = (frame == 0) ? 1 : 0;
            animationTick = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (image != null) {
            // Assuming bird sprite has 2 frames like other entities, if not it will just draw half.
            int frameWidth = image.getWidth(null) / 2;
            int frameHeight = image.getHeight(null);
            
            // If the image doesn't seem to be a sprite sheet, just draw normally.
            if (frameWidth <= 0 || frameHeight <= 0) {
                super.draw(g2d);
                return;
            }
            
            // For safety, let's just draw the whole image if it's meant to be a single frame,
            // or 2 frames if it's a sprite. Let's assume it has 2 frames:
            int sx1 = frame * frameWidth;
            int sy1 = 0;
            int sx2 = sx1 + frameWidth;
            int sy2 = frameHeight;
            
            int dx1 = (int) x;
            int dx2 = (int) x + width;
            int dy1 = (int) y;
            int dy2 = (int) y + height;
            
            g2d.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
    }
}
