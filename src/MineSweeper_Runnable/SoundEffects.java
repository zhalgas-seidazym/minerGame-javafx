package MineSweeper_Runnable;

import java.io.*;
import javax.sound.sampled.*;

import resources.ResourceLoader;
   
// To play sound using Clip, the process need to be alive.
public enum SoundEffects {
	DARKTHEME_BG("DarkThemeBackGroundMusic.wav"),
	HORRORTHEME_BG("HorrorThemeBackGroundMusic.wav"),
	WIN_GAME("GameWin.wav"),
	LOSE_GAME("GameOver.wav"),
	EXPLODE("MineExplode.wav"),
	PLANT_FLAG("PlantingFlag.wav"),
	REVEAL_DOUBLECLICK("RevealingButtonSoundEffect.wav");
	
	public static enum Volume{
		MUTE, LOW, MEDIUM, HIGH
	}
	
	public static Volume master_Volume = Volume.MEDIUM;
	private Volume volume;
		
	// Each sound file has its own clip, loaded with its own sound file
	private Clip clip;
	
	public static final float LOW_VOLUME = -30.0f;
	public static final float MEDIUM_VOLUME = -20.0f;
	public static final float HIGH_VOLUME = -10.0f;
	public static String theme = "Dark";
	FloatControl gainControl;
	
	// Constructor to construct each element of the enum with its own sound file
	SoundEffects(String soundFileName){
		try {
			// set up an audio input stream piped from the sound file.
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(ResourceLoader.getAudio(soundFileName));
			// get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream
			clip.open(audioIn);
			// Refresh the volume according to current volume settings
			//volume = Volume.MEDIUM;
			gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			setVolume(Volume.MEDIUM);
			
		}catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// stop the player if it is still running
	public void stopPlayingIfRunning() {
		if(clip.isRunning()) {
			clip.stop();	// Stop the player if it is still running
		}
	}
	
	// Play or Re-play the sound effect from the beginning, by rewinding.
	public void playOnce() {
		if(volume!=Volume.MUTE) {
			stopPlayingIfRunning(); 	// stop the player if it is still running
			clip.setFramePosition(0);	// rewind to the beginning
			clip.start();				// Start playing
		}
	}
	
	public void playLoop(int numLoop) {
		if(volume!=Volume.MUTE) {
			stopPlayingIfRunning(); 	// stop the player if it is still running
			clip.setFramePosition(0);	// rewind to the beginning
			clip.loop(numLoop-1);		// loop the clip numLoop times
		}
	}
	
	public void playEndlessLoop() {
		if(volume!=Volume.MUTE) {
			stopPlayingIfRunning(); 	// stop the player if it is still running
			clip.setFramePosition(0);	// rewind to the beginning
			clip.loop(Clip.LOOP_CONTINUOUSLY);				// Endless Loop
		}

	}
	
	public Volume getVolume() {
		return volume;
	}
	
	public void setVolume(Volume volume) {
		this.volume = volume;
		switch(volume) {
		case LOW:
			gainControl.setValue(LOW_VOLUME);
			break;
		case MEDIUM:
			gainControl.setValue(MEDIUM_VOLUME);
			break;
		case HIGH:
			gainControl.setValue(HIGH_VOLUME);
			break;
		default:
			return;
		}
	}
	
	public void setCurrentVolume() {
		switch(volume) {
		case LOW:
			gainControl.setValue(LOW_VOLUME);
			break;
		case MEDIUM:
			gainControl.setValue(MEDIUM_VOLUME);
			break;
		case HIGH:
			gainControl.setValue(HIGH_VOLUME);
			break;
		default:
			return;
		}
	}
	
	public void volumeUp() {
		switch(volume) {
		case LOW:
			setVolume(Volume.MEDIUM);
			break;
		case MEDIUM:
			setVolume(Volume.HIGH);
		default:
			return;
		}
	}
	
	public void volumeDown() {
		switch(volume) {
		case MEDIUM:
			setVolume(Volume.LOW);
			break;
		case HIGH:
			setVolume(Volume.MEDIUM);
		default:
			return;
		}		
	}
	
	public void muteVolume() {
		clip.stop();
	}
	
	public void unmuteVolume() {
		clip.start();
	}
	
	public void stopPlaying() {
		clip.stop();
	}
	
	// Optional static method to pre-load all the sound files.
	static void init() {
		values();	// calls the constructor for all the elements
	}
}