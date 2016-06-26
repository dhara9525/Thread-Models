package Team_Model;
/*
 * This class is used to create Zip files(.gz file) using the standard library Zip.
 * It takes in a file as input.The .gz zip file is created in the Output folder in the
 * working directory and that path is defined in the class itself.
 */
import java.io.*;
import java.util.zip.*;

public class Zip
{	private static int number = 0;        //To note the number of the file created
	public String filename = null;
	public int WriteZip(String sfile)throws IOException 
	{	
		number++;
		
		//Read the file using BufferedReader
		BufferedReader in = new BufferedReader(new FileReader(sfile));
		filename = "Output/Zip_output" + "_" + number +".gz";
		
		//Created the BufferedOutputStream using instance of GZIPOutputStream as a parameter
		BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(filename)));
		
		//Read character by character and store it in .gz file
		int c;
		while((c = in.read()) != -1)
			out.write(c);
		
		//Close the input and output streams
		in.close();
		out.close();
		
		return number;
	}
}