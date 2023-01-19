package JuliaCrossyRoad;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;


/**********************************************************
  
  Board
  
  Created by:  [ Julia Lau ]
  Date:        [ May 28th, 2021 ]
  
  Description:
	Board sets up and paints the individual Pixels that make up the board as organized in a 2D array of Pixels. Board also controls the flow of elements on the board
	through its methods such as checkValid() and hitEnemy() which compare the location of an object to another element on the board or location. Board also determines
	losing and winning outcomes from the result of the methods hitEnemy() and checkWin() respectively. 
	
	NOTABLE Instance Variables:
	- Character mainCharacter 	This is the Character that is controlled by the user through keyboard input
	- Opponent[] enemy			This is the array of Opponents, or cars, that move laterally across the screen
	- Pixel[][] pixelArray		This is the array of individual Pixels that are initialized with a location on the 
								x/y coordinate and a type (of true or false) as determined by the final int[][] map below
	- int rows					This establishes one's x-coordinate on the x/y coordinate system, starting at 0
	- int cols					This establishes one's y-coordinate on the x/y coordinate system, starting at 0
	
 ***********************************************************/

public class Board { 
	
	private static final int[][] map = 
			
			//2D array of ints that creates works with the Pixel[][] pixelArray to initialize if a Pixel is an obstacle or not
			//0 signifies normal Pixel, 1 signifies obstacle pixel (obstacle pixels restrict the movement of the mainCharacter)
		
		{
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 0, 0, 1} , 
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 0, 0, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0 , 1, 1, 1 , 1, 1, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 , 1 , 1, 1, 1 , 1, 1, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 , 0 , 1, 1, 1 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 , 0 , 1, 1, 1 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 0, 1, 1, 1} ,	
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 1, 1, 1 , 0, 0, 0 , 0, 0, 0 , 0, 0 , 0 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1 , 1 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1 , 1 , 0, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1 , 1 , 0, 0, 0 , 0, 0, 1 , 1, 1, 1 , 1, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1 , 1 , 0, 0, 0 , 0, 0, 0 , 1, 1, 1 , 1, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 0, 0, 0 , 0, 0, 0 , 1, 1, 1 , 1, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 1, 1, 1 , 1, 0, 0 , 1, 1, 1 , 1, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 1, 1, 1 , 1, 0, 0 , 0, 0, 1 , 1, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 1, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 1, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} ,
			{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1 , 1 , 1, 0, 0 , 0, 0, 0 , 0, 0, 0 , 0, 1 , 1 , 1, 1, 1, 1} 
		} ;

	private Character mainCharacter;
	private Opponent[] enemy;
	private Pixel[][] pixelArray;
	private int rows;
	private int cols;
	
	private int border; //around board frame
	private int title; //bar length
	private Color color; 
	private Color outline;
	
	private static Color grassRoad = new Color(64, 67, 119);
	
	public Board (int inRows, int inCols, int border, int titleBar, int squareLength, Character mainCharacter, Opponent[] enemy, Color boardColor, Color outlineC) {
		rows = inRows;
		cols = inCols;
		pixelArray = new Pixel[inRows][inCols];
		this.mainCharacter = mainCharacter;
		this.enemy = enemy;
		this.border = border;
		title = titleBar;
		color = boardColor;
		outline = outlineC;
		
		for (int i = 0; i < rows; i++) { //initializes entire board of Pixels, comparing it to int[][] map
			for (int j = 0; j < cols; j++) {
				
				if (map[i][j] == 0) {
					pixelArray[i][j] = new Pixel (i, j, squareLength, true);
				}
				
				else if (map[i][j] == 1) {
					pixelArray[i][j] = new Pixel (i, j, squareLength, false);
				}
			}
		}
		
		for (int i = 0; i < rows - 1; i ++) { //if an enemy (type Opponent) is located in that row, will set color for the "road" 
			for (int k = 0; k < enemy.length; k++) {
				
				if ( enemy[k].getLocation()[1] == i ) {
					
					for (int j = 0; j < cols; j++) {
						
						if (map[i][j] == 0) {
							pixelArray[i][j].setColor(Color.blue);
						}
						else {
							pixelArray[i ][j].setColor(grassRoad);
						}
					}
					
					if ( enemy[k].getLocation()[1] + 1 == i + 1 ) {
					
						for (int j = 0; j < cols; j++) {
						
							if (map[i + 1][j] == 0) {
								pixelArray[i + 1][j].setColor(Color.blue);
							}
							else {
								pixelArray[i + 1][j].setColor(grassRoad);
							}
						}
					}
				}	
			}
		}
	}
	
	public boolean checkValid (int step, int direction) {  //called in CrossyRoad class each time an arrow key is pressed
														   //checks to see if desired next location by user is an obstacle or not
														   //if location is an obstacle, returns false since not valid; if it is 
														   //not an obstacle, returns true because it is valid
		int[] currLoc = mainCharacter.getLoc();
		
		if (direction == 0 ) {
			
			if (currLoc[1] + 1 + step > rows - 1) {
				return false;
			}
			
			else if (currLoc[1] + step < 0) {
				return false;
			}
		
			if (pixelArray[currLoc[1] + step + 1][currLoc[0]].getType() == false  			//since mainCharacter is made up of 4 pixels (2x2), it needs to check
					|| pixelArray[currLoc[1] + step ][currLoc[0]].getType() == false 		//the location of all four pixels in relation to the next desired location
					|| pixelArray[currLoc[1] + step + 1][currLoc[0] + 1].getType() == false	//and whether there is an obstacle in the way
					|| pixelArray[currLoc[1] + step][currLoc[0] + 1].getType() == false) { 
				return false;
			}
			
			return true;
		}
		
		else if (direction == 1)  {
			
			if (currLoc[0] + 1 + step > cols - 1 ) { 
				return false;
			}
			
			else if (currLoc[0] + step < 0) {
				return false;
			}
			
			if (pixelArray[currLoc[1]][currLoc[0] + step].getType() == false 
					|| pixelArray[currLoc[1]][currLoc[0] + step + 1].getType() == false
					|| pixelArray[currLoc[1] + 1][currLoc[0] + step + 1].getType() == false
					|| pixelArray[currLoc[1] + 1][currLoc[0] + step ].getType() == false) { 
				return false;
			}

			return true;
		}
		
		return true;
	}

	public int hitEnemy () {	//called in CrossyRoad class each time actionPerformed is called. It checks to see if mainCharacter has hit an enemy
		
		int enemyLoc[];
		int currLoc[] = mainCharacter.getLoc();
		
		for (int i = 0 ; i < enemy.length; i ++) {
			
			enemyLoc = enemy[i].getLocation();
							
			if (enemyLoc[0] == currLoc[0] && enemyLoc[1] == currLoc[1] 						//since Opponents are also made of four pixels (2x2), 
						|| enemyLoc[0] == currLoc[0] && enemyLoc[1] == currLoc[1] + 1		//it needs to compare its location with each of mainCharacter's pixels
						|| enemyLoc[0] == currLoc[0] + 1 && enemyLoc[1] == currLoc[1] + 1 
						|| enemyLoc[0] == currLoc[0] + 1 && enemyLoc[1] == currLoc[1]
							
				|| enemyLoc[0] + 1 == currLoc[0] && enemyLoc[1] + 1 == currLoc[1] 
						|| enemyLoc[0] + 1 == currLoc[0] && enemyLoc[1] + 1 == currLoc[1] + 1
						|| enemyLoc[0]  + 1 == currLoc[0] + 1 && enemyLoc[1] + 1 == currLoc[1] + 1 					
						|| enemyLoc[0]  + 1 == currLoc[0] + 1 && enemyLoc[1] + 1== currLoc[1]
								
				|| enemyLoc[0] == currLoc[0] && enemyLoc[1] + 1 == currLoc[1] 
						|| enemyLoc[0]  == currLoc[0] && enemyLoc[1] + 1 == currLoc[1] + 1
						|| enemyLoc[0]   == currLoc[0] + 1 && enemyLoc[1] + 1 == currLoc[1] + 1 					
						|| enemyLoc[0]   == currLoc[0] + 1 && enemyLoc[1] + 1== currLoc[1]
										
				|| enemyLoc[0] + 1 == currLoc[0] && enemyLoc[1]  == currLoc[1] 
						|| enemyLoc[0] + 1 == currLoc[0] && enemyLoc[1] == currLoc[1] + 1
						|| enemyLoc[0]  + 1 == currLoc[0] + 1 && enemyLoc[1] == currLoc[1] + 1 					
						|| enemyLoc[0]  + 1 == currLoc[0] + 1 && enemyLoc[1] == currLoc[1])    {
			
				return 1;
			}
		}		
		
		return 0;
	}
	
	public int checkWin () {	//checks to see if mainCharacter has reached bottom of screen
		
		if (mainCharacter.getLocation()[1] >= 28) {
			return 2;
		}
		
		return 0;
	}
		
	public void paint(Graphics g, ImageObserver hi) { 
	
		for (int i = 0; i < pixelArray.length; i++) {
			for (int j = 0; j < pixelArray[0].length; j++ ) {
				pixelArray[i][j].paint(g, border, title, color, outline);
			}
		}
		
		mainCharacter.paint(g, hi); //works!
		
		for (int i = 0; i < enemy.length; i ++) {
			enemy[i].paint(g, hi);
		}
	}
}
