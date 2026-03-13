package com.trex.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private Clip bgmClip;
    private Clip sfxClip;

    public AudioPlayer() {
        // Initialize
    }

    public void playBGM(String filePath) {
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
            }
            File audioFile = new File(filePath);
            if (!audioFile.exists() && filePath.startsWith("src/")) {
                audioFile = new File(filePath.substring(4));
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // BGM should loop
            bgmClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    public void playSFX(String filePath) {
        try {
            // Re-use or create new clip for overlapping sounds. New clip ensures rapid plays work.
            File audioFile = new File(filePath);
            if (!audioFile.exists() && filePath.startsWith("src/")) {
                audioFile = new File(filePath.substring(4));
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(audioStream);
            sfxClip.start(); // Fire and forget
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
