package major;

import java.io.*;
import java.util.*;

public class Outlier {
// 1standard deviation
	public static void main(String[] args) throws Exception {
		BufferedReader line=null;
		String str =null;
		int outIndex=0;
		int removed =0;
		
		HashMap<String, int[]> model = null;
		HashMap<Integer, HashMap<String,int[]>> year =  new HashMap<Integer, HashMap<String,int[]>>();
		int limit[]=null;
		
		File folder = new File("c:\\users\\vikas\\desktop\\sd");
		File listOFFiles[] = folder.listFiles();
		File outfolder = new File("c:\\users\\vikas\\desktop\\separate");
		File outListOFFiles[] = outfolder.listFiles();
		
		for(File manufacturer : listOFFiles)
		{
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				String[] details = str.split(",");
				// 0Manufacturer,1Year,2Model,3count,4S.D,5Mean
				if(!year.containsKey(Integer.parseInt(details[1].trim())))
				{
					model = new HashMap<String, int[]>();
					limit =new int[2];
					limit[0]= Integer.parseInt(details[5].trim())-(1*Integer.parseInt(details[4].trim()));
					limit[1]= Integer.parseInt(details[5].trim())+(1*Integer.parseInt(details[4].trim()));
					model.put(details[2].trim(),limit);
					year.put(Integer.parseInt(details[1].trim()), model);
				}
				else
				{
					model =year.get(Integer.parseInt(details[1].trim()));
					if(model.containsKey(details[2].trim()))
					{
						limit =new int[2];
						limit[0]= Integer.parseInt(details[5].trim())-(1*Integer.parseInt(details[4].trim()));
						limit[1]= Integer.parseInt(details[5].trim())+(1*Integer.parseInt(details[4].trim()));
					model.put(details[2].trim(),limit);
					year.put(Integer.parseInt(details[1].trim()), model);
					}
					else
					{
						limit =new int[2];
						limit[0]= Integer.parseInt(details[5].trim())-(1*Integer.parseInt(details[4].trim()));
						limit[1]= Integer.parseInt(details[5].trim())+(1*Integer.parseInt(details[4].trim()));
					model.put(details[2].trim(),limit);
					year.put(Integer.parseInt(details[1].trim()), model);
					}
				}
			}
			
			
			line = new BufferedReader(new FileReader(outListOFFiles[outIndex]));
			str = line.readLine();
			PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\out1\\"+manufacturer.getName()));
			bo.println("Manufacturer,"+"Model,"+"Price,"+"Year,"+"KMS");
			System.out.println("Manufacturer,"+"Model,"+"Price,"+"Year,"+"KMS");
			
			while((str = line.readLine())!=null)
			{
				
				String[] details = str.split(",");
				//0Model,1Year,2KMS,3Price
				limit=year.get(Integer.parseInt(details[1].trim())).get(details[0].trim());
				
				int price = Integer.parseInt(details[3].trim());
				
				if(price>=limit[0] && price<=limit[1])
				{	
					
					String modelout = details[0];
					int yearout = Integer.parseInt(details[1].trim());
					int priceout = Integer.parseInt(details[3].trim());
					int kmsout = Integer.parseInt(details[2].trim());
					
					//"Manufacturer,"+"Model,"+"Price,"+"Year,"+"KMS"
					bo.println(manufacturer.getName().replace(".csv", "")+","+modelout+","+priceout+","+yearout+","+kmsout);	
				}
				else
				{
					++removed;
					String modelout = details[0];
					int yearout = Integer.parseInt(details[1].trim());
					int priceout = Integer.parseInt(details[3].trim());
					int kmsout = Integer.parseInt(details[2].trim());
					System.out.println(manufacturer.getName().replace(".csv", "")+","+modelout+","+priceout+","+yearout+","+kmsout);
				}
			}
			
			System.out.println(removed);
			++outIndex;
			model.clear();
			year.clear();
			
		}
		outIndex=0;
		
	}

}
