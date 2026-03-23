package com.trex.entities;

import com.trex.core.Collidable;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Character                                                                           *
 *  DESCRIPTION: ตัวละครหลักของผู้เล่น รองรับตรรกะระบบการทำงานและฟิสิกส์การกระโดด                       *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Inheritance: สืบทอดคุณสมบัติโครงสร้างพิกัดมาจากคลาส GameObject                                  *
 *  - Interface: นำ Collidable มาใช้สร้าง Hitbox ของตัวละคร                                       *
 *  - Overloading: มีคอนสตรัคเตอร์และเมธอด jump() หลายรูปแบบ (รับพารามิเตอร์ต่างกัน)                    *
 *  - Overriding: เขียนทับเมธอด update() และ getBounds()                                         *
 *                                                                                             *
 **********************************************************************************************/
public class Character extends GameObject implements Collidable {
    private double velocityY = 0;
    private double gravity = 0.5;
    private boolean isJumping = false;
    private final double groundY;
    private int frame = 0;
    private int jumpCount = 0;
    private int animationTick = 0;
    private int animationThreshold = 12;

    public void setAnimationSpeed(double speedBonus) {
        this.animationThreshold = Math.max(4, 12 - (int)(speedBonus * 1.5));
    }

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
            jumpCount = 0;
            velocityY = 0;
        }

        animationTick++;
        if (animationTick >= animationThreshold) {
            // frame = (frame == 0) ? 1 : 0; // Fix to use single frame as per user requirements
            frame = 0;
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
            
            // Flip horizontally
            int dx1 = (int) x + width;
            int dx2 = (int) x;
            int dy1 = (int) y;
            int dy2 = (int) y + height;
            
            g2d.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
    }

    public void jump() {
        if (!isJumping || jumpCount < 2) {
            velocityY = -12; 
            isJumping = true;
            jumpCount++;
        }
    }

    public void jump(double customForce) {
        if (!isJumping || jumpCount < 2) {
            velocityY = -customForce;
            isJumping = true;
            jumpCount++;
        }
    }

    @Override
    public Rectangle getBounds() {
        
        int xOffset = (int) (width * 0.27); 
        int yOffset = (int) (height * 0.27); 
        int reducedWidth = (int) (width * 0.55); 
        int reducedHeight = (int) (height * 0.32); 
        return new Rectangle((int)x + xOffset, (int)y + yOffset, width - reducedWidth, height - reducedHeight);
    }
}
