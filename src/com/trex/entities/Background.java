package com.trex.entities;

import java.awt.Graphics2D;
import java.awt.Image;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Background                                                                          *
 *  DESCRIPTION: จัดการการเลื่อนไปข้างหน้าของเลเยอร์ฉากหลัง เช่น ท้องฟ้าและพื้นดิน                          *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Inheritance: สืบทอดคุณสมบัติระดับคลาสมาจาก GameObject แม่                                      *
 *  - Overriding: เขียนทับเมธอด update() และ draw() เพื่อทำภาพเลื่อนแบบไร้ขอบเขต                      *
 *                                                                                             *
 **********************************************************************************************/
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
