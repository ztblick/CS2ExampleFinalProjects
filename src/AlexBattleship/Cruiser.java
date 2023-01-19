package AlexBattleship;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Cruiser extends Ship {
	private static final int SHIP_LENGTH = 3;
	
	public Cruiser(int startX, int startY)
	{
		super(SHIP_LENGTH, startX, startY, "Cruiser");
	}
	
	public Image getShipImage(Graphics g)
	{
		Image ship = new ImageIcon("Resources/Cruiser.png").getImage();
		if (super.isHorizontal() == false)
		{
			ship = new ImageIcon("Resources/CruiserRotated.png").getImage();
		}
		return ship;
	}
	
}
