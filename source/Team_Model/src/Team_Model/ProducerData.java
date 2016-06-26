package Team_Model;
/*
 * This class reads an input file and as per the instructions in the file this class segregates data into
 * 4 different queues for each thread.Blocking Queue data structure is used for each thread.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerData implements Runnable 
{
	private final BlockingQueue<String>[] sharedQueue = new LinkedBlockingQueue[4];
	public ProducerData(BlockingQueue<String> sharedQueue0,
						BlockingQueue<String> sharedQueue1,
						BlockingQueue<String> sharedQueue2,
						BlockingQueue<String> sharedQueue3 ) 
	{
		//Initializing sharedQueue for each threads
		for(int i = 0;i<4;i++){
			sharedQueue[i] = new LinkedBlockingQueue<String>();
		}
		this.sharedQueue[0] = sharedQueue0;
		this.sharedQueue[1] = sharedQueue1;
		this.sharedQueue[2] = sharedQueue2;
		this.sharedQueue[3] = sharedQueue3;
	}

	@Override
	public void run() 
	{
		try{

			FileReader fileReader = new FileReader("Input/inputfile.txt");
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			//Read instructions from the file one by one and fill the sharedQueue with the inputs specified in the 
			//inputfile.txt for each thread.
			while((line = bufferedReader.readLine()) != null){
				if(line.equals("XML")){
					sharedQueue[0].put(bufferedReader.readLine());
				}
				else if(line.equals("ZIP")){
					sharedQueue[1].put(bufferedReader.readLine());
				}
				else if(line.equals("SOUND")){
					sharedQueue[2].put(bufferedReader.readLine());
				}
				else if(line.equals("UNZIP")){
					sharedQueue[3].put(bufferedReader.readLine());
				}
			}

		}
		catch(IOException ex) {
			System.out.println("Error reading file ");  
			Logger.getLogger(ProducerData.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
