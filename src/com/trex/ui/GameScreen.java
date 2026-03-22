package com.trex.ui;

import com.trex.entities.Character;
import com.trex.entities.Obstacle;
import com.trex.entities.Rabbit;
import com.trex.core.AudioPlayer;
import com.trex.entities.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/*****************************************************************************
 *                                                                           *
 * CLASS: GameScreen                                                         *
 * DESCRIPTION: Main game logic, rendering, UI, and state management.        *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Inheritance: Extends JPanel for GUI rendering.                          *
 * - Interface: Implements Runnable for the game loop thread.                *
 * - Association: Composes Character, Obstacles, and Backgrounds.            *
 *                                                                           *
 ****************************************************************************/
public class GameScreen extends JPanel implements Runnable {
    private final int WIDTH = 900; 
    private final int HEIGHT = 500; 

    private Thread gameThread;
    private boolean isRunning = false;

    private Character player;
    private List<Obstacle> obstacles;
    private Background sky;
    private Background base;
    private AudioPlayer audioPlayer;

    private int state = 0; 
    private int score = 0;
    private int frames = 0;
    private int nextSpawnFrame = 100;

    private Image charImg, rabImg, skyImg, baseImg, startImg, gameoverImg;

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
                        audioPlayer.playBGM("src/sound/gamesound.wav");
                    } else if (state == 1) {
                        player.jump(15.5); 
                        audioPlayer.playSFX("src/sound/jump.wav");
                    } else if (state == 2) {
                        initGame();
                        state = 1;
                        audioPlayer.playBGM("src/sound/gamesound.wav");
                    }
                }
            }
        });

        initMenu();
    }

    private void loadImages() {
        
        charImg = new ImageIcon("src/img/char.png").getImage();
        rabImg = new ImageIcon("src/img/Rab.png").getImage();
        skyImg = new ImageIcon("src/img/sky.png").getImage();
        baseImg = new ImageIcon("src/img/base.png").getImage();
        startImg = new ImageIcon("src/img/startgame.png").getImage();
        gameoverImg = new ImageIcon("src/img/gameover.png").getImage();
    }

    private void initMenu() {
        sky = new Background(0, 0, WIDTH, HEIGHT, skyImg, 0, WIDTH);
        
        base = new Background(0, HEIGHT - 236, WIDTH, 236, baseImg, 0, WIDTH);

        player = new Character(60, 420 - 110, 110, 110, charImg);
        obstacles = new ArrayList<>();
    }

    private void initGame() {
        sky = new Background(0, 0, WIDTH, HEIGHT, skyImg, 1.0, WIDTH);
        base = new Background(0, HEIGHT - 236, WIDTH, 236, baseImg, 4.0, WIDTH);
        player = new Character(60, 420 - 110, 110, 110, charImg);
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
        if (score >= 500) {
            speedBonus = 3.0;
        } else if (score >= 400) {
            speedBonus = 2.0;
        } else if (score >= 300) {
            speedBonus = 1.0;
        }

        sky.setScrollSpeed(1.0 + (speedBonus * 0.25));
        base.setScrollSpeed(4.0 + speedBonus);

        sky.update();
        base.update();
        player.update();

        if (frames >= nextSpawnFrame && frames > 0) {

            int minSpawn = Math.max(40, 80 - (int) (speedBonus * 10));
            int maxSpawn = Math.max(80, 160 - (int) (speedBonus * 20));
            obstacles.add(new Rabbit(WIDTH, 420 - 95, 95, 95, rabImg, 5.0 + speedBonus + (frames / 1000.0)));
            nextSpawnFrame = frames + java.util.concurrent.ThreadLocalRandom.current().nextInt(minSpawn, maxSpawn);
        }

        List<Obstacle> toRemove = new ArrayList<>();
        for (Obstacle obs : obstacles) {
            obs.setSpeed(5.0 + speedBonus + (frames / 1000.0));
            obs.update();
            if (obs.getX() + obs.getWidth() < 0) {
                toRemove.add(obs);
            }
        }
        
        for (Obstacle obs : obstacles) {
            if (player.isColliding(obs)) {
                state = 2; 
                audioPlayer.stopBGM();
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
            base.draw(g2d);
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
            base.draw(g2d);
            player.draw(g2d);
            for (Obstacle obs : obstacles) {
                obs.draw(g2d);
            }
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Score: " + (frames / 10), 20, 30);
        } else if (state == 2) {
            sky.draw(g2d);
            base.draw(g2d);
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
