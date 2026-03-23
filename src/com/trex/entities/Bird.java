package com.trex.entities;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Bird                                                                                *
 *  DESCRIPTION: ศัตรูชนิดนกที่บินอยู่บนอากาศ (single-frame, tight hitbox)                          *
 *                                                                                             *
 **********************************************************************************************/
public class Bird extends Obstacle {

    public Bird(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image, speed);
    }

    /** ไม่ใช้ animation แล้ว — เก็บ method ไว้เพื่อไม่ให้ GameScreen compile error */
    public void setAnimationSpeed(double speedBonus) {
        // no-op: single frame, no animation needed
    }

    @Override
    public void update() {
        x -= speed;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (image != null) {
            g2d.drawImage(image, (int) x, (int) y, width, height, null);
        }
    }

    /**
     * Hitbox แน่นขึ้น: ตัดขอบปีกและพื้นที่โล่งออก
     * ให้ hitbox ครอบเฉพาะลำตัวนก
     *   - ตัดซ้าย/ขวา 20% ของ width
     *   - ตัดบน 25%, ตัดล่าง 20% ของ height
     */
    @Override
    public Rectangle getBounds() {
        int xOff  = (int)(width  * 0.20);
        int yOff  = (int)(height * 0.25);
        int hb_w  = (int)(width  * 0.60);
        int hb_h  = (int)(height * 0.55);
        return new Rectangle((int)x + xOff, (int)y + yOff, hb_w, hb_h);
    }
}
