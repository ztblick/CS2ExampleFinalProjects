package AlexBattleship;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Battleship extends Ship {
	private static final int SHIP_LENGTH = 4;
	
	public Battleship(int startX, int startY)
	{
		super(SHIP_LENGTH, startX, startY, "Battleship");
	}
	
	public Image getShipImage(Graphics g)
	{
		Image ship = new ImageIcon("Resources/Battleship.png").getImage();
		if (super.isHorizontal() == false)
		{
			ship = new ImageIcon("Resources/BattleshipRotated.png").getImage();
		}
		return ship;
	}
}
