package JuliaCrossyRoad;
import java.awt.Color;
import java.awt.Graphics;

/**********************************************************

Pixel

Created by:  [ Julia Lau ]
Date:        [ May 28th, 2021 ]

Description:
	
	NOTABLE Instance Variables:
	- boolean type	This determines the type of Pixel (whether it is normal or an obstacle)
	- int x			Location of x-coordinate on x/y coordinate
	- int y			Location of y-coordinate on x/y coordinate
	- Color color 	Color is kept null if not a "road" 
	
***********************************************************/

public class Pixel {
	
	private boolean type; //true is a normal Pixel, false is an obstacle Pixel
	private int x;
	private int y;
	private int dimension;
	private Color color;
	
	private static Color road = new Color (57,61,96);
	
	public Pixel (int inX, int inY, int dem, boolean type) {
		x = inX;
		y = inY;
		dimension = dem;
		this.type = type;
	}
		
	public boolean getType () {
		return type;
	}
	
	public int getX () {
		return x;
	}
	
	public int getY ( ) {
		return y;
	}
	
	public void setColor (Color color) {
		this.color = color;
	}
	
	public void paint(Graphics g, int border, int title, Color color, Color outlineC) {
		
		if (type == true) {
			g.setColor(color);
		}
		
		else if (type = false) {
			g.setColor(outlineC);
		}
		
		if (this.color == Color.blue ) {
			g.setColor(road);
		}
		
		else if (this.color != Color.blue && this.color != null) {
			g.setColor(this.color);
		}
		
		int drawX = x * dimension + border + title;
		int drawY = y * dimension + border ;
		g.fillRect(drawY, drawX, dimension, dimension);
		
		g.setColor(outlineC);
	}
}
