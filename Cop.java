/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dystopia;

/**
 * Todo code this cop
 * @author Rohans
 */
public class Cop {

public static Cop[] cops;
public static void createCops(){
	cops = new Cop[30];
for(int i = 0;i<cops.length;i++){
int aX=(int) (Math.random()*Map.map.length);	
int aY=(int) (Math.random()*Map.map[0].length);	
while(Map.map[aX][aY]!=Map.space){
	aX=(int) (Math.random()*Map.map.length);	
  aY=(int) (Math.random()*Map.map[0].length);	
}
cops[i]= new Cop();
cops[i].x=aX;
cops[i].y=aY;
cops[i].standingOn=Map.map[aX][aY];
Map.map[aX][aY]=Map.cop;
}
}
public static void moveAllCops(){
	floodFillMap(Map.Player.x,Map.Player.y);
for(Cop c:cops){
	int tX=c.x,tY=c.y;
  Map.map[c.x][c.y]=c.standingOn;
int shortestPath= Integer.MAX_VALUE;
if(navigationMap[c.x+1][c.y]<shortestPath){
	tX=c.x+1;
	tY=c.y;
	shortestPath = navigationMap[tX][tY];
}
if(navigationMap[c.x][c.y+1]<shortestPath){
	tX=c.x;
	tY=c.y+1;
	shortestPath = navigationMap[tX][tY];
}
if(navigationMap[c.x][c.y-1]<shortestPath){
	tX=c.x;
	tY=c.y-1;
	shortestPath = navigationMap[tX][tY];
}
if(navigationMap[c.x-1][c.y]<shortestPath){
	tX=c.x-1;
	tY=c.y;
	shortestPath = navigationMap[tX][tY];
}
c.x=tX;
c.y=tY;
c.standingOn = Map.map[c.x][c.y]==Map.cop?(Map.maze[c.x/Map.mult][c.y/Map.mult]?Map.space:Map.grass):Map.map[c.x][c.y];
Map.map[tX][tY]=Map.cop;
if(c.x==Map.Player.x&&c.y==Map.Player.y){
	//todo calc score and display message
	TitleFrame.playing.set(false);
}

}
}
public static int[][] navigationMap;

	/**
	 * We are using an integer matrix to represent the distance each spot if from
	 * the player The position being attempted to be reached is one Non reachable
	 * positions or walls or represented by Integer.MAX_VALUE The reset of the
	 * positions represent the distance needed to travel in the maze this is to be
	 * used for efficient navigation
	 *
	 * @param inX
	 * @param inY
	 * @
	 */
	public static void floodFillMap(int inX, int inY) {
		//System.out.println(inX+"|"+inY);

		navigationMap = new int[Map.map.length][Map.map[0].length];
		//System.out.println(navigationMap.length+"|"+navigationMap[0].length);
		markOpen(inX, inY, 1);
		for (int i = 0; i < navigationMap.length; i++) {
			for (int j = 0; j < navigationMap[0].length; j++) {
				if (navigationMap[i][j] == 0) {
					navigationMap[i][j] = Integer.MAX_VALUE;
				}
			}
		}

	}
	boolean upMine = false;
	boolean sideMine = false;

	public static void markOpen(int x, int y, int n) {
		if (Map.inMapBounds(x, y)) {
			navigationMap[x][y] = n;
		} else {
			return;
		}
		if(n>100){
			return;
		}
		if (Map.inMapBounds(x + 1, y) && (navigationMap[x + 1][y] == 0 || n + 1 < navigationMap[x + 1][y]) && (Map.map[x + 1][y] == Map.space || Map.map[x + 1][y] == Map.grass)) {
			markOpen(x + 1, y, n + 1);
		}

		if (Map.inMapBounds(x - 1, y) && (navigationMap[x - 1][y] == 0 || n + 1 < navigationMap[x - 1][y]) && (Map.map[x - 1][y] == Map.space || Map.map[x - 1][y] == Map.grass)) {
			markOpen(x - 1, y, n + 1);
		}

if (Map.inMapBounds(x, y + 1) && (navigationMap[x][y + 1] == 0 || n + 1 < navigationMap[x][y + 1]) && (Map.map[x][y + 1] == Map.space || Map.map[x][y + 1] == Map.grass)) {
			markOpen(x, y + 1, n + 1);
		}

		if (Map.inMapBounds(x, y - 1) && (navigationMap[x][y - 1] == 0 || n + 1 < navigationMap[x][y - 1]) && (Map.map[x][y - 1] == Map.space || Map.map[x][y - 1] == Map.grass)) {
			markOpen(x, y - 1, n + 1);
		}
	}
	public int standingOn;
	public int x, y;

	/**
	 * Create a cop at a random location and ensure that he is placed on an open
	 * spot (grass or road)
	 */
	public Cop() {
		int aX = (int) (Math.random() * Map.map.length);
		int aY = (int) (Math.random() * Map.map[0].length);
		while (!Map.maze[aX / Map.mult][aY / Map.mult]) {
			aX = (int) (Math.random() * Map.map.length);
			aY = (int) (Math.random() * Map.map[0].length);
		}
		x = aX;
		y = aY;
		standingOn = Map.map[x][y]==Map.cop?(Map.maze[x/Map.mult][y/Map.mult]?Map.space:Map.grass):Map.map[x][y];
		Map.map[x][y] = Map.cop;
	}
}
