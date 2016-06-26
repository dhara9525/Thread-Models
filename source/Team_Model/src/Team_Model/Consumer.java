package Team_Model;
/*
 * 4 different threads are executed.
 * 1st thread:Converts CSV file to XML file
 * 2nd thread:Zip files(creates .gz file)]
 * 3rd thread:Plays Music
 * 4th thread:Extracts file from Zip folder
 * These threads call their respective functions and each thread does only one type of job mentioned above.
 * We can add more functionalities as per requirements. The inputs are consumed as and when producer produces it.
 * After processing one input if there are more inputs in their respective queues then the thread considers
 * the next input from the queue and starts working on it.
 */
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

//Consumer Class
class Consumer implements Runnable
{
	private final BlockingQueue<String> sharedQueue;
	private GUIDisplay disp;
	public Consumer (BlockingQueue sharedQueue,GUIDisplay disp) 
	{
		this.sharedQueue = sharedQueue;
		this.disp = disp;
	}

	@Override
	public void run() 
	{ 
		while(true){
			//Convert to XML file
			if(Thread.currentThread().getName().contains("1")){
				if(!sharedQueue.isEmpty()){
					try{
						XMLWriter xml = new XMLWriter();
						//Create XML document from the input specified in the call
						if (xml.createXmlDocument(sharedQueue.take())){
							disp.ta1.append("Converting to XML \n ");
							disp.ta1.append("XML File created at location: "+ xml.path +"\n");
							Thread.sleep(5000);//Sleep just to show it on display that threads are moving parallely
						}
						else{
							disp.ta1.append("XML file cannot be created \n");
						}
					}
					catch(Exception e)
					{
						disp.ta1.append("XML file cannot be created \n");
					}
				}
			}
			//Zip the file
			else if(Thread.currentThread().getName().contains("2")){//Zip File
				if(!sharedQueue.isEmpty()){
					Zip jzip = new Zip();
					disp.ta2.append("Zipping file \n");
					try 
					{
						//Zip file
						int value = jzip.WriteZip(sharedQueue.take());
						if(value>0){
							disp.ta2.append("Zip file created at location:"+jzip.filename +"\n");
						}
						else{
							disp.ta2.append("Zip file cannot be created \n");
						}
						Thread.sleep(2500);//Sleep just to show it on display that threads are moving parallely

					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						disp.ta2.append("Zip file cannot be created \n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						disp.ta2.append("Zip file cannot be created \n");
					}
				}}
			//Play Music 
			else if(Thread.currentThread().getName().contains("3")){
				if(!sharedQueue.isEmpty()){
					try {
						PlayMusic player = new PlayMusic();
						disp.ta3.append("Playing music \n");
						
						//Play music-audio file is given as input
						String played = player.play(sharedQueue.take());
						Thread.sleep(1000);//Sleep just to show it on display that threads are moving parallely

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
			
			//Extract Files
			else if(Thread.currentThread().getName().contains("4")){
				if(!sharedQueue.isEmpty()){
					//disp.ta4.append("\n Thread4");
					try {
						Unzipping unzip = new Unzipping();
						disp.ta4.append("Extracting File \n");
						String path = unzip.unZipFile(sharedQueue.take());
						if(! path.isEmpty()){
							disp.ta4.append("Extracted files at location:" + path + "\n");
						}
						else{
							disp.ta4.append("Error in Unzipping File \n");
						}
						Thread.sleep(5000);//Sleep just to show it on display that threads are moving parallely
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}}

		}
	}

}

