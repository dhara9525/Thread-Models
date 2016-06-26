package Team_Model;
/*
 * This class is used to create a XML document from the CSV file(taken as input) and stores the XML File 
 * in the Output folder in the working directory and that path is defined in the class itself.
 * This class first creates a Document,processes that document and transforms it in the final tree structure of XML
 */
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*; 

public class XMLWriter
{   private static int number = 0;
	public String path = null; 
    //Creating the empty XML Document Format using the XML builders and its factory methods
	//and returns that document
	public Document createDoc() throws IOException, ParserConfigurationException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		return doc;
	}
	
	//The document format received from the above function and the file to be converted to XML are considered in this 
	//function
	public void processDoc(Document doc, String sfile) throws Exception
	{
		FileReader fileReader = new FileReader(sfile);
		// Always wrap FileReader in BufferedReader.
        BufferedReader reader = new BufferedReader(fileReader);
        
        //Extract the headers and the nodes from the incoming file 
        String line = reader.readLine();
        String[] headers = line.split(",");
        line = reader.readLine();
        String[] nodes = line.split(",");
        
		Element root;
		Element child;
		
		//Appends the header to the XML document
		root = doc.createElement(headers[0]);
		doc.appendChild(root);
		
		//Reads the file line by line,appends the tag and the text of the elements to the document 
        while ((line=reader.readLine()) != null) 
        {    		
        	child = doc.createElement(headers[1]);
        	String[] data = line.split(",");
        	
        	//Creates the tag and text to be inserted
        	for (int x=0; x<nodes.length; ++x)
        	{
				Element c = doc.createElement(nodes[x]);  
				c.appendChild(doc.createTextNode(data[x]));
				child.appendChild(c);
        	}
        	//Appends the child created above to the root
        	root.appendChild(child);
        }	
        
        //Close the Buffered file reader 
        reader.close();
	}
	
	//Create XML from the processed document i.e. transform the source tree obtained into the required tree	using some
	//predefined classes for transformation
	public void createXML(Document doc) throws Exception
	{
		//TransformerFactory instance is used to create Transformer objects. 
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		// create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		String xmlString = sw.toString();
		number++;
		path = "Output/XML_Output" + "_" + number +".xml";
		
		File f = new File(path);
		f.getParentFile().mkdirs(); 
		f.createNewFile();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
		bw.write(xmlString);
		bw.flush();
		bw.close();		
	}

	//Calls to the bove functions to create document,process it and transform to the final XML Document
	public boolean createXmlDocument(String sfile) throws Exception 
	{
		Document doc = createDoc();
		processDoc(doc,sfile);
		createXML(doc);
		return doc.hasChildNodes();
	}
}