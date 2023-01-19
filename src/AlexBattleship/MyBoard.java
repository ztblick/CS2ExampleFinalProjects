package AlexBattleship;
import java.util.Random;

/*********************************************************************************
 * MyBoard
 * 
 * @author Alex Boesch
 * @version May 28, 2021
 * 
 * Description:
 * 
 * MyBoard is a subclass of board and is supposed to do more specific actions. It is created by
 * calling super() and the XCorner position as a parameter and setting the name to My Board.
 * The class' main functionality is to shoot the missile at a random cell location that has not
 * already been fired at.
 * 
 *********************************************************************************/

public class MyBoard extends Board{

	Random rand = new Random();
	String name;
	
	public MyBoard() {
		super(30);
		name = "My Board";
	}
	
	public void shootMissile() {
		boolean valid = false;
		int row = rand.nextInt(10); 
		int col = rand.nextInt(10);
		
		//while the random cell location is in a hit or missed state, the program will attempt to find a new random location
		while(!valid)
		{
			row = rand.nextInt(10); 
			col = rand.nextInt(10);
			
			if (boardArray[row][col].getState() == WATER || boardArray[row][col].getState() == SHIP)
			{
				valid = true;
			}
		}
		Cell selectedCell = boardArray[row][col];
		selectedCell.hitCell(); //hits the cell
	}
	
	public String getName()
	{
		return name;
	}

}
