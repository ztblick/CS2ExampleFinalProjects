package JuliaCrossyRoad;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**********************************************************

Character

Created by:  [ Julia Lau ]
Date:        [ May 28th, 2021 ]

Description:
	
	Character is an object that represents the chicken mainCharacter in CrossyRoad. It is a subclass of the Sprite class. 
	It moves through keyboard input of the arrow keys and uses the Board class' checkValid() method to determine 
	whether or not to move the object.
	
***********************************************************/

public class Character extends Sprite{ //like checker
	
	private Color color;
	private int space; // character is slightly smaller than pixel size
	private int border; // window's boarder
	private int title;
	
	public Character (int dimension, int startX, int startY, int title, int border) {
		
		super(startX, startY, dimension);
		color = new Color (138, 153, 72);
		space = dimension / 10;
		this.border = border;
		this.title = title;
	}
	
	public int[] getLoc() {
		return super.getLocation();
	}
	
	public void move(boolean valid, int direction, Color Incolor, int step) {  //step represents the amount of pixels object will displace when an arrow key is pressed
		
		if (valid && (direction == 0 || direction == 2)) { 
			changeY (step);
		}
		
		else if (valid && (direction == 1 || direction == 3)) {
			changeX (step);
		}
	}

	public void paint(Graphics g, ImageObserver hi) { //creates an Image and shadow for the Character object
		g.setColor(color);
		g.fillOval(super.currX * super.dimension + border + space, super.currY * super.dimension + border + title + space, super.dimension * 2 - space * 2, super.dimension * 2 - space * 2);
	
		Image mainChar = new ImageIcon ("Resources/chDown.png").getImage();
		g.drawImage(mainChar, super.currX * super.dimension + border , super.currY * super.dimension + border + title , 50, 50, hi); 
	}

}
