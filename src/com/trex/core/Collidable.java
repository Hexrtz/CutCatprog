package com.trex.core;

import java.awt.Rectangle;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Collidable                                                                          *
 *  DESCRIPTION: อินเตอร์เฟซที่กำหนดขอบเขตและเงื่อนไขการชนกันของออบเจกต์ในเกม                          *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Interface Class: ทิ้งเมธอด getBounds() ให้คลาสอื่น และมี default isColliding()                 *
 *                                                                                             *
 **********************************************************************************************/
public interface Collidable {
    Rectangle getBounds();
    default boolean isColliding(Collidable other) {
        return this.getBounds().intersects(other.getBounds());
    }
}
