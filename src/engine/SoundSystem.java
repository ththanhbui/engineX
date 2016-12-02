package engine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Sounds are loaded using loadSound, and are stored in a hash map.
 */

public class SoundSystem {
	private static HashMap<String, Clip> mSoundBank = new HashMap<>();
	
	public static void loadSound(String filename, String identifier) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			mSoundBank.put(identifier, clip);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	public static void playSound(String identifier) {
		mSoundBank.get(identifier).start();
	}
	
	public static void stopSound(String identifier) {
		mSoundBank.get(identifier).stop();
	}
	
	public static boolean isPlaying(String identifier) {
		return mSoundBank.get(identifier).isActive();
	}
}