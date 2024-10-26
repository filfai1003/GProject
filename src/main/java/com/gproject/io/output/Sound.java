package com.gproject.io.output;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gproject.menu.Settings.*;

public class Sound {
    private static final Map<String, Clip> musicClips = new HashMap<>();
    private static final Map<String, Clip> soundClips = new HashMap<>();

    public static void load(String filepath) {
        if (!musicClips.containsKey(filepath) && !soundClips.containsKey(filepath)) {
            try {
                File soundFile = new File(filepath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                musicClips.put(filepath, clip); // Precarica solo in musicClips per un uso generale
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public static void playMusic(String filepath) {
        load(filepath);
        Clip clip = musicClips.get(filepath);
        if (clip != null) {
            setVolume(clip, masterAudioLevel * musicAudioLevel);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    public static void playSound(String filepath) {
        try {
            File soundFile = new File(filepath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            setVolume(clip, masterAudioLevel * effectAudioLevel);
            clip.start();
            soundClips.put(filepath, clip);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    soundClips.remove(filepath);
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void setVolume(Clip clip, float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public static void setMusicVolume() {
        for (Clip clip : musicClips.values()) {
            setVolume(clip, masterAudioLevel*musicAudioLevel);
        }
    }
    public static void setEffectsVolume() {
        for (Clip clip : soundClips.values()) {
            setVolume(clip, masterAudioLevel*effectAudioLevel);
        }
    }
}
