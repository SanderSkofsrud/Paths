package edu.ntnu.idatt2001.paths.utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {
  public void play(String filename) {
    try (InputStream is = new BufferedInputStream(getClass().getResourceAsStream(filename))) {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void playOnLoop(String filename) {
    try (InputStream is = new BufferedInputStream(getClass().getResourceAsStream(filename))) {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-12.5f);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
