package major;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Remove {
//remove lesser than 6
	public static void main(String[] args) throws Exception{
		//file to record removed data
		PrintStream bolessthan6removed = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\removelessthan6data.csv"));
		bolessthan6removed.println("Manufacturer,"+"Model,"+"Price,"+"Year,"+"KMS");
		
		ArrayList<String> remove = new ArrayList<String>();
		BufferedReader line=null;
		String str =null;
		TreeMap<String, Integer> countmap = new TreeMap<String, Integer>();
		TreeMap<String, Set<Integer>> setmap = new TreeMap<String, Set<Integer>>();
		
		File folder = new File("c:\\users\\vikas\\desktop\\separatenew");
		File listOFFiles[] = folder.listFiles();
		for(File manufacturer : listOFFiles)
		{
			//count
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				//Manufacturer,Model,Price,Year,KMS
				String[] details = str.split(",");
				if(!countmap.containsKey(details[1].trim()))
				{
					int count=1;
					countmap.put(details[1].trim(), count);
				}
				else
				{
					int count = countmap.get(details[1].trim());
					countmap.put(details[1].trim(),++count);
					
				}
			
			}
			
			for(Map.Entry<String, Integer> entry: countmap.entrySet())
			{
				if(entry.getValue()<6)
				remove.add(entry.getKey());
			}
			
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\remove\\"+manufacturer.getName()));
			bo.println("Manufacturer,"+"Model,"+"Price,"+"Year,"+"KMS");
			
			bo.flush();
			while((str = line.readLine())!=null)
			{
				//Manufacturer,Model,Price,Year,KMS
				String[] details = str.split(",");
				if(!remove.contains(details[1].trim()))
				{
					bo.println(str);
					bo.flush();
				}
				else
				{
					bolessthan6removed.println(str);
				}
			
			}
			
		
			
			countmap.clear();
			
		}
	}

	
}
