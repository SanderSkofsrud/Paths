package edu.ntnu.idatt2001.paths.utility;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundPlayer {
  public void play(String filename) {
    try {
      File soundFile = new File(filename);
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(soundFile));
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void playOnLoop(String filename) {
    try {
      File soundFile = new File(filename);
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(soundFile));
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-12.5f);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
