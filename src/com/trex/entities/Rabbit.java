package com.trex.entities;

import java.awt.Image;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Rabbit                                                                              *
 *  DESCRIPTION: ศัตรูชนิดกระต่ายที่เป็นอุปสรรคเคลื่อนที่บุกพุ่งชนด้วยความเร็วสูง                                *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Inheritance: ดึง DNA สืบทอดสายสัมพันธ์มาจากคลาส Obstacle                                      *
 *  - Overriding: เขียนทับ update() กำหนดรูปแบบการวิ่งบุกเดี่ยวเข้าหาผู้เล่น                               *
 *                                                                                             *
 **********************************************************************************************/
public class Rabbit extends Obstacle {

    public Rabbit(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image, speed);
    }

    @Override
    public void update() {
        x -= speed;
    }
}
