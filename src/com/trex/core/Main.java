package com.trex.core;

import com.trex.ui.GameScreen;
import javax.swing.JFrame;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: Main                                                                                *
 *  DESCRIPTION: จุดเริ่มต้นของโปรแกรม ทำหน้าที่ตั้งค่าหน้าต่างหลักและเป็นตัวประเดิมเรียกใช้งานตัวเกม             *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Association: เป็นตัวสร้างและควบคุมอินสแตนซ์หน้าจอ GameScreen ทั้งดุ้น                               *
 *                                                                                             *
 **********************************************************************************************/
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("T-Rex Runner OOP");
        GameScreen rootScreen = new GameScreen();

        frame.add(rootScreen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        rootScreen.startGame();
    }
}
