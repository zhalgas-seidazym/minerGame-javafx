package MineSweeper_Runnable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
//Use AWT's event handlers
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Use Swing's Containers and Components
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.Timer;

import resources.ResourceLoader;



/**
 * The Mine Sweeper Game.
 * Left-click to reveal a cell.
 * Right-click to plant/remove a flag for marking a suspected mine.
 * You win if all the cells not containing mines are revealed.
 * You lose if you reveal a cell containing a mine.
 */
@SuppressWarnings("serial")
public class MineSweeper extends JFrame{
	//Name-constants for the game properties
	public static final int ROWS_EASY = 10;	//number of cells
	public static final int COLS_EASY = 10;
	public static final int ROWS_INTERMEDIATE = 16;	//number of cells
	public static final int COLS_INTERMEDIATE = 16;
	public static final int ROWS_HARD = 16;	//number of cells
	public static final int COLS_HARD = 30;
	public static final int TIME_INTERVAL = 1000;
	public static final int UPDATES_PANEL_HEIGHT = 100;	
	public static final Dimension WINDOW_SIZE_EASY = new Dimension(530, 600);
	public static final Dimension WINDOW_SIZE_INTERMEDIATE = new Dimension(630, 630);
	public static final Dimension WINDOW_SIZE_HARD = new Dimension(1100, 630);
	public static final Dimension RESET_BUTTON_SIZE = new Dimension(70, 70);
	public static final Font TEXTFIELD_FONT = new Font("Monospaced", Font.BOLD, 30);
	
	JTextField tfNumMines, tfTimer;
	MyJButton resetButton;
	// declare Timer object to count time
	public Timer timeCounter;
	ActionListener taskPerformer; // action listener for the timer object
	int counter = 0;
	
	public static final int CELL_SIZE = 60;	//Cell width and height, in pixels
	public int canvasWidth = CELL_SIZE*Cell.currentCols;	//game board width
	public int canvasHeight = CELL_SIZE*Cell.currentRows;	//game board height
	
	JRadioButtonMenuItem lowVolumeMenuItem, mediumVolumeMenuItem, highVolumeMenuItem,
						horrorThemeMenuItem, darkThemeMenuItem;
	MyJLabel updatesPanel;
	
	// number of mines in this game. Can vary to control the difficulty level
	//int numMines = 10;
	
	// Allocate a common instance of listener as the MouseEvent listener
	// for all the JButtons
	CellMouseListener cellMouseListener = new CellMouseListener();
	
	// initialise array of Cell objects, each with boolean mines and flags value
	Cell gameCells[][];
	
	String headerBannerHorrorTheme = "HeaderBannerHorrorTheme.jpg",
			headerBannerDarkTheme = "HeaderBannerDarkTheme.jpg",
			resetButtonHorrorTheme = "8 bit HouseHorrorTheme.jpg",
			resetButtonDarkTheme = "8 bit HouseDarkTheme.jpeg";
	String instructions = "The grid is entirely filled with unknown squares. Some are empty while some contain Mines (or Explosives). The objective of the game is to reveal all the empty squares without detonating any of the mines.\r\n" + 
			"You WIN when you successfully clear the whole grid but once a mine goes off, its GAME OVER.\r\n" + 
			"When playing MineSweeper, there will be numbers in squares next to unrevealed square.  This  indicates how many mines are adjacent to it. If an empty square displays 2, that means there are two mines next to it.\r\n" + 
			"Left click if you are sure a square does not contain a mine. Conversely, if you think a square contains a mine, right click on that square in order to flag it.\r\n" + 
			"Happy Mining!";
	String credits = "This game is jointly created by:\n" +
						"Cheng Kuan Yong, Jason (U1721902D) and\n" +
						"Ng Ye Dong (U1720848H)! \n" + 
						"Thank you for playing our game!";
	String creditsGif = "CreditsRobots.gif",
			creditsHorrorGif = "CreditsHorrorTheme.gif";
	String gameOverGif = "GameOverGlitch.gif",
			gameWinGif = "GameWin.gif",
			gameWinHorrorThemeGif = "GameWinHorrorTheme.gif";
	
	
	// constructor to set up all the UI and game components
	public MineSweeper(int numRows, int numCols) {
		// initialise sound files
		SoundEffects.init();
		
		gameCells = new Cell[numRows][numCols];
		Container cp = getContentPane();	// JFrame's content-page
		cp.setLayout(new BorderLayout());	// in 10x10 GridLayout
		
		canvasWidth = CELL_SIZE*numCols;	//game board width
		canvasHeight = CELL_SIZE*numRows;	//game board height
		
		JPanel topPanel = new JPanel(new BorderLayout());
		
		// creating menus and menu items
		// add a common itemListener to each menu item
		MenuItemListener menuItemListener = new MenuItemListener();
		
		final JMenuBar menu = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
			JMenu subFileMenu = new JMenu("New");
				JMenuItem easyMenuItem = new JMenuItem("Easy");
				easyMenuItem.setActionCommand("Easy");
				easyMenuItem.addActionListener(menuItemListener);
				subFileMenu.add(easyMenuItem);
				JMenuItem intermediateMenuItem = new JMenuItem("Intermediate");
				intermediateMenuItem.setActionCommand("Intermediate");
				intermediateMenuItem.addActionListener(menuItemListener);
				subFileMenu.add(intermediateMenuItem);
				JMenuItem hardMenuItem = new JMenuItem("Hard");
				hardMenuItem.setActionCommand("Hard");
				hardMenuItem.addActionListener(menuItemListener);
				subFileMenu.add(hardMenuItem);
			fileMenu.add(subFileMenu);
				
			JMenuItem resetMenuItem = new JMenuItem("Reset Game");
			resetMenuItem.setActionCommand("Reset Game");
			resetMenuItem.setMnemonic(KeyEvent.VK_R);
			resetMenuItem.addActionListener(menuItemListener);
			fileMenu.add(resetMenuItem);
			
			JMenuItem exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.setActionCommand("Exit");
			exitMenuItem.addActionListener(menuItemListener);
			fileMenu.add(exitMenuItem);
		menu.add(fileMenu);
		
		JMenu optionMenu = new JMenu("Options");
			JMenu setVolumeMenu = new JMenu("Set Volume");
				lowVolumeMenuItem = new JRadioButtonMenuItem("Low");
				lowVolumeMenuItem.setActionCommand("Low Volume");
				lowVolumeMenuItem.addActionListener(menuItemListener);
				setVolumeMenu.add(lowVolumeMenuItem);
				mediumVolumeMenuItem = new JRadioButtonMenuItem("Medium");
				mediumVolumeMenuItem.setActionCommand("Medium Volume");
				mediumVolumeMenuItem.addActionListener(menuItemListener);
				setVolumeMenu.add(mediumVolumeMenuItem);
				highVolumeMenuItem = new JRadioButtonMenuItem("High");
				highVolumeMenuItem.setActionCommand("High Volume");
				highVolumeMenuItem.addActionListener(menuItemListener);
				setVolumeMenu.add(highVolumeMenuItem);
			optionMenu.add(setVolumeMenu);
			JMenu setThemeMenu = new JMenu("Set Theme");
				horrorThemeMenuItem = new JRadioButtonMenuItem("Horror Theme");
				horrorThemeMenuItem.setActionCommand("Horror Theme");
				horrorThemeMenuItem.addActionListener(menuItemListener);
				setThemeMenu.add(horrorThemeMenuItem);
				darkThemeMenuItem = new JRadioButtonMenuItem("Dark Theme");
				darkThemeMenuItem.setActionCommand("Dark Theme");
				darkThemeMenuItem.addActionListener(menuItemListener);
				setThemeMenu.add(darkThemeMenuItem);
			optionMenu.add(setThemeMenu);
			JMenuItem enableSoundMenuItem = new JMenuItem("Enable Sound");
			enableSoundMenuItem.setActionCommand("Enable Sound");
			enableSoundMenuItem.addActionListener(menuItemListener);
			optionMenu.add(enableSoundMenuItem);
			JMenuItem disableSoundMenuItem = new JMenuItem("Disable Sound");
			disableSoundMenuItem.setActionCommand("Disable Sound");
			disableSoundMenuItem.addActionListener(menuItemListener);
			optionMenu.add(disableSoundMenuItem);
		menu.add(optionMenu);
		
		JMenu helpMenu = new JMenu("Help");
			JMenuItem howToPlayMenuItem = new JMenuItem("How To Play");
			howToPlayMenuItem.setActionCommand("How To Play");
			howToPlayMenuItem.addActionListener(menuItemListener);
			helpMenu.add(howToPlayMenuItem);
			JMenuItem showCreditsMenuItem = new JMenuItem("Show Credits");
			showCreditsMenuItem.setActionCommand("Show Credits");
			showCreditsMenuItem.addActionListener(menuItemListener);
			helpMenu.add(showCreditsMenuItem);
		menu.add(helpMenu);

		// use self created class MyJLabel that extends JLabel to overwrite the paint method
		updatesPanel = new MyJLabel(new ImageIcon(ResourceLoader.getImage(headerBannerHorrorTheme)));
		updatesPanel.setLayout(new FlowLayout());
		updatesPanel.setPreferredSize(new Dimension(canvasWidth, UPDATES_PANEL_HEIGHT));
		tfNumMines = new JTextField("");
			tfNumMines.setFont(TEXTFIELD_FONT);
			tfNumMines.setHorizontalAlignment(JTextField.CENTER);
			tfNumMines.setEditable(false);
			updatesPanel.add(tfNumMines);
		resetButton = new MyJButton(new ImageIcon(ResourceLoader.getImage(resetButtonHorrorTheme)));
			resetButton.setBackground(Color.WHITE);
			resetButton.setFocusPainted(false);
			resetButton.setPreferredSize(RESET_BUTTON_SIZE);
			resetButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					resetGame();
				}
			});
			updatesPanel.add(resetButton);

		tfTimer = new JTextField("000");
			tfTimer.setFont(TEXTFIELD_FONT);
			tfTimer.setHorizontalAlignment(JTextField.CENTER);
			tfTimer.setEditable(false);
			updatesPanel.add(tfTimer);
		taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				counter++;
				tfTimer.setText(String.format("%03d", counter));
			}
		};
		timeCounter = new Timer(TIME_INTERVAL, taskPerformer);	
		
		JPanel panel = new JPanel(new GridLayout(numRows, numCols, 2, 2));
		
		// construct Cell array and add to the content-pane
		for(int row = 0; row<numRows; row++) {
			for(int col = 0; col<numCols; col++) {
				// initialise the Cell objects, with no flags and no mines in each cell
				gameCells[row][col] = new Cell(false, false, false);	//Allocate each JButton of the array
				panel.add(gameCells[row][col]);
				
				// add MouseEvent listener to process the left/right mouse click
				gameCells[row][col].addMouseListener(cellMouseListener);
			}
		}
		System.out.println(Cell.numMines + " " + Cell.currentRows + "x" + Cell.currentCols);
		//initialise for a new game
		initGame();
		
		// adding all JPanels into the content pane
		panel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
		topPanel.add(menu, BorderLayout.NORTH);
		topPanel.add(updatesPanel, BorderLayout.CENTER);
		cp.add(topPanel, BorderLayout.NORTH);
		cp.add(panel, BorderLayout.CENTER);
		adjustWindowSize(cp);
		pack();	//resize windows to fit the preferred size
		System.out.println("Cell size: " + gameCells[0][0].getHeight() + "x" + gameCells[0][0].getWidth());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MineSweeper");
		setVisible(true);
		
		// Set theme background music and images
		applyTheme();
		this.setIconImage(ResourceLoader.getImage("MineSweeperIcon.png"));
	}
	
	private void adjustWindowSize(Container cp) {
		if(Cell.currentDifficulty.equals("Easy")) {
			cp.setPreferredSize(WINDOW_SIZE_EASY);
		}
		else if(Cell.currentDifficulty.equals("Intermediate")) {
			cp.setPreferredSize(WINDOW_SIZE_INTERMEDIATE);
		}
		else if(Cell.currentDifficulty.equals("Hard")) {
			cp.setPreferredSize(WINDOW_SIZE_HARD);
		}
	}
	
	private void initGame() {
		// Reset cells, mines and flags
		for (int row=0; row<Cell.currentRows; row++) {
			for (int col=0; col<Cell.currentCols; col++) {
				// Set all cells to un-revealed
				gameCells[row][col].initCell();
			}
		}
		// Set the number of mines and the mines' location
		// randomise the mines
		// numMines=1; //FOR TESTING PURPOSES ONLY	
		if(EasterEggs.takeEasterEggChance) {
			gameCells[0][0].setMines(true);	//FOR TESTING PURPOSES ONLY
			EasterEggs.takeEasterEggChance = false;
		}
		else {
			for(int i=0; i<Cell.numMines; i++) {
				int row, col;
				do {
					row = (int)(Math.random()*Cell.currentRows);
					col = (int)(Math.random()*Cell.currentCols);
				}while(gameCells[row][col].hasMines());
				gameCells[row][col].setMines(true);
			}
			// gameCells[0][0].setMines(true);
		}
		tfNumMines.setText(String.format("%02d", Cell.numMines));
		applyVolumeSettings();
	    updateAllCellsNeighbourCounter();
	}
	
	// The entry main() method
	public static void main(String[] args) {
		new MineSweeper(ROWS_EASY, COLS_EASY);
	}
	
	// Define the action listener Inner Class
	private class MenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			// auto-generated method stub
			if(event.getActionCommand().equals("Reset Game")) {
				resetGame();
			}
			else if(event.getActionCommand().equals("Exit")) {
				System.exit(0);
			}
			else if(event.getActionCommand().equals("Easy")) {
				Cell.currentDifficulty = "Easy";
				MineSweeper.this.dispose();
				drawBoard();
			}
			else if(event.getActionCommand().equals("Intermediate")) {
				System.out.println("change difficulty to intermediate");
				Cell.currentDifficulty = "Intermediate";
				MineSweeper.this.dispose();
				drawBoard();
			}
			else if(event.getActionCommand().equals("Hard")) {
				System.out.println("change difficulty to hard");
				Cell.currentDifficulty = "Hard";
				MineSweeper.this.dispose();
				drawBoard();
			}
			else if(event.getActionCommand().equals("Low Volume")) {
				System.out.println("Setting volume to low");
				lowVolumeMenuItem.setSelected(true);
				mediumVolumeMenuItem.setSelected(false);
				highVolumeMenuItem.setSelected(false);
				SoundEffects.master_Volume = SoundEffects.Volume.LOW;
				for(SoundEffects s: SoundEffects.values()) {
					s.setVolume(SoundEffects.Volume.LOW);
				}
			}
			else if(event.getActionCommand().equals("Medium Volume")) {
				System.out.println("Setting volume to medium");
				lowVolumeMenuItem.setSelected(false);
				mediumVolumeMenuItem.setSelected(true);
				highVolumeMenuItem.setSelected(false);
				SoundEffects.master_Volume = SoundEffects.Volume.MEDIUM;
				for(SoundEffects s: SoundEffects.values()) {
					s.setVolume(SoundEffects.Volume.MEDIUM);
				}
			}
			else if(event.getActionCommand().equals("High Volume")) {
				System.out.println("Setting volume to high");
				lowVolumeMenuItem.setSelected(false);
				mediumVolumeMenuItem.setSelected(false);
				highVolumeMenuItem.setSelected(true);
				SoundEffects.master_Volume = SoundEffects.Volume.HIGH;
				for(SoundEffects s: SoundEffects.values()) {
					s.setVolume(SoundEffects.Volume.HIGH);
				}
			}
			else if(event.getActionCommand().equals("Horror Theme")) {
				SoundEffects.theme = "Horror";
				applyTheme();
			}
			else if(event.getActionCommand().equals("Dark Theme")) {
				SoundEffects.theme = "Dark";
				applyTheme();
			}
			else if(event.getActionCommand().equals("Enable Sound")) {
				for(SoundEffects s: SoundEffects.values()) {
					s.setCurrentVolume();
				}
				applyTheme();
			}
			else if(event.getActionCommand().equals("Disable Sound")) {
				stopAllMusic();
			}
			else if(event.getActionCommand().equals("How To Play")) {
				JOptionPane.showMessageDialog(null, instructions, "How To Play", JOptionPane.PLAIN_MESSAGE);
			}
			else if(event.getActionCommand().equals("Show Credits")) {
				EasterEggs.creditsCounter++;
				if(EasterEggs.creditsCounter == 3) {
					EasterEggs.applyEasterEggCredits();
				}
				else {
					ImageIcon creditsIcon;
					if(SoundEffects.theme.equals("Horror")) {
						creditsIcon = new ImageIcon(ResourceLoader.getImage(creditsHorrorGif));
					}
					else {
						creditsIcon = new ImageIcon(ResourceLoader.getImage(creditsGif));
					}
					JOptionPane.showMessageDialog(null, credits, "Show Credits", JOptionPane.INFORMATION_MESSAGE, creditsIcon);
				}
			}
		}
	}
	
	// Define the Listener Inner Class
	public class CellMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// Determine the (row, col) of the JButton that triggered the event
	         int rowSelected = -1;
	         int colSelected = -1;
	 
	         // Get the source object that fired the Event
	         JButton source = (JButton)e.getSource();
	         // Scan all currentRows and columns, and match with the source object
	         boolean found = false;
	         for (int row = 0; row < Cell.currentRows && !found; ++row) {
	            for (int col = 0; col < Cell.currentCols && !found; ++col) {
	               if (source == gameCells[row][col]) {
	                  rowSelected = row;
	                  colSelected = col;
	                  found = true;   // break both inner/outer loops
	               }
	            }
	         }
	         
	         
	         // Left-click to reveal a cell; Right-click to plant/remove the flag.
	         if (e.getButton() == MouseEvent.BUTTON1) {  // Left-button clicked
	        	 System.out.println("Screen size: " + getContentPane().getWidth() + "x" + getContentPane().getHeight());
	        	 // first click will never be a mine
	        	 if(isAllNotRevealed()) {
	        		 if(gameCells[rowSelected][colSelected].hasMines()) {
	        			 gameCells[rowSelected][colSelected].setMines(false);
	        			 putRandomMine();
	        			 updateAllCellsNeighbourCounter();
	        		 }
	        		 timeCounter.start();
	        	 }
	            // If you hit a mine, game over
	        	System.out.println("(" + rowSelected + "," + colSelected + ")");
	        	if(!gameCells[rowSelected][colSelected].isRevealed() && !gameCells[rowSelected][colSelected].hasFlags()) {
		        	if (gameCells[rowSelected][colSelected].hasMines()) {
		        			loseScreen(rowSelected, colSelected);
		        	}
		            // Otherwise, reveal the cell and display the number of surrounding mines
		        	else {
		        		gameCells[rowSelected][colSelected].revealCell();
		        		// if neighbourCounter is zero, check neighbouring cells value and reveal cells with zero neighbourCounter
		        		floodFillCells(rowSelected, colSelected);
		        	}
	        	}
	        	// add chording when double clicked on a revealed empty cell
	        	if(e.getClickCount() == 2) {
	        		System.out.println("DOUBLE CLICKED");
	        		gameCells[rowSelected][colSelected].setDoubleClicked(true);
	        		if(gameCells[rowSelected][colSelected].isRevealed()) {
	        			if(metChordRequirement(rowSelected, colSelected)) {
	        				revealNeighbourCells(rowSelected, colSelected);
	        				if(!gameCells[rowSelected][colSelected].isEmpty()) {
	        					SoundEffects.REVEAL_DOUBLECLICK.playOnce();
	        				}
	        			}
	        		}
	        		if(mineIsRevealed()) {
	        			loseScreen(rowSelected, colSelected);
	        		}
	        		
	        	}
	         }
	 
	         else if (e.getButton() == MouseEvent.BUTTON3) { // right-button clicked
	            // If the location is flagged, remove the flag
	        	 if(!gameCells[rowSelected][colSelected].isRevealed()) {
		        	 if(gameCells[rowSelected][colSelected].hasFlags()) {
		        		 gameCells[rowSelected][colSelected].removeFlag();
		        		 Cell.numMines++;
		        		 tfNumMines.setText(String.format("%02d", Cell.numMines));
		        		 SoundEffects.PLANT_FLAG.playOnce();
		        	 }
			         // Otherwise, plant a flag.
		        	 else {
		        		 gameCells[rowSelected][colSelected].plantFlag();
		        		 Cell.numMines--;
		        		 tfNumMines.setText(String.format("%02d", Cell.numMines));
		        		 SoundEffects.PLANT_FLAG.playOnce();
		        	 }
	        	 }
	         }
	         // Check if the player has won, after revealing this cell
	     	 boolean gameIsWon = true;
	         for(int row=0; row<Cell.currentRows; row++) {
	        	 for (int col=0; col<Cell.currentCols; col++) {
	        		 if(!gameCells[row][col].hasMines() && !gameCells[row][col].isRevealed()) {
	        			 gameIsWon = false;
	        		 }
	        	 }
	         }
	         
	         if(gameIsWon) {
	        	 winScreen();
	         }
		}
	}
	
	public void resetGame() {
		for(int row=0; row<Cell.currentRows; row++) {
			for(int col=0; col<Cell.currentCols; col++) {
				gameCells[row][col].resetCell();
			}
		}
		counter = 0;
		timeCounter.stop();
		tfTimer.setText("000");
		Cell.resetNumMines();
		tfNumMines.setText(String.format("%03d", Cell.numMines));

	}
	
	public void drawBoard() {
		if(Cell.currentDifficulty.equals("Easy")) {
			Cell.numMines = 10;
			Cell.currentCols = 10;
			Cell.currentRows = 10;
			Cell.Font_Numbers = new Font("Monospaced", Font.BOLD, 20);
			new MineSweeper(Cell.currentRows, Cell.currentCols);
		}
		else if(Cell.currentDifficulty.equals("Intermediate")) {
			Cell.numMines = 40;
			Cell.currentRows = 16;
			Cell.currentCols = 16;
			Cell.Font_Numbers = new Font("Monospaced", Font.BOLD, 15);
			new MineSweeper(Cell.currentRows, Cell.currentCols);
		}
		else if(Cell.currentDifficulty.equals("Hard")) {
			Cell.numMines = 99;
			Cell.currentRows = 16;
			Cell.currentCols = 30;
			Cell.Font_Numbers = new Font("Monospaced", Font.BOLD, 10);
			System.out.println("Cell.numMines: " + Cell.numMines + " currentRows:" + Cell.currentRows + " currentCols: " + Cell.currentCols);
			new MineSweeper(Cell.currentRows, Cell.currentCols);
		}
	}
	
	public boolean isAllNotRevealed() {
		for(int row = 0; row<Cell.currentRows; row++) {
			for(int col=0; col<Cell.currentCols; col++) {
				if(gameCells[row][col].isRevealed()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void putRandomMine() {
		int randRow, randCol;
		do {
			randRow = (int)(Math.random()*Cell.currentRows);
			randCol = (int)(Math.random()*Cell.currentCols);
		}while(gameCells[randRow][randCol].hasMines());
		gameCells[randRow][randCol].setMines(true);
	}
	
	public void winScreen() {
		// stops background music and set win music
		stopAllMusic();
		SoundEffects.WIN_GAME.playOnce();
		timeCounter.stop();
		
		EasterEggs.winCounter++;
		if(EasterEggs.winCounter%3 == 0) {
			int easterWinResult = EasterEggs.applyEasterEggWin();
			if(easterWinResult == JOptionPane.YES_OPTION) System.exit(0);
			else if(easterWinResult == JOptionPane.NO_OPTION) {
				MineSweeper.this.dispose();
				drawBoard();
				return;
			}
		}
		ImageIcon winImage;
		String winScreenMessage;
		if(SoundEffects.theme.equals("Horror")) {
			winImage = new ImageIcon(ResourceLoader.getImage(gameWinHorrorThemeGif));
			winScreenMessage = "Congratulations! You've survived the game!";
		}
		else {
			winImage = new ImageIcon(ResourceLoader.getImage(gameWinGif));
			winScreenMessage = "Congratulations! You've won the game!";
		}
		// Popup window after losing
		Object[] options = {"Close", "Restart"};
		JLabel label = new JLabel(winScreenMessage);
		label.setFont(new Font("Monospaced", Font.BOLD, 18));
		int result = JOptionPane.showOptionDialog(null,
		        label,
		        "YAY!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, winImage, options, null);
		if (result == JOptionPane.YES_OPTION) System.exit(0);
		else if(result == JOptionPane.NO_OPTION) {
			MineSweeper.this.dispose();
			drawBoard();
		}
	}
	
	public void loseScreen(int rowSelected, int colSelected) {
		// stops background music and set lose music
		stopAllMusic();
		SoundEffects.EXPLODE.playOnce();
		SoundEffects.LOSE_GAME.playOnce();
		timeCounter.stop();
		
		for(int row=0; row<Cell.currentRows; row++) {
			for(int col=0; col<Cell.currentCols; col++) {
				if(gameCells[row][col].hasMines() ) {
					gameCells[row][col].revealCell();
				}
			}
		}
		if(!gameCells[rowSelected][colSelected].isDoubleClicked()) {
			gameCells[rowSelected][colSelected].showThemedExplodingMines();
		}
		
		EasterEggs.lostCounter++;
		if(EasterEggs.lostCounter%3 == 0) {
			int easterLoseResult = EasterEggs.applyEasterEggLose();
			if(easterLoseResult == JOptionPane.YES_OPTION) {
				EasterEggs.takeEasterEggChance = true;
				MineSweeper.this.dispose();
				drawBoard();
				return;
			}
		}
		
		// Popup window after losing
		Object[] options = {"Quit", "Restart"};
		int result = JOptionPane.showOptionDialog(null,
		        null,"GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
		        new ImageIcon(ResourceLoader.getImage(gameOverGif)), options, null);
		if (result == JOptionPane.OK_OPTION) System.exit(0);
		else if(result == JOptionPane.NO_OPTION) {
			MineSweeper.this.dispose();
			drawBoard();
		}		
	}
	
	public boolean mineIsRevealed() {
		for(int row=0; row<Cell.currentRows; row++) {
			for(int col=0; col<Cell.currentCols; col++) {
				if(gameCells[row][col].isRevealed() && gameCells[row][col].hasMines()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isInGrid(int x, int y) {
		if(x<0 || y<0) return false;
		if(x >= Cell.currentRows || y >= Cell.currentCols) return false;
		return true;
	}
	
	public boolean metChordRequirement(int rowSelected, int colSelected) {
		int numSurroundingFlags = 0;
		for(int row=rowSelected-1; row<= rowSelected+1; row++) {
			for(int col=colSelected-1; col<=colSelected+1; col++) {
				if(isInGrid(row, col) && gameCells[row][col].hasFlags()) {
					numSurroundingFlags++;
				}
			}
		}
		System.out.println("Surrounding flags: " + numSurroundingFlags + " counter: " + gameCells[rowSelected][colSelected].getNeighbourCounter());
		if(numSurroundingFlags == gameCells[rowSelected][colSelected].getNeighbourCounter()) {
			return true;
		}
		return false;
	}

	public void revealNeighbourCells(int rowSelected, int colSelected) {
		// handling center cells
			for(int row=rowSelected-1; row<= rowSelected+1; row++) {
				for(int col=colSelected-1; col<=colSelected+1; col++) {
					if(isInGrid(row, col) && !gameCells[row][col].hasFlags()) {
						gameCells[row][col].revealCell();
						floodFillCells(row, col);
					}
				}
			}
			
	}
	
	public void floodFillCells(int rowSelected, int colSelected) {
		// handling center cells
		if(gameCells[rowSelected][colSelected].isEmpty()) {
			for(int row=rowSelected-1; row<= rowSelected+1; row++) {
				for(int col=colSelected-1; col<=colSelected+1; col++) {
					if(isInGrid(row, col) && !gameCells[row][col].isRevealed() && !gameCells[row][col].hasFlags()) {
						gameCells[row][col].revealCell();
						floodFillCells(row, col);
					}
				}
			}
		}		
	}
	
	
	public void updateNeighbourCounter(int rowSelected, int colSelected) {
		for(int targetRow=rowSelected-1; targetRow<= rowSelected+1; targetRow++) {
			for(int targetCol=colSelected-1; targetCol<=colSelected+1; targetCol++) {
				if(isInGrid(targetRow, targetCol) && gameCells[targetRow][targetCol].hasMines()) {
					gameCells[rowSelected][colSelected].incrementNeighbourCounter();
				}
			}
		}
	}
	
	public void updateAllCellsNeighbourCounter() {
		 for(int row=0; row<Cell.currentRows; row++) {
			 for(int col=0; col<Cell.currentCols; col++) {
				 gameCells[row][col].setNeighbourCounter(0);
			 }
		 }
		 for(int row=0; row<Cell.currentRows; row++) {
			 for(int col=0; col<Cell.currentCols; col++) {
				 updateNeighbourCounter(row, col);
			 }
		 }
	}
	
	public void setCellsTheme() {
		for(int row=0; row<Cell.currentRows; row++) {
			for(int col = 0; col<Cell.currentCols; col++) {
				gameCells[row][col].applyTheme();
			}
		}
	}
	
	public void applyTheme() {
		if(SoundEffects.theme.equals("Horror")) {
			SoundEffects.DARKTHEME_BG.stopPlaying();
			SoundEffects.HORRORTHEME_BG.playEndlessLoop();
			horrorThemeMenuItem.setSelected(true);
			darkThemeMenuItem.setSelected(false);
			resetButton.setImageIcon(new ImageIcon(ResourceLoader.getImage(resetButtonHorrorTheme)));
			updatesPanel.setImageIcon(new ImageIcon(ResourceLoader.getImage(headerBannerHorrorTheme)));
			tfTimer.setBackground(Color.BLACK);
			tfTimer.setForeground(Color.WHITE);
			tfNumMines.setBackground(Color.BLACK);
			tfNumMines.setForeground(Color.WHITE);
			setCellsTheme();
			
		}
		else if(SoundEffects.theme.equals("Dark")) {
			SoundEffects.HORRORTHEME_BG.stopPlaying();
			SoundEffects.DARKTHEME_BG.playEndlessLoop();
			horrorThemeMenuItem.setSelected(false);
			darkThemeMenuItem.setSelected(true);
			resetButton.setImageIcon(new ImageIcon(ResourceLoader.getImage(resetButtonDarkTheme)));
			updatesPanel.setImageIcon(new ImageIcon(ResourceLoader.getImage(headerBannerDarkTheme)));
			tfTimer.setBackground(Color.BLACK);
			tfTimer.setForeground(Color.WHITE);
			tfNumMines.setBackground(Color.BLACK);
			tfNumMines.setForeground(Color.WHITE);
			setCellsTheme();
		}
	}
	
	public void applyVolumeSettings() {
		if(SoundEffects.master_Volume == SoundEffects.Volume.LOW) {
			lowVolumeMenuItem.setSelected(true);
			mediumVolumeMenuItem.setSelected(false);
			highVolumeMenuItem.setSelected(false);
			System.out.println("Volume is low!");
		}
		else if(SoundEffects.master_Volume == SoundEffects.Volume.MEDIUM) {
			lowVolumeMenuItem.setSelected(false);
			mediumVolumeMenuItem.setSelected(true);
			highVolumeMenuItem.setSelected(false);
		}
		else if(SoundEffects.master_Volume == SoundEffects.Volume.HIGH) {
			lowVolumeMenuItem.setSelected(false);
			mediumVolumeMenuItem.setSelected(false);
			highVolumeMenuItem.setSelected(true);
		}
	}
	
	public void stopAllMusic() {
		for(SoundEffects s: SoundEffects.values()) {
			s.stopPlaying();
		}
	}
}
