package MineSweeper_Runnable;

import resources.ResourceLoader;
import java.awt.Font;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
// use self created class MyJLabel that extends JLabel to overwrite the paint method
public class Cell extends MyJButton{
	public static int currentRows = 10;
	public static int currentCols = 10;
	public static int numMines = 10;
	public static String currentDifficulty = "Easy";
	public static Font Font_Numbers = new Font("Monospaced", Font.BOLD, 20);

	private int neighbourCounter = 0;
	private boolean hasMines;
	private boolean hasFlags;
	private boolean isRevealed;
	private boolean isDoubleClicked = false;

	private String grassHorrorTheme = "8 bit EggHorrorTheme.jpg",
					grassDarkTheme = "8 bit ShrubDarkTheme.jpg",
					emptyGrassHorrorTheme = "EmptyHorrorTheme.JPG",
					emptyGrassDarkTheme = "8 bit ShrubEmptyDarkTheme.jpg";
	private String[] grassNumHorrorTheme = {"OneHorrorTheme.JPG", "TwoHorrorTheme.JPG", "ThreeHorrorTheme.JPG", "FourHorrorTheme.JPG",
									"FiveHorrorTheme.JPG", "SixHorrorTheme.JPG", "SevenHorrorTheme.JPG", "EightHorrorTheme.JPG"};
	private String[] grassNumDarkTheme = {"OneDarkTheme.JPG", "TwoDarkTheme.JPG", "ThreeDarkTheme.JPG", "FourDarkTheme.JPG",
			"FiveDarkTheme.JPG", "SixDarkTheme.JPG", "SevenDarkTheme.JPG", "EightDarkTheme.JPG"};
	private String flagHorrorTheme = "8 bit FlagHorrorTheme.jpg", 
					flagDarkTheme = "8 bit FlagDarkTheme.jpg";
	private String explodeHorrorTheme = "8 bit ExplodeHorrorTheme.JPG",
					explodeDarkTheme = "8 bit ExplodeDarkTheme.jpg",
					minesHorrorTheme = "AlienMinesHorrorTheme.jpg",
					minesDarkTheme = "8 bit MinesDarkTheme.jpg";
	
	public Cell(boolean hasMines, boolean hasFlags, boolean isRevealed) {
		super();
		applyTheme();
		this.hasMines = hasMines;
		this.hasFlags = hasFlags;
		this.isRevealed = isRevealed;
	}
	
	public void initCell() {
        this.setEnabled(true);  // enable button
        this.setFont(Font_Numbers);
        this.setFocusPainted(false);
        this.setMines(false);	// clear all the mines
        this.setFlags(false);	// clear all the flags
        this.setRevealed(false);
        neighbourCounter = 0;
		applyTheme();
	}
	
	public void resetCell() {
        this.setEnabled(true);  // enable button
        this.setFlags(false);
        this.setRolloverEnabled(true);
        this.setFont(Font_Numbers);
        this.setRevealed(false);
        applyTheme();
    }
	
	public void applyTheme() {
		if(SoundEffects.theme.equals("Horror")) {
			if(this.hasFlags()) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(flagHorrorTheme)));
			}
			else if(this.isRevealed() && this.isEmpty()) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(emptyGrassHorrorTheme)));
			}
			else if(this.isRevealed() && neighbourCounter!=0) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(grassNumHorrorTheme[neighbourCounter-1])));
			}
			else if(!this.isRevealed()) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(grassHorrorTheme)));
			}
		}
		else if(SoundEffects.theme.equals("Dark")) {
			if(this.hasFlags()) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(flagDarkTheme)));
			}
			else if(this.isRevealed() && this.isEmpty()) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(emptyGrassDarkTheme)));
			}
			else if(this.isRevealed() && neighbourCounter!=0) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(grassNumDarkTheme[neighbourCounter-1])));
			}
			else if(!this.isRevealed()) {
				this.setImageIcon(new ImageIcon(ResourceLoader.getImage(grassDarkTheme)));
			}
		}
	}
	
	public void showThemedMines() {
		if(SoundEffects.theme.equals("Horror")) {
			this.setImageIcon(new ImageIcon(ResourceLoader.getImage(minesHorrorTheme)));
		}
		else if(SoundEffects.theme.equals("Dark")) {
			this.setImageIcon(new ImageIcon(ResourceLoader.getImage(minesDarkTheme)));
		}
	}
	
	public void showThemedExplodingMines() {
		if(SoundEffects.theme.equals("Horror")) {
			this.setImageIcon(new ImageIcon(ResourceLoader.getImage(explodeHorrorTheme)));
		}
		else if(SoundEffects.theme.equals("Dark")) {
			this.setImageIcon(new ImageIcon(ResourceLoader.getImage(explodeDarkTheme)));
		}
	}
	
	
	public static void resetNumMines() {
		if(currentDifficulty.equals("Easy")) {
			numMines = 10;
		}
		else if(currentDifficulty.equals("Intermediate")) {
			numMines = 40;
		}
		else if(currentDifficulty.equals("Hard")) {
			numMines = 99;
		}
	}
	
	public int getNeighbourCounter() {
		return neighbourCounter;
	}

	public void setNeighbourCounter(int neighbourCounter) {
		this.neighbourCounter = neighbourCounter;
	}

	public boolean hasMines() {
		return hasMines;
	}

	public void setMines(boolean hasMines) {
		this.hasMines = hasMines;
	}

	public boolean hasFlags() {
		return hasFlags;
	}

	public void setFlags(boolean hasFlags) {
		this.hasFlags = hasFlags;
	}

	public void incrementNeighbourCounter() {
		neighbourCounter++;
	}


	public boolean isRevealed() {
		return isRevealed;
	}


	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}
	
	
	public void revealCell() {
		isRevealed = true;
		if(!hasMines()) {
			applyTheme();
		}
		else {
			if(isDoubleClicked) {
				showThemedExplodingMines();
			}
			else {
				showThemedMines();
			}
		}
		this.setEnabled(false);
	}
	
	public boolean isDoubleClicked() {
		return isDoubleClicked;
	}

	public void setDoubleClicked(boolean isDoubleClicked) {
		this.isDoubleClicked = isDoubleClicked;
	}

	public void plantFlag() {
		if(SoundEffects.theme.equals("Horror")) {
			this.setImageIcon(new ImageIcon(ResourceLoader.getImage(flagHorrorTheme)));
		}
		else if(SoundEffects.theme.equals("Dark")) {
			this.setImageIcon(new ImageIcon(ResourceLoader.getImage(flagDarkTheme)));
		}
		 this.setRolloverEnabled(false);
		 this.setFlags(true);
	}
	
	public void removeFlag() {
		this.setRolloverEnabled(true);
		this.setFlags(false);
		applyTheme();
	}
	
	
	public boolean isEmpty() {
		return neighbourCounter == 0 && isRevealed() && !hasMines();
	}
}
