package MineSweeper_Runnable;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

import resources.ResourceLoader;

public class EasterEggs {
	public static int creditsCounter = 0;
	public static String easterEggCredits = "<html>We're grateful for your support for the team behind<br/> this game "
			+ "and would like to present to you this Easter Egg!<html>";
	private static String easterEggCreditsGif = "BabyGrootEasterEgg.gif";
	
	public static int lostCounter = 0;
	public static String easterEggLost = "<html>It seems that you have some trouble playing the game!<br/>"
			+ "Let me help you!<html>";
	public static String easterEggLostGif = "OneMoreChanceEasterEgg.gif";
	public static boolean takeEasterEggChance = false;
	
	public static int winCounter = 0;
	public static String easterEggWinGif = "AliensEasterEgg.gif";
	public static boolean easterEggWin = false;
	
	public static void applyEasterEggCredits() {
		JLabel label = new JLabel(easterEggCredits);
		label.setFont(new Font("Monospaced", Font.BOLD, 13));
		JOptionPane.showMessageDialog(null, label, "Easter egg 1", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ResourceLoader.getImage(easterEggCreditsGif)));
	}
	
	public static int applyEasterEggLose() {
		JLabel label = new JLabel(easterEggLost);
		label.setFont(new Font("Monospaced", Font.BOLD, 13));
		Object[] options = {"Yes Please!", "No Need, I'm an independent kid who needs no help"};
		return JOptionPane.showOptionDialog(null,
		        label,"GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
		        new ImageIcon(ResourceLoader.getImage(easterEggLostGif)), options, null);
	}
	
	public static int applyEasterEggWin() {
		Object[] options = {"Close", "Restart"};
		return JOptionPane.showOptionDialog(null,
		        null, "Congratulations! You've won and deserve a treat. Enjoy.",
		        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
		        new ImageIcon(ResourceLoader.getImage(easterEggWinGif)), options, null);		
	}
}
