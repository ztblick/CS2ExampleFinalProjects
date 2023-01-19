package AlexBattleship;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Submarine extends Ship{
	private static final int SHIP_LENGTH = 3;
	
	public Submarine(int startX, int startY) {
		super(SHIP_LENGTH, startX, startY, "Submarine");
	}
	
	public Image getShipImage(Graphics g)
	{
		Image ship = new ImageIcon("Resources/Submarine.png").getImage();
		if (super.isHorizontal() == false)
		{
			ship = new ImageIcon("Resources/SubmarineRotated.png").getImage();
		}
		return ship;
	}
}
