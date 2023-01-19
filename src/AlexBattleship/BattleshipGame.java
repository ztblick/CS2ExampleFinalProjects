package AlexBattleship;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

/**************************************************************************
 * 
 * Battleship Game
 * 
 * @author Alex Boesch
 * @version May 28, 2021
 * 
 * Description of Program:
 * 
 * BattleshipGame is the client class that implements the game of Battleship. It
 * utilizes classes Board, Ship and Cell. Board contains two subclasses, MyBoard
 * and OppBoard. Ship contains 5 subclasses, Battleship, Carrier, Cruiser, Destroyer
 * and Submarine, for each of the individual hips.
 * 
 * Further description on how the game is played can be found when opening the program.
 * 
 * Description of Class:
 * 
 * The BattleshipGame client class mainly handles user interaction with the graphics
 * window, such as mouse input. Therefore, it is necessary to implement MouseListener and
 * MouseMotionLister, as well as their respective methods, in the the BattleshipGame class,
 * so the mouse can be fluidly integrated into the game.
 * 
 * The most used methods from Mouse classes are mouseClicked(), mouseDragged(), mouseReleased()
 * and mousePressed(). The mouse mouseDragged(), mouseReleased() and mousePressed() methods
 * are used to handle the set-up of the battleship game, where the user is asked to drag
 * ships to their desired locations. In these methods, the click location is often given as
 * reference to myBoard and oppBoard objects, so that the ships can be placed on the boards'
 * correct positions when they are released. The mouseClicked() method is to used to handle
 * rotate capabilities, start and end game capabilities and firing missiles. Click locations 
 * from mouseClicked() is also often used as reference for the board.
 * 
 * In order to distinguish between different stages of the game, stages are encoded as 
 * public static final int variables and the state of the game changes by altering the variable, private
 * int state. This allows for easy functionality with graphics and other areas in the code.
 * 
 * BattleshipGame also contains instance variables myBoard and oppBoard, which handles many
 * of the actions based on the user's mouse input. The program also contains a currentShip
 * instance variable that stores the current ship selection of the user during the set-up process,
 * a selectedCell instance variable, which stores the cell location the user chooses when about to fire,
 * and a dragging instance variable, which ensures that the user is dragging on a ship when the
 * mouse is dragged in the set-up process.
 * 
 * Finally, the BattleshipGame Class handles a large chunk of the graphical components 
 * of the program by using double buffering for a better user experience.
 * 
 *
 ****************************************************************************/
public class BattleshipGame  extends JFrame 
implements MouseListener, MouseMotionListener, ActionListener
{
	//Instance Variables
	
	//Basic Graphics constants
	public static final int MAX_WIDTH = 1280;
	public static final int MAX_HEIGHT = 720;
	public static final int TITLE_BAR = 23;
	public static final int TITLE_WIDTH = MAX_WIDTH / 2;
	public static final int TITLE_HEIGHT = TITLE_WIDTH * 3 / 13;
	public static final int DELAY_IN_MILLISEC = 20;
	
	//Reference variables for the different states of game
	public static final int GAME = 1; //state when user is, for example, firing missiles and destroying ships
	public static final int SET_UP = 2; //state when user chooses locations to place ships
	public static final int PLAYER_WINS = 3; //state at end of program when the player wins
	public static final int CPU_WINS = 5; //state at end of program when the CPU wins.
	public static final int WELCOME_SCREEN = 4; 
	
	private boolean dragging; //ensures that used is actually dragging a ship
	private Cell selectedCell; //cell that user selects when it wants to fire missile
	private Ship currentShip; //in set-up process, ship that the user has currently selected
	
	private int state;
	private Board myBoard, oppBoard;

	public static void main(String[] args)
	{
		BattleshipGame battleship = new BattleshipGame();
		battleship.setSize(MAX_WIDTH, MAX_HEIGHT);
		battleship.setVisible(true);
		battleship.setTitle("Battleship");
		
		//adding mouse input into program
		battleship.addMouseListener(battleship);
		battleship.addMouseMotionListener(battleship);
	}
	
	public BattleshipGame()
	{
		//initialize variables
		myBoard = new MyBoard();
		oppBoard = new OppBoard();
		
		dragging = false;
		selectedCell = null;
		state = WELCOME_SCREEN; //starts game in welcome screen
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(MAX_WIDTH, MAX_HEIGHT);
		setVisible(true);
		createBufferStrategy(2);
		
		Timer timer = new Timer(DELAY_IN_MILLISEC, this);
        // Start the timer
        timer.start();   
				
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//obtains x and y pixels of the location the user clicked
		int x = e.getX();
		int y = e.getY();
		
		if (state == WELCOME_SCREEN)
		{
			//alters state to SET_UP if user clicks start button
			boolean isClickOnStartButton = x >= BattleshipGame.MAX_WIDTH/2 - 100 && x <= BattleshipGame.MAX_WIDTH/2 + 100
					&& y >= BattleshipGame.MAX_HEIGHT-60 && y <= BattleshipGame.MAX_HEIGHT - 20;
					
			if (isClickOnStartButton)
				state = SET_UP;
		}
		
		//handles the actions needed when ship is rotated and the start button is pressed
		else if (state == SET_UP)
		{
			//Action for when you click on start button
			boolean isClickOnStartButton = x >= BattleshipGame.MAX_WIDTH/2 - 100 && x <= BattleshipGame.MAX_WIDTH/2 + 100
			&& y >= BattleshipGame.MAX_HEIGHT-60 && y <= BattleshipGame.MAX_HEIGHT - 20;
			
			//starts the game if user clicks the button and all the ships are placed
			if (isClickOnStartButton && myBoard.areShipsPlaced())
			{
				state = GAME;
				
				//resets the current ship selections
				currentShip.changeSelection();
				currentShip = null;
				
				//hides the ships on the opponent's board as well as places their ships
				oppBoard.changeShipsVisibility(false);
				oppBoard.placeOppShips();
			}
			
			//if user wishes to rotate the ship if there is a ship that is selected
			boolean isClickOnRotateButton = x >= 400 && x <= 430 && y >= 175 && y <= 205;
			if (currentShip != null && isClickOnRotateButton)
			{
				myBoard.rotate(currentShip);
			}
		}
		
		//handles the actions when the user clicks on locations where it wants to fire missiles
		else if (state == GAME)
		{
			//takes care of instances when user clicks on cell that it wants to fire at
			Cell c = oppBoard.getCell(x, y);
			if (c != null && oppBoard.isValidGuess(c))
			{
				//takes the previously selected cell and sets selection to false
				//then, takes new selection location and sets it to the selected cell and sets that cells selection to true
				if (selectedCell != null)
					selectedCell.setSelection(false);
				selectedCell = c;
				c.setSelection(true);
			}
			
			//when user clicks on the fire button, it checks if a cell is selected and then executes actions
			boolean isClickOnFireButton = x >= BattleshipGame.MAX_WIDTH/2 - 100 && x <= BattleshipGame.MAX_WIDTH/2 + 100
					&& y >= BattleshipGame.MAX_HEIGHT-60 && y <= BattleshipGame.MAX_HEIGHT - 20;
						
			if (selectedCell != null && isClickOnFireButton) //click on fire button
			{
				oppBoard.shootMissile(); //myBoard shoots missile at opponent's board
				
				//resets selection
				selectedCell.setSelection(false);
				selectedCell = null;
				 					
				myBoard.shootMissile(); //opponent shoots missile at myBoard
					
				if(myBoard.isGameOver())
				{
					state = CPU_WINS;
				}
				if (oppBoard.isGameOver())
				{
					state = PLAYER_WINS;
				}
			}
		}
		
		else if (state == PLAYER_WINS || state == CPU_WINS)
		{
			//if the user wants to end the game
			boolean isClickOnEndGame = x >= BattleshipGame.MAX_WIDTH/2 + 30 && x <= BattleshipGame.MAX_WIDTH/2 + 330
					&& y >= BattleshipGame.MAX_HEIGHT-60 && y <= BattleshipGame.MAX_HEIGHT - 20;
			if (isClickOnEndGame)
				System.exit(0);

			//if the user wants to play again
			boolean isClickOnPlayAgain = x >= BattleshipGame.MAX_WIDTH/2 - 350 && x <= BattleshipGame.MAX_WIDTH/2 - 50
					&& y >= BattleshipGame.MAX_HEIGHT-60 && y <= BattleshipGame.MAX_HEIGHT - 20;
					
			if (isClickOnPlayAgain)
			{
				myBoard = new MyBoard();
				oppBoard = new OppBoard();
				state = SET_UP; //brings user back to setUp phase
			}
		}
		
		//after mouse is clicked, the window is repainted
		repaint();
	}

	//when mouse is pressed, ensures that user clicks on ship and ship is removed from that location
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		Ship newShipSelection = myBoard.getShip(x, y);
		
		//sets selection to false for the ship that was previously selected if a new ship is selected
		if (currentShip != null && newShipSelection != null)
			currentShip.changeSelection();
	
		//makes the new ship that is clicked the ship that is selected
		if (state == SET_UP && newShipSelection != null)
		{
			dragging = true;
			currentShip = newShipSelection;
			currentShip.changeSelection();
			
			//when ship is clicked and about to be dragged, the ship is removed
			Cell c = myBoard.getCell(x, y);
			if (c != null)
				myBoard.removeShip(currentShip);
		}
		repaint();
	}

	@Override
	//when mouse is released, it snaps the ship to correct position and adds it to the Board class
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (state == SET_UP && myBoard.getShip(x, y) != null)
		{
			dragging = false;
			
			Cell c = myBoard.getCell(x, y);
			if (c != null) //releases ship on the board or a cell
			{
				int row = c.getRow();
				int col = c.getCol();
				
				//alters the row and column locations if the ship exceeds the Board length or width
				if (currentShip.isHorizontal() && col + currentShip.getShipLength() >= Board.BOARD_COLS)
					col = Board.BOARD_COLS - currentShip.getShipLength();
				else if (!currentShip.isHorizontal() && row + currentShip.getShipLength() >= Board.BOARD_ROWS)
					row = Board.BOARD_ROWS - currentShip.getShipLength();
				
				//checks if the ship overlaps with other ships
				if (myBoard.isOverlap(currentShip, row, col))
				{
					if (currentShip.getRow() == -1 && currentShip.getCol() == -1) //previous location was in fleet box
					{
						//moves the ship to the fleet box location
						currentShip.resetToDefault();
						currentShip.changeSelection();
						currentShip = null;
					}
					else
					{
						//moves the ship back to the previous location
						myBoard.placeShip(currentShip, currentShip.getRow(), currentShip.getCol());
					}
				}
				else
				{
					//if there is no overlap, places the ship
					myBoard.placeShip(currentShip, row, col);
				}
			}
			else
			{
				currentShip.resetToDefault();
				currentShip.changeSelection();
				currentShip = null;
			}
		}
		repaint();
	}

	//moves ships to the xPixel locations and yPixel locations when it is dragged
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();		
		
		if (state == SET_UP && dragging)
		{
			currentShip.setXCorner(x);
			currentShip.setYCorner(y);
		}
		
		repaint();
	}
	
	//necessary for animation
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	//Unused methods, but necessary for implementation
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent arg0) {}
	
	//draws a button
	private void drawButton(int xBoxLoc, int yBoxLoc, int boxWidth, int boxHeight, String label,
			int xLoc, int yLoc, Color c, Graphics g)
	{
		g.setColor(c);
		g.fillRect(xBoxLoc, yBoxLoc, boxWidth, boxHeight);
		g.setColor(Color.BLACK);
		g.drawString(label, xLoc, yLoc);
	}
	
	//Paint
	public void paint(Graphics g)
    {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;

        Graphics g2 = null;

        try 
        {
            g2 = bf.getDrawGraphics();

            myPaint(g2);
        } 
        finally 
        {
            g2.dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync(); 
    }
	
	public void myPaint(Graphics g)
	{
		if (state == WELCOME_SCREEN)
		{
			//draws background
			Image background = new ImageIcon("Resources/WelcomeScreen.png").getImage();
			g.drawImage(background, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
			
			//draws start game button
			g.setFont(new Font("Copperplate", Font.BOLD, 40)); 
			drawButton(BattleshipGame.MAX_WIDTH/2 - 100, BattleshipGame.MAX_HEIGHT-60, 200, 40,
					"Start", BattleshipGame.MAX_WIDTH/2 - 60, BattleshipGame.MAX_HEIGHT-30, Color.GREEN, g);
		}
		else
		{
			Image background = new ImageIcon("Resources/BattleshipBackground.jpeg").getImage();
			g.drawImage(background, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
			Image title = new ImageIcon("Resources/Title_Battleship.png").getImage();
			g.drawImage(title, MAX_WIDTH * 1/4, TITLE_BAR + 5, TITLE_WIDTH, TITLE_HEIGHT, this);
			
			myBoard.display(g);
			oppBoard.display(g);
		}		
		
		if (state == SET_UP)
		{
			//draws start game button
			g.setFont(new Font("Copperplate", Font.BOLD, 40)); 
			drawButton(BattleshipGame.MAX_WIDTH/2 - 100, BattleshipGame.MAX_HEIGHT-60, 200, 40,
					"Start", BattleshipGame.MAX_WIDTH/2 - 60, BattleshipGame.MAX_HEIGHT-30, Color.GREEN, g);
			
			//inserts rotate image
			Image rotateButton = new ImageIcon("Resources/Button.png").getImage();
			g.setColor(Color.RED); //surrounding square
			
			g.fillRect(400, 175, 30, 30);
			g.drawImage(rotateButton, 400, 175, 30, 30, this);
		}
		if (state == GAME)
		{
			//draws fire button
			g.setFont(new Font("Copperplate", Font.BOLD, 40)); 
			drawButton(BattleshipGame.MAX_WIDTH/2 - 100, BattleshipGame.MAX_HEIGHT-60, 200, 40,
					"FIRE", BattleshipGame.MAX_WIDTH/2 - 50, BattleshipGame.MAX_HEIGHT-30, Color.RED, g);
		}
		
		if(state == PLAYER_WINS || state == CPU_WINS)
		{
			Image darkenedBackground = new ImageIcon("Resources/BattleshipBackgroundDark.jpeg").getImage();
			g.drawImage(darkenedBackground, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
			
			String gameOverMessage = "You Win!";
			int width = MAX_WIDTH/2 - 140;
			if (state == CPU_WINS)
			{
				gameOverMessage = "You Lose.";
				width = MAX_WIDTH/2 - 150;
			}
			
			//draws game outcome button(not really a button, but requires same functionality)
			g.setFont(new Font("Copperplate", Font.BOLD, 60));
			drawButton(MAX_WIDTH/2 - 170, MAX_HEIGHT/2 - 30, 340, 60, gameOverMessage, width, MAX_HEIGHT/2 + 15,
					Color.WHITE, g);
			
			//draws the play again button
			g.setFont(new Font("Copperplate", Font.BOLD, 40)); 
			drawButton(BattleshipGame.MAX_WIDTH/2 - 350, BattleshipGame.MAX_HEIGHT-60, 300, 40,
					"Play Again", BattleshipGame.MAX_WIDTH/2 - 320, BattleshipGame.MAX_HEIGHT-30, Color.GREEN, g);
			
			//draws end game button
			drawButton(BattleshipGame.MAX_WIDTH/2 + 30, BattleshipGame.MAX_HEIGHT-60, 300, 40, 
					"End Game", BattleshipGame.MAX_WIDTH/2 + 80, BattleshipGame.MAX_HEIGHT-30, Color.RED, g);
		}
	}
	
}
