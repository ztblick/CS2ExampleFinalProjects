package AlexBattleship;
import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Carrier extends Ship {
	private static final int SHIP_LENGTH = 5;
	
	public Carrier(int startX, int startY)
	{
		super(SHIP_LENGTH, startX, startY, "Carrier");
	}
	
	public Image getShipImage(Graphics g)
	{
		Image ship = new ImageIcon("Resources/Carrier.png").getImage();
		if (super.isHorizontal() == false)
		{
			ship = new ImageIcon("Resources/CarrierRotated.png").getImage();
		}
		return ship;
	}

}
