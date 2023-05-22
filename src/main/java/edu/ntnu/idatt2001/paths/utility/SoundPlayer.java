package edu.ntnu.idatt2001.paths.utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;


/**
 * The type Sound player.
 * The SoundPlayer class is used to play sound files.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class SoundPlayer {
  private Clip clip;

  /**
   * A method that plays a sound file by taking in a filename.
   * It tries to play the sound file and catches an exception if it fails.
   *
   * @param filename the filename of the sound file
   */
  public static void play(String filename) {
    try (InputStream is = new BufferedInputStream(
            SoundPlayer.class.getResourceAsStream(filename))) {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
      Clip playClip = AudioSystem.getClip();
      playClip.open(audioInputStream);
      playClip.start();
    } catch (Exception e) {
      throw new RuntimeException("Could not play sound file");
    }
  }

  /**
   * A method that plays a sound file on loop.
   * It tries to play the sound file and catches an exception if it fails.
   *
   * @param filename the filename of the sound file
   */
  public void playOnLoop(String filename) {
    try (InputStream is = new BufferedInputStream(getClass().getResourceAsStream(filename))) {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-12.5f);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      throw new RuntimeException("Could not play sound file");
    }
  }

  /**
   * A method that stops the sound file if one is currently playing.
   */
  public void stop() {
    if (clip != null && clip.isRunning()) {
      clip.flush();
      clip.stop();
    }
  }

}
