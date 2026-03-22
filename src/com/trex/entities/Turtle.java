package com.trex.entities;

import java.awt.Graphics2D;
import java.awt.Image;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Turtle                                                                              *
 *  DESCRIPTION: ศัตรูชนิดเต่าเคลื่อนที่เข้าหาผู้เล่น พร้อมอนิเมชั่นเดิน 2 เฟรม                             *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Inheritance: สืบทอดสายสัมพันธ์มาจากคลาส Obstacle                                        *
 *  - Overriding: เขียนทับเมธอด update() และ draw() เพื่อทำระบบอนิเมชั่น                           *
 *                                                                                             *
 **********************************************************************************************/
public class Turtle extends Obstacle {
    private int frame = 0;
    private int animationTick = 0;
    private int animationThreshold = 5;

    public void setAnimationSpeed(double speedBonus) {
        this.animationThreshold = Math.max(2, 5 - (int)(speedBonus * 1.0));
    }

    public Turtle(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image, speed);
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
            int frameWidth = image.getWidth(null) / 2;
            int frameHeight = image.getHeight(null);
            
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
