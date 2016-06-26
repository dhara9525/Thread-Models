package Team_Model;
/*
 * This is the driver class.We create one producer here and n consumers depending
 * on n functions which a team has to do. Here we are considering 4 
 * functions and hence create 4 consumers. All the producer and consumer threads
 * are started and also the GUIDisplay object is created and GUI is displayed.
 *Team Model:
 *For this implementation it is considered that only 4 types of requests comes in:
 *1.Converting CSV file to XML file
 *2.Zip file
 *3.Playing some music
 *4.Extract files from zip file
 *Each job is done by a specialized thread and these threads form a team.
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;

public class TeamModel {

	public static void main(String[] args){

		//Blocking Queue data structure
		BlockingQueue<String>[] sharedQueue = new LinkedBlockingQueue[4];
		for(int i = 0;i<4;i++){
			sharedQueue[i] = new LinkedBlockingQueue<String>();
		}
		
		//Call the GUI display class to display the GUI
		GUIDisplay disp = new GUIDisplay();
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Display the window.
        disp.setVisible(true);
		
        //Creating Producer Thread
        Thread prodThread = new Thread(new ProducerData(sharedQueue[0],sharedQueue[1],sharedQueue[2],sharedQueue[3]));
		
        //Start Producer Thread
        prodThread.start();
        
        //Create Consumer Thread,Name it and Start Threads
		Thread consThread[] = new Thread[4];
		for(int i = 0;i<4;i++){
			//Creating Consumer Thread
			consThread[i] = new Thread(new Consumer(sharedQueue[i],disp));
			consThread[i].setName("Thread" + (i+1));
			consThread[i].start();
		}
		System.out.println("Waiting for threads to finish.");

	}
}

