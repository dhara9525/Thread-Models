package Team_Model;
/*
 * This class extracts files from a zip file(taken as input) and stores the extracted
 * files in the Output folder in the working directory.
 * The standard libraries of zip are used in this class.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipping {

	public String unZipFile(String sfile) throws IOException {
		byte[] buffer = new byte[1024];
		ZipEntry entry = null;
		FileOutputStream out = null;
		int number = 1;
		String path = null;
		try {

			// Opening file to read
			File file = new File(sfile);
			ZipInputStream input = new ZipInputStream(new FileInputStream(file));

			// Read the zip file and check if its a file or folder and extract
			// them accordingly from
			// the zip file
			while ((entry = input.getNextEntry()) != null) {
				String dir = entry.getName();
				try {
					String fileSeparator = dir.substring(dir.length() - 1);
					if (fileSeparator.equals("/") || fileSeparator.equals("\\")) {
						// Its a folder
						dir = dir.substring(0, dir.lastIndexOf(fileSeparator) + 1);
						if (number == 1) {
							path = "Output/" + dir.substring(0,dir.indexOf(fileSeparator));
							number++;
						}
						dir = "Output/" + dir;
						(new File(dir)).mkdirs();
						continue;
					}
				} catch (Exception e) {
				}
				//Its a file
				out = new FileOutputStream("Output/" + entry.getName());
				if (number == 1) {
					path = "Output/" + entry.getName();
					number++;
				}
				int len = 0;
				
				//Extract the files and write 
				while ((len = input.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.close();
			}
			input.close();
			return path;
		} catch (IOException ex) {
			ex.printStackTrace();
			return path;
		}
	}
}
