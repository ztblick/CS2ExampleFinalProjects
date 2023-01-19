package AlexBattleship;
import java.awt.*;

/*********************************************************************************
 * Cell
 * 
 * @author Alex Boesch
 * @version May 28, 2021
 * 
 * Description:
 * 
 * Cell is a class that is comprised of a state that is either WATER, SHIP, MISSED or HIT.
 * It stores the board that the cell is in, the ship it has, and the row and col it is on.
 * Finally, it stores if it is selected.
 * 
 * The class consists of various getter and setter methods that allows it to function.
 * 
 *********************************************************************************/


public class Cell {
	private int row, col; //row and column of cell
	private Ship ship; //ship that is contained in cell. If none, ship is null
	private int state; //the state of he cell
	private int width, height; //width and height of the cell
	private boolean isSelected; //if the cell is selected
	private Board board; //board that the cell is on
	
	//encoded values
	public static final int WATER = 0;
	public static final int SHIP = 1;
	public static final int MISSED = 2;
	public static final int HIT = 3;
	
	public Cell(int rowIn, int colIn, int width, int height, Board board)
	{
		row = rowIn;
		col = colIn;
		this.width = width;
		this.height = height;
		ship = null;
		state = WATER;
		isSelected = false;
		this.board = board;
	}
	
	//displays cell by drawing rectange and filling it
	public void display(Graphics g)
	{
		g.setColor(new Color(15,77,146));
		if (isSelected)
		{
			g.setColor(Color.BLUE);
		}
		g.fillRect(board.getXCorner() + (width * col), board.Y_CORNER + (height * row), width, height);
		g.setColor(Color.WHITE);
		g.drawRect(board.getXCorner() + (width * col), board.Y_CORNER + (height * row), width, height);
	}
	
	//getter methods
	public int getState()
	{
		return state;
	}
	
	public int getRow()
	{
		return row;
	}

	public int getCol()
	{
		return col;
	}
	
	public Ship getShip()
	{
		return ship;
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	//setter methods
	public void setState(int state)
	{
		this.state = state;
	}
	
	public void setSelection(boolean selection)
	{
		isSelected = selection;
	}
	
	public void setShip(Ship shipIn)
	{
		ship = shipIn;
	}

	//carries out the action to hit cell and change the state based on its current state
	public boolean hitCell()
	{
		if (state == WATER)
		{
			state = MISSED;
			return false;
		}
		if (state == SHIP)
		{
			state = HIT;
			if (ship != null)
			{
				ship.hit();
				if (!ship.isAlive())
				{
					ship.setVisibility(true);
					ship.resetToDefault();
				}
			}
			return true;
		}
		return false;
	}

}
