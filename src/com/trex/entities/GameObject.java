package com.trex.entities;

import com.trex.core.Renderable;
import java.awt.Graphics2D;
import java.awt.Image;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: GameObject                                                                          *
 *  DESCRIPTION: คลาสนามธรรมที่เป็นโครงสร้างหลักเบื้องต้นสุดของออบเจกต์ต่างๆ ภายในเกม                     *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Abstract Class: กำหนดพิมพ์เขียวเบื้องต้นนามธรรมให้รูปภาพและพิกัดแกน                                *
 *  - Interface: ประกอบร่างกับ Renderable ทำสัญญารองรับฟีเจอร์การวาด                                 *
 *  - Encapsulation: เปิดใช้ตัวแปรแบบ protected ไว้จัดการข้อมูล                                       *
 *                                                                                             *
 **********************************************************************************************/
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
