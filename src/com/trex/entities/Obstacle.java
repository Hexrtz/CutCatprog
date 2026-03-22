package com.trex.entities;

import com.trex.core.Collidable;
import java.awt.Image;
import java.awt.Rectangle;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Obstacle                                                                            *
 *  DESCRIPTION: คลาสแม่นามธรรมสำหรับสร้างศัตรู พลวัติและสิ่งกีดขวางสารพัดชนิด                             *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Abstract Class: รวบรวมและวางโครงสร้างพฤติกรรมความเร็วสิ่งกีดขวาง                               *
 *  - Inheritance: รับช่วงต่อสืบทอดรูปแบบเบื้องต้นมาจาก GameObject                                     *
 *  - Interface: นำเป้าหมายตัวอันตรายเข้าสู่กฎการชนด้วย Collidable                                    *
 *                                                                                             *
 **********************************************************************************************/
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
        
        int xOffset = (int) (width * 0.26); 
        int yOffset = (int) (height * 0.26); 
        int reducedWidth = (int) (width * 0.47); 
        int reducedHeight = (int) (height * 0.37); 
        return new Rectangle((int)x + xOffset, (int)y + yOffset, width - reducedWidth, height - reducedHeight);
    }
}
