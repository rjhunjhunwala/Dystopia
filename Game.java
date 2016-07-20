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

	public static Car[] cars = new Car[Map.citySize * Map.citySize / 10];

	public static void makeCars(){
		for (int i = 0; i < cars.length; i++) {
			cars[i] = new Car();
		}
	}

	@Override
	public void run() {
		makeCars();
		Cop.bounty= 0;
		Cop.numCops=10;
		int i = 0;
			Cop.createCops();
		while (TitleFrame.playing.get()) {
			if(i%50==0){
				for(int j = 0; j<10;j++)
				Cop.addCop();
			}
			Cop.bounty+=100;
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
			}
			for (Car c : cars) {
				c.move();
			}
			Cop.moveAllCops();
		i++;
		}
	}
}
