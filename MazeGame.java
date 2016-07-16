/**
 * Maze Game in under 200 lines. Decided it was time to clean up past code.
 * This implementation is much more efficient, and is much cleaner.
 */
package dystopia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Simple Maze Game
 *
 * @author rohan
 */
public class MazeGame {

	public static class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public GamePanel() {

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(620, 640);
		}
public static BufferedImage spaceS;
public static BufferedImage wallS;
public static BufferedImage playerS;
static{
try{
			spaceS = ImageIO.read(new File("Empty.png"));
			wallS=ImageIO.read(new File("Wall.png"));
playerS=ImageIO.read(new File("Player.png"));
}catch(Exception ex){}
}
@Override
		public void paintComponent(Graphics g) {
	g.setColor(Color.green);
			g.fillRect(0,0,650,650);
			for (int y = Player.y - 10; y <= Player.y + 10; y++) {
				for (int x = Player.x + 10; x >= Player.x - 10; x--){
					BufferedImage b;
          b = (!inMapBounds(x,y)||(map[x][y]==MazeGame.space) ? spaceS : wallS);
	if(x == Player.x&&y==Player.y){
		b= playerS;
	}
					int i = x-Player.x+10;
	int j = y-Player.y+10;
					g.drawImage(b, (i+j)*15,((j-i)*15)+350-b.getHeight(), this);
				}
			}
			g.setColor(Color.blue);
			//g.fillOval(300,300,30,30);
	//		g.drawImage(space, 315, 315, this);
		}
	}

	public static class Player {

		public static int x = 4, y = 4;
		private static final long serialVersionUID = 1L;

		public static void move(char m) {
			int tX = x, tY = y;
			switch (m) {
				case 'w':
					tY--;
					break;
				case 'a':
					tX--;
					break;
				case 's':
					tY++;
					break;
				case 'd':
					tX++;
					break;
			}
			if (inMapBounds(tX, tY) && map[tX][tY]!=wall) {
				x = tX;
				y = tY;
			}
		}
	}

	public static class GameFrame extends JFrame {

		private static final long serialVersionUID = 1L;

		public GameFrame() {
			super("Maze Game");
			this.add(new GamePanel());
			this.pack();
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyPressed(KeyEvent e) {
					Player.move(e.getKeyChar());
				}

				@Override
				public void keyReleased(KeyEvent e) {

				}
			});
		}
	}
	/**
	 * The boolean at maze[x][y] is whether or not that spot is open
	 */
	public static boolean[][] maze;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		makeMaze();
		GameFrame frame = new GameFrame();
		while (true) {
			frame.repaint();
		}
	}

	public static class Coord {

		public final int x, y;

		Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static Stack<Coord> nodes = new Stack<>();
public static int[][] map;
public static final int mult=3;
public static final int space = 'S';
public static final int wall='W';
public static void makeMaze() {
		maze = new boolean[65][65];
		nodes.push(new Coord(1, 1));
		while (!nodes.empty()) {
			findNewNode();
		}
		map= new int[65*3][65*3];
		for(int i=0;i<map.length;i++){
		      for(int j = 0;j<map[i].length;j++){
				map[i][j]=maze[i/3][j/3]?space:wall;
			}
		}
	}

	public static void findNewNode() {
		Coord c = nodes.peek();
		int x = c.x;
		int y = c.y;

		int startCase = (int) (Math.random() * 4);

		for (int i = 0; i < 4; i++) {
			int attemptX = x, attemptY = y;
			switch (startCase) {
				case 0:
					attemptX += 2;
					break;
				case 1:
					attemptX -= 2;
					break;
				case 2:
					attemptY += 2;
					break;
				case 3:
					attemptY -= 2;
					break;
			}
			if (inMazeBounds(attemptX, attemptY)) {
				if (!maze[attemptX][attemptY]) {
					maze[attemptX][attemptY] = true;
					maze[x + ((attemptX - x) / 2)][y + ((attemptY - y) / 2)] = true;
					nodes.push(new Coord(attemptX, attemptY));
					return;
				}
			}
			startCase += 1;
			startCase %= 4;
		}
		nodes.pop();
	}

	public static boolean inMazeBounds(int x, int y) {
		return x > 0 && y > 0 && x < maze.length && y < maze[x].length;
	}
	public static boolean inMapBounds(int x, int y) {
		return x > 0 && y > 0 && x < map.length && y < map[x].length;
	}
}
