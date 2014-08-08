package automate;

import java.util.ArrayList;
import java.io.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XML {
	
	String[] URL = new String[10000];
	int[] weight = new int[10000];
	int index = 0;

	public static void main(String[] args) throws IOException, DocumentException
	{
		new XML();
	}
	
	public XML() throws IOException, DocumentException
	{
		File file = new File("assets/file.txt");
		ArrayList stack = new ArrayList();
		
		
		//Hashtable<Integer, String> siteTable = new Hashtable<Integer, String>();
		
		String xml="";
		DataInputStream istream = new DataInputStream(new FileInputStream(file));
		//System.out.println(istream.readLine());
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	xml+=line+"\n";
		    	if(line.startsWith("<") && !line.startsWith("</"))
		    	{
		    		stack.add(line);
		    	}
		    	else if(line.startsWith("</"))
		    	{
		    		String fix = ((String)stack.get(stack.size()-1)).replace('<', ' ');
		    		fix = fix.replace('>', ' ');
		    		URL[index] = fix.trim();
		    		weight[index] = stack.size();
		    		stack.remove(stack.size()-1);
		    		index++;
		    	}
		    }
		    // line is not visible here.
		}
		for(int i=index-1;i>=0;i--)
			System.out.println(URL[i] + ", " +weight[i]);
		//System.out.println(xml);
	}
	public int getWeight(int index)
	{
		return weight[index];
	}
	public String getURL(int index)
	{
		return URL[index];
	}
	public int getIndex()
	{
		return index;
	}
	
}