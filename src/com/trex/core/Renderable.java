package com.trex.core;

import java.awt.Graphics2D;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Renderable                                                                          *
 *  DESCRIPTION: อินเตอร์เฟซตัวสัญญาใจสำหรับคอมโพเนนต์ในเกมที่ต้องถูกวาดลงบนหน้าจอ                        *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Interface Class: เป็นสัญญาแข็งขันว่าคลาสที่ใช้ต้องมีเมธอด draw()                                   *
 *                                                                                             *
 **********************************************************************************************/
public interface Renderable {
    void draw(Graphics2D g2d);
}
