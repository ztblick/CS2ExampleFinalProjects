package AlexBattleship;
import java.util.Random;

/*********************************************************************************
 * OppBoard
 * 
 * @author Alex Boesch
 * @version May 28, 2021
 * 
 * Description:
 * 
 * OppBoard is a subclass of board and is supposed to do more specific actions. It is created by
 * calling super() and the XCorner position as a parameter and setting the name to Opponent Board.
 * The class' main functionality is to shoot the missile at a random cell location that has not
 * already been fired at. Also, it is to place the opponent's ships.
 * 
 *********************************************************************************/

public class OppBoard extends Board{

	Random rand = new Random();
	String name;
	
	public OppBoard() {
		super(BattleshipGame.MAX_WIDTH / 2 + 30);
		name = "Opponent's Board";
	}
	
	public void shootMissile()
	{
		Cell selectedCell = super.findSelectedCell();
		selectedCell.hitCell(); //hits the cell
	}
	
	public void placeOppShips()
	{
		/* This method tries to find a valid location for the ship if it is not overlaping another ship,
		 * or it does not go out of bounds. The program will continue to try to find a location to
		 * place the ship until it is valid.
		 * 
		 * If it is all true, then the ship will be placed at the specified location.
		 */
		for (Ship ship: ships) {
			int maxVal = BOARD_ROWS - ship.getShipLength();
			
			boolean isValidLocation = false;
			
			int row = rand.nextInt(10);
			int col = rand.nextInt(maxVal);
			
			while(!isValidLocation)
			{
				row = rand.nextInt(10); 
				col = rand.nextInt(maxVal);
				boolean horizontal = rand.nextBoolean();
				ship.setHorizontal(horizontal);
				
				if(!ship.isHorizontal())
				{
					row = rand.nextInt(maxVal); 
					col = rand.nextInt(10);
				}
				
				if(!isOverlap(ship, row, col))
				{
					isValidLocation = true;
				}
			}
			
			super.placeShip(ship, row, col);
		}
	}
	
	public String getName()
	{
		return name;
	}

}
