package com.trex.ui;

import com.trex.entities.Character;
import com.trex.entities.Obstacle;
import com.trex.entities.Turtle;
import com.trex.core.AudioPlayer;
import com.trex.entities.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: GameScreen                                                                          *
 *  DESCRIPTION: ส่วนแกนหลักของเกมทั้งหมด จัดการหน้าจอ ระบบ UI และควบคุมสถานะต่างๆ                     *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Inheritance: สืบทอดคลาสของ JPanel พื้นฐานของ Java สำหรับใช้วาดงาน GUI                         *
 *  - Interface: โคลนพลัง Runnable เข้าประคองและคุมเฟรมเรต Thread เกมลูป                           *
 *  - Association: ขมวดรวมประกอบออบเจกต์ Character, Obstacles และ Background ไว้ด้วยกัน            *
 *                                                                                             *
 **********************************************************************************************/
public class GameScreen extends JPanel implements Runnable {
    private final int WIDTH = 900; 
    private final int HEIGHT = 500; 

    private Thread gameThread;
    private boolean isRunning = false;

    private Character player;
    private List<Obstacle> obstacles;
    private Background sky;
    
    private AudioPlayer audioPlayer;

    private int state = 0; 
    private int score = 0;
    private int frames = 0;
    private int nextSpawnFrame = 100;

    private Image charImg, turtleImg, skyImg, startImg, gameoverImg;

    public GameScreen() {
        audioPlayer = new AudioPlayer();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        loadImages();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (state == 0) {
                        initGame();
                        state = 1;
                        audioPlayer.playBGM("src/sound/sound_play.wav");
                    } else if (state == 1) {
                        player.jump(13.5); 
                        audioPlayer.playSFX("src/sound/jump.wav");
                    } else if (state == 2) {
                        initGame();
                        state = 1;
                        audioPlayer.playBGM("src/sound/sound_play.wav");
                    }
                }
            }
        });

        initMenu();
    }

    private void loadImages() {
        
        charImg = new ImageIcon("src/img/human.png").getImage();
        turtleImg = new ImageIcon("src/img/total.png").getImage();
        skyImg = new ImageIcon("src/img/backgroudV2.png").getImage();
        startImg = new ImageIcon("src/img/StartGame.png").getImage();
        gameoverImg = new ImageIcon("src/img/gameover.png").getImage();
    }

    private void initMenu() {
        sky = new Background(0, 0, WIDTH, HEIGHT, skyImg, 0, WIDTH);
        
        

        player = new Character(60, 420 - 180, 180, 180, charImg);
        obstacles = new ArrayList<>();
    }

    private void initGame() {
        sky = new Background(0, 0, WIDTH, HEIGHT, skyImg, 1.0, WIDTH);
        
        player = new Character(60, 420 - 180, 180, 180, charImg);
        obstacles = new ArrayList<>();
        frames = 0;
        score = 0;
        nextSpawnFrame = java.util.concurrent.ThreadLocalRandom.current().nextInt(80, 150);
    }

    public void startGame() {
        if (gameThread == null || !isRunning) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();

            if (state == 1) {
                updateCore();
            }
            repaint();

            long sleepTime = 1000 / 60 - (System.currentTimeMillis() - startTime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateCore() {
        frames++;
        score = frames / 10;

        double speedBonus = 0;
        if (score >= 200) {
            speedBonus = 1.0 + Math.floor((score - 200) / 150.0);
        }

        double currentSpeed = 7.5 + (speedBonus * 1.5) + (frames / 500.0);
        // Parallax scaling based directly on obstacle speed
        sky.setScrollSpeed(currentSpeed / 3.0);
        

        sky.update();
                player.update();

        if (frames >= nextSpawnFrame && frames > 0) {

            int minSpawn = Math.max(40, 80 - (int) (speedBonus * 10));
            int maxSpawn = Math.max(80, 160 - (int) (speedBonus * 20));
            obstacles.add(new Turtle(WIDTH, 420 - 110, 130, 130, turtleImg, 5.0 + speedBonus + (frames / 1000.0)));
            nextSpawnFrame = frames + java.util.concurrent.ThreadLocalRandom.current().nextInt(minSpawn, maxSpawn);
        }

        List<Obstacle> toRemove = new ArrayList<>();
        for (Obstacle obs : obstacles) {
            obs.setSpeed(currentSpeed);
            if (obs instanceof Turtle) {
                ((Turtle) obs).setAnimationSpeed(speedBonus);
            }
            obs.update();
            if (obs.getX() + obs.getWidth() < 0) {
                toRemove.add(obs);
            }
        }
        
        for (Obstacle obs : obstacles) {
            if (player.isColliding(obs)) {
                state = 2; 
                audioPlayer.stopBGM();
                audioPlayer.playSFX("src/sound/dead_sound.wav");
                break;
            }
        }
        obstacles.removeAll(toRemove);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (state == 0) {

            sky.draw(g2d);
                        player.draw(g2d);

            if (startImg != null) {
                
                g2d.drawImage(startImg, 0, 0, WIDTH, HEIGHT, null);
            } else {
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics metrics = g2d.getFontMetrics();
                String text = "Press SPACE to Start Game";
                g2d.drawString(text, (WIDTH - metrics.stringWidth(text)) / 2, HEIGHT / 2);
            }
        } else if (state == 1) {
            sky.draw(g2d);
                        player.draw(g2d);
            for (Obstacle obs : obstacles) {
                obs.draw(g2d);
            }
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Score: " + (frames / 10), 20, 30);
        } else if (state == 2) {
            sky.draw(g2d);
                        player.draw(g2d);

            if (gameoverImg != null) {
                g2d.drawImage(gameoverImg, 0, 0, WIDTH, HEIGHT, null);

                int boxWidth = 150;
                int boxHeight = 40;
                int boxX = (WIDTH - boxWidth) / 2;
                int boxY = 92; 
                g2d.setColor(new Color(246, 196, 213)); 
                g2d.fillRect(boxX, boxY, boxWidth, boxHeight);

                String scoreText = String.format("%05d", (frames / 10));
                g2d.setColor(new Color(110, 31, 51));
                g2d.setFont(new Font("Monospaced", Font.BOLD, 32));
                FontMetrics metrics = g2d.getFontMetrics();
                int textX = (WIDTH - metrics.stringWidth(scoreText)) / 2;
                int textY = 125; 
                g2d.drawString(scoreText, textX, textY);
            } else {
                for (Obstacle obs : obstacles) {
                    obs.draw(g2d);
                }
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                FontMetrics metrics = g2d.getFontMetrics();
                String overText = "GAME OVER";
                g2d.drawString(overText, (WIDTH - metrics.stringWidth(overText)) / 2, HEIGHT / 2 - 20);

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics metrics2 = g2d.getFontMetrics();
                String resText = "Press SPACE to Restart";
                g2d.drawString(resText, (WIDTH - metrics2.stringWidth(resText)) / 2, HEIGHT / 2 + 20);
                String scoreText = "Score: " + (frames / 10);
                g2d.drawString(scoreText, (WIDTH - metrics2.stringWidth(scoreText)) / 2, HEIGHT / 2 + 50);
            }
        }
    }
}
