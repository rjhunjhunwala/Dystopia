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
public class Map {

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
		public static BufferedImage grassS;
    public static BufferedImage carS;
		static {
			try {
				grassS = ImageIO.read(new File("Grass.png"));
				spaceS = ImageIO.read(new File("Empty.png"));
				wallS = ImageIO.read(new File("Wall.png"));
				playerS = ImageIO.read(new File("Player.png"));
			  carS = ImageIO.read(new File("Car.png"));
			} catch (Exception ex) {
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.green);
			g.fillRect(0, 0, 650, 650);
			for (int y = Player.y - 10; y <= Player.y + 10; y++) {
				for (int x = Player.x + 10; x >= Player.x - 10; x--) {
					BufferedImage b = grassS;
					if (inMapBounds(x, y)) {
						switch (map[x][y]) {
							case grass:
								b = grassS;
								break;
							case space:
								b = spaceS;
								break;
							case wall:
								b = wallS;
								break;
							case car:
								b = carS;
								break;
						}
					}

					int i = x - Player.x + 10;
					int j = y - Player.y + 10;
					g.drawImage(b, (i + j) * 15, ((j - i) * 15) + 350 - b.getHeight(), this);
					if (x == Player.x && y == Player.y) {
						i = 10;
						j = 10;
						g.drawImage(playerS, (i + j) * 15, ((j - i) * 15) + 350 - playerS.getHeight(), this);
					}
				}
			}

			g.setColor(Color.blue);
			//g.fillOval(300,300,30,30);
			//		g.drawImage(space, 315, 315, this);
		}
	}

	public static class Player {

		public static int x = mult, y = mult;
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
			if (inMapBounds(tX, tY) && map[tX][tY] != wall&&map[tX][tY]!=car) {
				x = tX;
				y = tY;
			}
		}
	}

	public static class GameFrame extends JFrame {

		private static final long serialVersionUID = 1L;

		public GameFrame() {
			super("Dystopia");
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
		makeCity();
		GameFrame frame = new GameFrame();
		    new Thread(new Game()).start();
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
	public static final int mult = 5;
	public static final int grass = 'G';
	public static final int space = 'S';
	public static final int wall = 'W';
	public static final int car = 'C';
	public static final int citySize = 39;

	public static void makeCity() {
		maze = new boolean[citySize][citySize];
		nodes.push(new Coord(1, 1));
		while (!nodes.empty()) {
			findNewNode();
		}
		map = new int[maze.length * mult][maze[0].length * mult];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = !maze[i / mult][j / mult] ? (Math.random()<.3?grass:wall): space;
			}
		}
		for (int i = 0; i < 350; i++) {
			int x = (int) (Math.random() * (citySize-2)) + 1;
			int y = (int) (Math.random()*(citySize-2))+1;
			if ((x%2==1||y%2==1)&&!maze[x][y]) {
				for (int j = 0; j < mult; j++) {
					for (int k = 0; k < mult; k++) {
						map[mult * x + j][mult * y + k] = grass;
					}
				}
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
