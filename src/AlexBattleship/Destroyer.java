package AlexBattleship;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Destroyer extends Ship {
	private static final int SHIP_LENGTH = 2;

	public Destroyer(int startX, int startY) {
		super(SHIP_LENGTH, startX, startY, "Destroyer");
	}
	
	public Image getShipImage(Graphics g)
	{
		Image ship = new ImageIcon("Resources/Destroyer.png").getImage();
		if (super.isHorizontal() == false)
		{
			ship = new ImageIcon("Resources/DestroyerRotated.png").getImage();
		}
		return ship;
	}
}
