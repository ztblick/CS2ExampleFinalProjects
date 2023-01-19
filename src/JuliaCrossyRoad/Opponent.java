package JuliaCrossyRoad;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon; 

/**********************************************************

Opponent

Created by:  [ Julia Lau ]
Date:        [ May 28th, 2021 ]

Description:
	
	Opponent is an object that represents the cars in CrossyRoad. It is a subclass of the Sprite class. 
	It moves through calling the move() method in the actionPerformed method in the CrossyRoad class which 
	changes the x-component of the object. Its speed is determined by a rate, randomly generated from 2-5 pixels. 
	The object will disappear off screen once it reaches the end of the board, or reaches the last column.
	
	NOTABLE Instance Variables:
	- int rate			A randomly generated int from a range of 2-5 pixels 
	- int direction 	A randomly generated int representing which direction the object will move in with a 50% chance of each direction
	
***********************************************************/

public class Opponent extends Sprite { 
	
	private int rate;
	private int direction; // to left (-1) and to right (1)
	private int border; // of window
	private int title; 
	private int boardCols;
	private static Color shadow = new Color(46, 49, 77);

	public Opponent( int inDim, int startX, int startY, int title, int border, int cols) {	
		
		super(startX, startY, inDim);
		rate = (int) (Math.random() * 3) + 2;
		direction = (Math.random() > 0.5 ) ? 1 : -1 ;
		this.border = border;
		this.title = title;
		boardCols = cols;
	}
	
	public void move() { 
		
		int[] loc = super.getLocation();
		if (direction == 1) {
			
			if (loc[0] + 1 >= boardCols - 2) {
				setX(0);
				rate = (int) (Math.random() * 3) + 2;	
			}
			
			else {
				super.changeX(rate);
			}
		}
		
		else if (direction <= -1) {	
			
			if (loc[0] - 1 <= 1 ) {
				setX(boardCols -2);
				rate = (int) (Math.random() * 3) + 2;	
			}
			
			else {
				super.changeX(-rate);
			}
		}
	}
	
	public void paint(Graphics g, ImageObserver hi) {
		
		g.setColor(shadow);
		g.fillRect(super.currX * super.dimension + border, super.currY * super.dimension + border + title, super.dimension * 2 , super.dimension * 2 );
		
		Image car = new ImageIcon ("Resources/car1.png").getImage();
		Image car2 = new ImageIcon ("Resources/car2.png").getImage();
		
		if (direction == -1) {
			g.drawImage(car, super.currX * super.dimension + border, super.currY * super.dimension + border + title, 60, 50, hi);
		}
		
		if (direction == 1) {
			g.drawImage(car2, super.currX * super.dimension + border, super.currY * super.dimension + border + title, 60, 55, hi);
		}
	}
}
