package MineSweeper_Runnable;

import java.awt.Font;
import javax.swing.ImageIcon;
import resources.ResourceLoader;

public class Cell extends MyJButton {
    public static int currentRows = 10;
    public static int currentCols = 10;
    public static int numMines = 10;
    public static String currentDifficulty = "Easy";
    public static Font Font_Numbers = new Font("Monospaced", 1, 20);
    private int neighbourCounter = 0;
    private boolean hasMines;
    private boolean hasFlags;
    private boolean isRevealed;
    private boolean isDoubleClicked = false;
    private String grassHorrorTheme = "8 bit EggHorrorTheme.jpg";
    private String grassDarkTheme = "8 bit ShrubDarkTheme.jpg";
    private String emptyGrassHorrorTheme = "EmptyHorrorTheme.JPG";
    private String emptyGrassDarkTheme = "8 bit ShrubEmptyDarkTheme.jpg";
    private String[] grassNumHorrorTheme = new String[]{"OneHorrorTheme.JPG", "TwoHorrorTheme.JPG", "ThreeHorrorTheme.JPG", "FourHorrorTheme.JPG", "FiveHorrorTheme.JPG", "SixHorrorTheme.JPG", "SevenHorrorTheme.JPG", "EightHorrorTheme.JPG"};
    private String[] grassNumDarkTheme = new String[]{"OneDarkTheme.JPG", "TwoDarkTheme.JPG", "ThreeDarkTheme.JPG", "FourDarkTheme.JPG", "FiveDarkTheme.JPG", "SixDarkTheme.JPG", "SevenDarkTheme.JPG", "EightDarkTheme.JPG"};
    private String flagHorrorTheme = "8 bit FlagHorrorTheme.jpg";
    private String flagDarkTheme = "8 bit FlagDarkTheme.jpg";
    private String explodeHorrorTheme = "8 bit ExplodeHorrorTheme.JPG";
    private String explodeDarkTheme = "8 bit ExplodeDarkTheme.jpg";
    private String minesHorrorTheme = "AlienMinesHorrorTheme.jpg";
    private String minesDarkTheme = "8 bit MinesDarkTheme.jpg";

    public Cell(boolean hasMines, boolean hasFlags, boolean isRevealed) {
        this.applyTheme();
        this.hasMines = hasMines;
        this.hasFlags = hasFlags;
        this.isRevealed = isRevealed;
    }

    public void initCell() {
        this.setEnabled(true);
        this.setFont(Font_Numbers);
        this.setFocusPainted(false);
        this.setMines(false);
        this.setFlags(false);
        this.setRevealed(false);
        this.neighbourCounter = 0;
        this.applyTheme();
    }

    public void resetCell() {
        this.setEnabled(true);
        this.setFlags(false);
        this.setRolloverEnabled(true);
        this.setFont(Font_Numbers);
        this.setRevealed(false);
        this.applyTheme();
    }

    public void applyTheme() {
        if (SoundEffects.theme.equals("Horror")) {
            if (this.hasFlags()) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.flagHorrorTheme)));
            } else if (this.isRevealed() && this.isEmpty()) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.emptyGrassHorrorTheme)));
            } else if (this.isRevealed() && this.neighbourCounter != 0) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.grassNumHorrorTheme[this.neighbourCounter - 1])));
            } else if (!this.isRevealed()) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.grassHorrorTheme)));
            }
        } else if (SoundEffects.theme.equals("Dark")) {
            if (this.hasFlags()) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.flagDarkTheme)));
            } else if (this.isRevealed() && this.isEmpty()) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.emptyGrassDarkTheme)));
            } else if (this.isRevealed() && this.neighbourCounter != 0) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.grassNumDarkTheme[this.neighbourCounter - 1])));
            } else if (!this.isRevealed()) {
                this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.grassDarkTheme)));
            }
        }

    }

    public void showThemedMines() {
        if (SoundEffects.theme.equals("Horror")) {
            this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.minesHorrorTheme)));
        } else if (SoundEffects.theme.equals("Dark")) {
            this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.minesDarkTheme)));
        }

    }

    public void showThemedExplodingMines() {
        if (SoundEffects.theme.equals("Horror")) {
            this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.explodeHorrorTheme)));
        } else if (SoundEffects.theme.equals("Dark")) {
            this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.explodeDarkTheme)));
        }

    }

    public static void resetNumMines() {
        if (currentDifficulty.equals("Easy")) {
            numMines = 10;
        } else if (currentDifficulty.equals("Intermediate")) {
            numMines = 40;
        } else if (currentDifficulty.equals("Hard")) {
            numMines = 99;
        }

    }

    public int getNeighbourCounter() {
        return this.neighbourCounter;
    }

    public void setNeighbourCounter(int neighbourCounter) {
        this.neighbourCounter = neighbourCounter;
    }

    public boolean hasMines() {
        return this.hasMines;
    }

    public void setMines(boolean hasMines) {
        this.hasMines = hasMines;
    }

    public boolean hasFlags() {
        return this.hasFlags;
    }

    public void setFlags(boolean hasFlags) {
        this.hasFlags = hasFlags;
    }

    public void incrementNeighbourCounter() {
        ++this.neighbourCounter;
    }

    public boolean isRevealed() {
        return this.isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }

    public void revealCell() {
        this.isRevealed = true;
        if (!this.hasMines()) {
            this.applyTheme();
        } else if (this.isDoubleClicked) {
            this.showThemedExplodingMines();
        } else {
            this.showThemedMines();
        }

        this.setEnabled(false);
    }

    public boolean isDoubleClicked() {
        return this.isDoubleClicked;
    }

    public void setDoubleClicked(boolean isDoubleClicked) {
        this.isDoubleClicked = isDoubleClicked;
    }

    public void plantFlag() {
        if (SoundEffects.theme.equals("Horror")) {
            this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.flagHorrorTheme)));
        } else if (SoundEffects.theme.equals("Dark")) {
            this.setImageIcon(new ImageIcon(ResourceLoader.getImage(this.flagDarkTheme)));
        }

        this.setRolloverEnabled(false);
        this.setFlags(true);
    }

    public void removeFlag() {
        this.setRolloverEnabled(true);
        this.setFlags(false);
        this.applyTheme();
    }

    public boolean isEmpty() {
        return this.neighbourCounter == 0 && this.isRevealed() && !this.hasMines();
    }
}
