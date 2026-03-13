package com.trex.core;

import com.trex.ui.GameScreen;
import javax.swing.JFrame;

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
