/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dystopia;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rohans
 */
public class Game implements Runnable {

	public Car[] cars = new Car[Map.citySize * Map.citySize / 10];

	{
		for (int i = 0; i < cars.length; i++) {
			cars[i] = new Car();
		}
	}

	@Override
	public void run() {
    new Thread(new Runnable(){
			public void run(){
				try {
					Thread.sleep(2500);
				} catch (InterruptedException ex) {
					Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
				}
				for(int i = 0;i<Map.map.length;i++){
					for(int j = 0;j<Map.map[i].length;j++){
						if(Map.map[i][j]==Map.cop){
							Map.map[i][j]=Map.space;
						}
					}
				}
			}
		}).start();
			Cop.createCops();
		while (TitleFrame.playing.get()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
			}
			for (Car c : cars) {
				c.move();
			}
			Cop.moveAllCops();
		}
	}
}
