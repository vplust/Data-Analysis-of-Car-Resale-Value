package major;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class TempCount {

	public static void main(String[] args) throws Exception{
		
		BufferedReader line=null;
		String str =null;
		TreeMap<String, Integer> countmap = new TreeMap<String, Integer>();
		TreeMap<String, Set<Integer>> setmap = new TreeMap<String, Set<Integer>>();
		
		File folder = new File("c:\\users\\vikas\\desktop\\out3");
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
				{
					System.out.println(manufacturer.getName());
				System.out.println(entry.getKey()+" ");
				}
			}
			
		
			
			countmap.clear();
			
		}
	}

}
