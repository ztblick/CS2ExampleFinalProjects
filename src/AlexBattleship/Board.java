package AlexBattleship;
import java.awt.*;

/*********************************************************************************
 * Board
 * 
 * @author Alex Boesch
 * @version May 28, 2021
 * 
 * Description:
 * Board is a class that creates the board for each of the individuals players. It has two
 * subclasses, MyBoard and OppBoard, which are to do more specified tasks that cannot be 
 * done by just the Board alone.
 * 
 * The class has instance variables of the X_CORNER position it should be placed at, the 
 * Y_CORNER position it should be placed at, the cell width, cell height, and both a ships
 * array and a boardArray. These last two values have a visibility of protected, so that 
 * the MyBoard and OppBoard child classes can access it, but a getter method could have 
 * also worked fine with its visibility being private. The ships array is to store the ships
 * that are on the board, and the boardArray is how the actual board is created: it is created
 * by a 2d array of cells.
 * 
 * The constructor takes in the value of the xCorner based on if it is created as a MyBoard object
 * or an OppBoard object. In the constructor, all the necessary values are initialized. For
 * example, the ships are initialized with their child objects.
 * 
 * The methods that mutate the boardArray in this class are placeShip, removeShip, rotate and shootMissile.
 * They are able to either place a ship, remove a ship, rotate a ship or shoot a missile. In order to do so, they
 * change the value of state in each of the cells backed on the mouse input. The methods that are
 * accessors are getCell() findSelectedCell() and getXCorner().
 * 
 * Overall, the board is greatly based on the 2d array of cells.
 * 
 *********************************************************************************/

import javax.swing.*;

public class Board extends JFrame{
	public static final int BOARD_ROWS = 10;
	public static final int BOARD_COLS = 10;

	public static final int WATER = 0;
	public static final int SHIP = 1;
	public static final int MISSED = 2;
	public static final int HIT = 3;
	
	private int FLEET_BOX_X_CORNER;
	
	private int X_CORNER;
	public static final int Y_CORNER = BattleshipGame.TITLE_HEIGHT + BattleshipGame.TITLE_BAR + 60;
	
	public static final int CELL_WIDTH = 40;
	public static final int CELL_HEIGHT = 40;
	
	
	//protected allows the subclasses to also have access to this information
	//could have used a getter method instead
	protected Ship[] ships;
	protected Cell[][] boardArray; //made up of cells
	
	//constructor
	public Board(int xCorner) 
	{
		boardArray = new Cell[BOARD_ROWS][BOARD_COLS];
		for (int r = 0; r < BOARD_ROWS; r++)
			for (int c = 0; c < BOARD_COLS; c++)
				boardArray[r][c] = new Cell(r, c, CELL_WIDTH, CELL_HEIGHT, this);
		
		X_CORNER = xCorner;
		FLEET_BOX_X_CORNER = X_CORNER + (CELL_WIDTH * BOARD_COLS);
		
		//create array of temporary ships first. Then set that array to the ships.
		Ship[] tempShips = 
				{new Carrier(FLEET_BOX_X_CORNER, Y_CORNER + 40), 
				new Battleship(FLEET_BOX_X_CORNER,Y_CORNER + 120), 
				new Cruiser(FLEET_BOX_X_CORNER, Y_CORNER + 200), 
				new Submarine(FLEET_BOX_X_CORNER, Y_CORNER + 280),
				new Destroyer(FLEET_BOX_X_CORNER, Y_CORNER + 360)};
		ships = tempShips;
	}
	
	//accessor methods
	public Ship getShip(int xClickLoc, int yClickLoc)
	{
		//returns ship at clicked pixel location
		for (Ship ship: ships)
			if (ship.isClickOnShip(xClickLoc, yClickLoc))
				return ship;

		return null;
	}
	
	public int getXCorner()
	{
		return X_CORNER;
	}
	
	public Cell getCell(int xPixel, int yPixel)
	{
		//returns cell at specified pixel location --> if no cell, return null
		int row = getRowFromPixel(yPixel);
		int col = getColFromPixel(xPixel);
				
		if (row != -1 && col != -1)
		{
			return boardArray[row][col];
		}
		return null;
	}
	
	public Cell findSelectedCell()
	{
		//finds which cell is selected --> if no cell is selected, return nulls
		for (int row = 0; row < BOARD_ROWS; row++)
			for (int col = 0; col < BOARD_COLS; col++)
				if (boardArray[row][col].isSelected())
					return boardArray[row][col];
		
		return null;
	}
	
	// Methods used in set-up process to place, remove and rotate ships
	
	public void placeShip(Ship ship, int row, int col)
	{
		if (ship.isHorizontal())
		{	
			//if ship is horizontal, updates cells EASTWARDS of row and col to state ship
			for (int i = 0; i < ship.getShipLength(); i++) 
			{
				boardArray[row][col+i].setState(SHIP);
				boardArray[row][col+i].setShip(ship);
			}
		}
		else
		{	
			//if ship is horizontal, updates cells DOWNWARDS of row and col to state ship
			for (int i = 0; i < ship.getShipLength(); i++)
			{
				boardArray[row+i][col].setState(SHIP);
				boardArray[row+i][col].setShip(ship);
			}
		}
		ship.setXCorner(X_CORNER + col * CELL_WIDTH);
		ship.setYCorner(Y_CORNER + row * CELL_HEIGHT);
		ship.setRow(row);
		ship.setCol(col);
	}
	
	public void rotate(Ship ship)
	{
		//checks if the ship is out of bounds first
		boolean isOutOfBounds = false;
		if(ship.isHorizontal()) 
		{
			if (ship.getRow() + ship.getShipLength() > Board.BOARD_COLS) 
				isOutOfBounds = true;
		}
		else
		{
			if (ship.getCol() + ship.getShipLength() > Board.BOARD_ROWS) 
				isOutOfBounds = true;
		}
		
		if (!isOutOfBounds)
		{
			//this removes ship, then rotates if it does not overlap with another ship. Then it replaces the ship
			removeShip(ship);
			ship.rotate();
			if(isOverlap(ship, ship.getRow(), ship.getCol()))
			{
				ship.rotate();
			}
			placeShip(ship, ship.getRow(), ship.getCol());	
		}
	}
	
	public void removeShip(Ship ship)
	{
		//sets the cells where the ship is to water
		if (ship != null) {
			if (ship.isHorizontal())
			{
				for (int i = 0; i < ship.getShipLength(); i++)
				{
					boardArray[ship.getRow()][ship.getCol()+i].setState(WATER);
					boardArray[ship.getRow()][ship.getCol()+i].setShip(null);
				}
			}
			else
			{
				for (int i = 0; i < ship.getShipLength(); i++)
				{
					boardArray[ship.getRow()+i][ship.getCol()].setState(WATER);
					boardArray[ship.getRow()+i][ship.getCol()].setShip(null);
				}
			}
		}
	}
	
	//methods that are coded in subclasses, but placed here for program to function
	public void shootMissile() //shoots missile
	{
		//necessary for MyBoard and OppBoard ShootMissle methods to call
	}

	public void placeOppShips() //places the opponent's ships
	{
		//code is only in OppBoard.
	}

	//Methods to ensure user actions are valid
	public boolean isOverlap(Ship ship, int row, int col)
	{
		if (ship.isHorizontal())
		{
			for (int i = 0; i < ship.getShipLength(); i++)
				if (boardArray[row][col+i].getState() == SHIP)
					return true;
		}
		else
		{
			for (int i = 0; i < ship.getShipLength(); i++)
				if (boardArray[row+i][col].getState() == SHIP)
					return true;
		}
		return false;
	}
	
	public boolean isValidGuess(Cell c) {
		
		if(c.getState() == MISSED || c.getState() == HIT)
			return false;
		return true;
	}

	//gets the rows and cols from pixel locations
	private int getColFromPixel(int xPixel)
	{
		for (int c = 0; c < BOARD_COLS; c++)
			if (xPixel > X_CORNER + c * CELL_WIDTH && xPixel <= X_CORNER + (c+1) * CELL_WIDTH)
				return c;
		
		return -1;
	}
	
	private int getRowFromPixel(int yPixel)
	{
		
		for (int r = 0; r < BOARD_ROWS; r++)
			if (yPixel > Y_CORNER + (r * CELL_HEIGHT) && yPixel <= Y_CORNER + (r+1) * CELL_HEIGHT)
				return r;
				
		return -1;
	}
	
	//boolean methods that return value to BattleshipGame if ships are placed or game is over.
	public boolean areShipsPlaced()
	{
		for (Ship ship: ships)
			if (ship.getRow() == -1 && ship.getRow() == -1)
				return false;
		
		return true;
	}


	public boolean isGameOver()
	{
		for (int r = 0; r < BOARD_ROWS; r++)
			for (int c = 0; c < BOARD_COLS; c++)
				if (boardArray[r][c].getState() == SHIP)
					return false;
		
		return true;
	}
	
	//mutator method to change visibility of ships
	public void changeShipsVisibility(boolean visibility)
	{
		for (Ship ship: ships)
			ship.setVisibility(visibility);
	}

	//draw Methods
	private void centerText(int xCorner, int yCorner, int width, int height, Font f, String text, Graphics g)
	{
		//automatically centers text
		FontMetrics metrics = g.getFontMetrics(f);
	    int x = xCorner + (width - metrics.stringWidth(text)) / 2;
	    int y = yCorner + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(f);
	    g.drawString(text, x, y);
	}

	private void drawCellBox(Graphics g, int xCorner, int yCorner, int width, int height, Font f, String coordString)
	{
		//draws the
		g.setColor(Color.WHITE);
		g.drawRect(xCorner, yCorner, width, height);
		g.setColor(Color.GRAY);
		g.fillRect(xCorner, yCorner, width, height);
		g.setColor(Color.RED);
		centerText(xCorner, yCorner, width, height, f, coordString, g);
	}

	private void makeFleetBox(Graphics g)
	{
		//makes the fleet box to the left of the board
		g.setColor(Color.GRAY);
		int fleetBoxWidth = BattleshipGame.MAX_WIDTH/2 - 40 - (BOARD_ROWS * CELL_WIDTH);
		int fleetBoxHeight = BOARD_ROWS * CELL_HEIGHT;
		
		g.fillRect(FLEET_BOX_X_CORNER, Y_CORNER, fleetBoxWidth, fleetBoxHeight);
		
		g.setColor(Color.WHITE);
		g.drawRect(FLEET_BOX_X_CORNER, Y_CORNER, fleetBoxWidth, fleetBoxHeight);
		
		g.setColor(Color.BLUE);
		centerText(FLEET_BOX_X_CORNER, Y_CORNER-5 , fleetBoxWidth, CELL_HEIGHT, new Font("Copperplate", Font.BOLD, 20), "Fleet", g);
	}

	private void displayGrid(Graphics g)
	{	
		Font f = (new Font("Copperplate", Font.BOLD, 15)); //fontSize is 15
		
		for (int row = 0; row < BOARD_ROWS; row++)
		{
			//creates coord guides on the left
			drawCellBox(g, X_CORNER - 20, Y_CORNER + row * CELL_HEIGHT, 20, CELL_HEIGHT, f, Character.toString((char) row + 65));
			for (int col = 0; col < BOARD_COLS; col++)
			{
				//creates coord guides at top
				if (row == 0)
					drawCellBox(g, X_CORNER + col * CELL_HEIGHT, Y_CORNER - 20, CELL_WIDTH, 20, f, Integer.toString(col+1));
				
				//displays the cell
				boardArray[row][col].display(g);
			}
		}
	}

	public void display (Graphics g)
	{
		displayGrid(g);
		makeFleetBox(g);
		
		//writes title
		String boardTitle = getName();
		g.setColor(Color.WHITE);
		centerText(X_CORNER, BattleshipGame.TITLE_BAR + BattleshipGame.TITLE_HEIGHT + 10, BOARD_COLS * CELL_WIDTH, 20, new Font("Copperplate", Font.BOLD, 30), boardTitle, g);
		
		//places ships
		for (Ship ship: ships)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Copperplate", Font.PLAIN, 20));
			String shipName = ship.getName();
			g.drawString(shipName, ship.getDefaultXLoc(), ship.getDefaultYLoc());
			ship.draw(g);
		}
		
		for (int row = 0; row < Board.BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++)
			{
				//shows the exploded areas
				if (boardArray[row][col].getState() == HIT)
				{
					Image fire = new ImageIcon("Resources/Fire.gif").getImage();
					g.drawImage(fire, X_CORNER + (CELL_WIDTH * col), Y_CORNER + (CELL_HEIGHT * row), CELL_WIDTH, CELL_HEIGHT, this);
				}
				//shows the missed areas
				if (boardArray[row][col].getState() == MISSED)
				{
					g.setColor(Color.WHITE);
					g.fillOval(X_CORNER + (CELL_WIDTH * col) + 10, Y_CORNER + (CELL_HEIGHT * row) + 10, 20, 20);
				}
			}
		}
	}
}
