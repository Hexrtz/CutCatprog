package com.trex.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/***********************************************************************************************
 *                                                                                             *
 *  CLASS: AudioPlayer                                                                         *
 *  DESCRIPTION: จัดการระบบเสียงเพลงเปิดคลอ (BGM) และเสียงเอฟเฟกต์ (SFX)                            *
 *                                                                                             *
 *  OOP CONCEPTS APPLIED:                                                                      *
 *  - Encapsulation: ซ่อนอินสแตนซ์ของ Clip ไว้ภายในด้วย private modifier                            *
 *                                                                                             *
 **********************************************************************************************/
public class AudioPlayer {
    private Clip bgmClip;
    private Clip sfxClip;

    public AudioPlayer() {
        
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
            
            // ลดเสียง BGM ลงอีกเยอะๆ ให้เบาลงจากเดิมมาก (ประมาณ -15.0 เดซิเบล)
            if (bgmClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f); 
            }
            
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); 
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
            
            File audioFile = new File(filePath);
            if (!audioFile.exists() && filePath.startsWith("src/")) {
                audioFile = new File(filePath.substring(4));
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(audioStream);

            if (filePath.contains("jump.wav")) {
                FloatControl gainControl = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-50.0f);
            }
            
            sfxClip.start(); 
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
