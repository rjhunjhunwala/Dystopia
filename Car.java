/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dystopia;

/**
 *
 * @author Rohans
 */
public class Car{
public int x,y;
/**
 * Represents the direction this car is traveling
 *   3
 *   I
 * 2-O-0
 *   I
 *   1
 *   
 */
public int dir;
/**
 * Creates a car at a given location
 * @param inX
 * @param inY 
 */
	private Car(int inX,int inY){
	if(!Map.inMapBounds(inX,inY)){
		//something bad happenned....
		//um... just write to stderr and pretend this never happened
		System.err.println("Something bad happened while creating car");
		//just spawn this car somewhere inconspicuous
		x = Map.mult;
		y = Map.map.length-Map.mult;
	 //bottom left corner
	}else{
		x = inX;
		y = inY;
			}
	Map.map[x][y] = Map.car;
	dir = 0;
}
public Car(){

int xAttempt = (int) (Math.random()*Map.map.length);	
int yAttempt = (int) (Math.random()*Map.map[0].length);	
while(Map.map[xAttempt][yAttempt]!=Map.space){
xAttempt = (int) (Math.random()*Map.map.length);	
yAttempt = (int) (Math.random()*Map.map[0].length);		
}
x = xAttempt;
y = yAttempt;
Map.map[x][y]=Map.car;
if(Map.maze[(x/Map.mult)+1][(y/Map.mult)]){
	dir = 0;
}
else if(Map.maze[(x/Map.mult)][(y/Map.mult)+1]){
	dir = 1;
}else if(Map.maze[(x/Map.mult)-1][(y/Map.mult)]){
	dir = 2;
}else if(Map.maze[(x/Map.mult)][(y/Map.mult)+1]){
	dir = 1;
}
}
public void move(){
	Map.map[x][y]=Map.space;
	double angle = Math.PI/2*dir;
	int dX =(int) Math.cos(angle);
	int dY = (int) Math.sin(angle);
	if(Map.map[x+dX][y+dY]!=Map.space){
		dX*=-1;
		dY*=-1;
		dir+=2;
	}
	if(x+dX!=Map.Player.x||y+dY!=Map.Player.y){
	x+=dX;
	y+=dY;
	}
	Map.map[x][y]=Map.car;
}
}
