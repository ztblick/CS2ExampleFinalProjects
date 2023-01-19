package AlexBattleship;
import java.awt.*;
import java.awt.image.ImageObserver;

/*********************************************************************************
 * Ship
 * 
 * @author Alex Boesch
 * @version May 28, 2021
 * 
 * Description:
 * 
 * Ship is a class that is a parent class of 5 classes Battleship, carrier, cruiser, destroyer
 * and submarine. The ship has instance variables of xPixelCorner, yPixelCorner, row, col,
 * defaultXLoc, defaultYLoc and name. It also has instance variables of shipLength, health, isHorizontal
 * and health.
 * 
 * The class consists of various getter and setter methods that allows it to function.
 * 
 *********************************************************************************/

public class Ship implements ImageObserver{
	private boolean isSelected;
	
	//ideally, it would be better to only have one of these pairs of variables, but
	//because we need to update the location of the ship when it is being dragged,
	//we need both of these values.
	private int xPixelCorner;
	private int yPixelCorner;
	private int row;
	private int col;
	
	public int defaultShipXLoc;
	public int defaultShipYLoc;
	
	private int shipLength;
	private int health;
	private String name;
	
	private boolean isHorizontal;
	private boolean setVisible;
		
	public Ship(int shipLengthIn, int startX, int startY, String name)
	{
		//intializes variables
		shipLength = shipLengthIn; 
		xPixelCorner = startX;
		yPixelCorner = startY;
		defaultShipXLoc = startX;
		defaultShipYLoc = startY;
		health = shipLength;
		row = -1;
		col = -1;
		
		this.name = name;
		
		isHorizontal = true;
		isSelected = false;
		setVisible = true;
	}

	public void draw(Graphics g)
	{
		//draws the ship by getting the inherited image
		int width = Board.CELL_WIDTH * shipLength;
		int height = Board.CELL_HEIGHT;
		if (!isHorizontal)
		{
			width = Board.CELL_WIDTH;
			height = Board.CELL_HEIGHT * shipLength;
		}
	
		if(setVisible)
			g.drawImage(getShipImage(g), xPixelCorner, yPixelCorner, width, height, (ImageObserver) this);
		
		//if is Selected, sets green box around ship
		if (isSelected)
		{
			g.setColor(Color.GREEN);
			g.drawRect(xPixelCorner, yPixelCorner, width, height);
		}
		
	}
	
	//getter methods
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public String getName()
	{
		return name;
	}

	public int getDefaultXLoc()
	{
		return defaultShipXLoc;
	}

	public int getDefaultYLoc()
	{
		return defaultShipYLoc;
	}

	public int getShipLength()
	{
		return shipLength;
	}
	
	public boolean getVisibility()
	{
		return setVisible;
	}

	public Image getShipImage(Graphics g)
	{
		return null; //necessary for program, but actual image is in inherited class
	}

	public boolean isHorizontal()
	{
		return isHorizontal;
	}

	public void rotate()
	{
		isHorizontal = !isHorizontal;
	}

	//setter methods

	public void setHorizontal(boolean horizontalIn)
	{
		isHorizontal = horizontalIn;
	}

	public void setXCorner(int newX)
	{
		xPixelCorner = newX;
	}

	public void setYCorner(int newY)
	{
		yPixelCorner = newY;
	}

	public void setRow(int rowIn)
	{
		row = rowIn;
	}

	public void setCol(int colIn)
	{
		col = colIn;
	}

	public void setVisibility(boolean visibilityIn) {
		setVisible = visibilityIn;
	}

	public void changeSelection()
	{
		isSelected = !isSelected;
	}

	//checks if a certain x click location and y click location is on the ship.
	public boolean isClickOnShip(int xClickLoc, int yClickLoc)
	{
		boolean isClickOnShip = false;
		
		if (isHorizontal)
		{
			if (xClickLoc >= xPixelCorner && xClickLoc <= (xPixelCorner + Board.CELL_WIDTH * shipLength)
					&& yClickLoc >= yPixelCorner && yClickLoc <= (yPixelCorner + Board.CELL_HEIGHT))

				isClickOnShip = true;
		}
		else
		{
			if (xClickLoc >= xPixelCorner && xClickLoc <= (xPixelCorner + Board.CELL_WIDTH)
					&& yClickLoc >= yPixelCorner && yClickLoc <= (yPixelCorner + Board.CELL_HEIGHT * shipLength))
				isClickOnShip = true;
		}
		return isClickOnShip;
	}
	
	//resets the ship to its position on the fleet box
	public void resetToDefault() {
		xPixelCorner = defaultShipXLoc;
		yPixelCorner = defaultShipYLoc;
		row = -1;
		col = -1;
		
		if (!isHorizontal)
		{
			isHorizontal = true;
		}
	}

	//decreases the ships health by one
	public void hit()
	{
		health--;
	}
	
	public boolean isAlive()
	{
		return health > 0; //if ship is still alive
	}
	
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		return false; //necessary for image to be implemented
	}
}
