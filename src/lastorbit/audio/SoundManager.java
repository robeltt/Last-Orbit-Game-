package lastorbit.audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {

    private Clip musicClip;

    
    public void playMusicLoop(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("MUSIC: loading " + file.getAbsolutePath());
                return;   
            }
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            musicClip = AudioSystem.getClip();
            musicClip.open(stream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);   
            musicClip.start();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("MUSIC: unsupported format (is it really a WAV? MP3 won't work): " + e.getMessage());
        } catch (IOException e) {
            System.out.println("MUSIC: couldn't read file: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("MUSIC: no audio line available: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    public void setVolume(float level) {
        if (musicClip == null) {
            return;
        }
        try {
            FloatControl gain = (FloatControl)
                musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gain.getMinimum();
            float max = gain.getMaximum();
            float db = min + (max - min) * level;
            gain.setValue(db);
        } catch (IllegalArgumentException e) {
        }
    }
}