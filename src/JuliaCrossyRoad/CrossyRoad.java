package JuliaCrossyRoad;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;


/**********************************************************
  
  CrossyRoad
  
  Created by:  [ Julia Lau ]
  Date:        [ May 28th, 2021 ]
  
  Description:
	CrossyRoad is a client class with a main method that creates an instance of a game of CrossyRoad. This class utilizes the Pixel, 
	Board, Sprite, Character, and Opponent classes and implements KeyListener for keyboard input from arrow keys to move an instance 
	of the Character class as well as MotionListener to move cars, instances of the opponent class. A Timer is created and each time 
	DELAY_IN_MIL passes, the paint method will be called, repainting the entire board. Buffer Strategy is also utilized to reduce 
	glitches in graphics. You can win and lose the game with a window that pops up signifying your outcome status.
	
 ***********************************************************/


public class CrossyRoad extends JFrame implements KeyListener, ActionListener{
	
	private Board crossyBoard;
	private Character player;
	private Opponent[] enemy;
	
	private static final int ROWS = 30;
	private static final int COLS = 32;
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 800 + 200; 
	private static final int TITLE_BAR = 38;
	private static final int BORDER = 20;
	private static final int EXTRA_TOP_SPACE = 20;
	private static final int SQUARE_LENGTH = (WINDOW_WIDTH-BORDER*2)/COLS;
	private static final int STEP_SIZE = 1;
	
	private static int DELAY_IN_MIL = 200; //start at 200	
	private static Timer timer;

	private static int outcome  = 0; // 0 = still playing, 1 = lose, 2 = win
	
	private static Color COLOR = new Color (166, 181, 96); 
	private static Color OUTLINEC = new Color (186, 198, 134);
	private static Color backscheme = new Color (175, 215, 234);
	
	public CrossyRoad () {
		
		player = new Character(SQUARE_LENGTH, (COLS-1)/2, 0, TITLE_BAR + EXTRA_TOP_SPACE, BORDER);
		enemy = new Opponent[6]; //number of cars
		
		int loc = 3;
		for (int i = 0; i < enemy.length; i ++) {
			enemy[i] = new Opponent(SQUARE_LENGTH, 0, loc, TITLE_BAR + EXTRA_TOP_SPACE, BORDER, COLS);
			loc = loc + (int) (Math.random() * 3) + 3; //randomly sets the row each enemy is located with a range of 3
		}
		
		crossyBoard = new Board (ROWS, COLS, BORDER, TITLE_BAR + EXTRA_TOP_SPACE, SQUARE_LENGTH, player, enemy, COLOR, OUTLINEC);
		
		setTitle("cRoSsY rOaD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true); 
		addKeyListener(this);
		
		timer = new Timer(DELAY_IN_MIL, this);
		timer.start();	
	}
	
	public static void main(String[] args) {
		
		CrossyRoad newGame = new CrossyRoad(); 
	
	}
	
	//paint methods ————————————————————————————————————————————————
	
	 public void paint(Graphics g) { //uses a Buffer Strategy
		  
		 BufferStrategy buffer = getBufferStrategy();
		 
		 if(buffer == null) {
			 createBufferStrategy(2);
			 return;
		 }
		 
		 Graphics g1 = buffer.getDrawGraphics();
		 newPainter(g1);
		 g.dispose();
		 buffer.show();
	 }
	 
	private void newPainter(Graphics g) {
		 
		 Image logo = new ImageIcon ("Resources/logoStraight.png").getImage();
		 Image tryagain = new ImageIcon ("Resources/tryagain.png").getImage();
		 
		 g.setColor(backscheme);
		 g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		 System.out.println(outcome);
		 crossyBoard.paint(g, this);
		 player.paint(g, this);
		 
	//paint outcomes
		 
		 g.drawImage(logo, 10, 765, 200, 40, this);
		 
		 if (outcome == 1) {
			 finalScreen(g);
			 Image lose = new ImageIcon ("Resources/youlose.png").getImage();
			 g.drawImage(lose, 200, 450, 400, 70, this);
			 g.drawImage(tryagain, 290, 650, 200, 50, this);
		 }
		 
		 else if (outcome == 2) {
			 finalScreen(g);
			 Image win = new ImageIcon ("Resources/youwin.png").getImage();
			 g.drawImage(win, 200, 450, 400, 70, this);
		 }
		 
		 if (!timer.isRunning()) {
			 finalScreen(g);

			 if (outcome == 2) {
				 Image win = new ImageIcon ("Resources/youwin.png").getImage();
				 g.drawImage(win, 200, 450, 400, 70, this);
			 }
			 
			 else {
				 Image lose = new ImageIcon ("Resources/youlose.png").getImage();
				 g.drawImage(lose, 200, 450, 400, 70, this);
				 g.drawImage(tryagain, 290, 650, 200, 50, this);
			 }
		 }
	 }
	 
	 private void finalScreen (Graphics g ) {
		 
		 Color back = new Color(175, 215, 234, 200); 
		 g.setColor(back);
		 g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	 }
	 
	@Override
	public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) { //for movement of Character
    	
    	int keyCode = e.getKeyCode();
    	boolean valid;
    	
    	if (keyCode == KeyEvent.VK_DOWN) {
    				
    		valid = crossyBoard.checkValid(STEP_SIZE, 0);
    		player.move(valid, 2, Color.WHITE, STEP_SIZE);
    	}
    	
    	if (keyCode == KeyEvent.VK_UP) {
    				
    		valid = crossyBoard.checkValid(-STEP_SIZE, 0);
    		player.move(valid, 0, Color.WHITE, -STEP_SIZE);	
    	}
    	
    	if (keyCode == KeyEvent.VK_RIGHT) {
    				
    		valid = crossyBoard.checkValid(STEP_SIZE, 1);
    		player.move(valid, 1, Color.WHITE, STEP_SIZE);	
    	}
    	
    	if (keyCode == KeyEvent.VK_LEFT) {
    				
    		valid = crossyBoard.checkValid(-STEP_SIZE, 1);
    		player.move(valid, 3, Color.WHITE, -STEP_SIZE);
    	}
    	
    	repaint();
    }  
    
    @Override
	public void actionPerformed(ActionEvent arg0) { //for Opponents (movement of the cars)
		
    	for (int i = 0; i < enemy.length; i ++) {
    		enemy[i].move();
    		
			if (outcome != 1) {
				outcome = crossyBoard.checkWin();
			}
			
			if (outcome != 2) {
				outcome = crossyBoard.hitEnemy();
			}
			
			if (outcome != 0) {
				timer.stop();
				removeKeyListener(this);
    		}
    	}
    
		repaint();
	}
}
