package major;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class MeanOut {

	public static void main(String[] args) throws Exception{
		
		BufferedReader line=null;
		String str =null;
		int mean[] =null;
		int sum =0;
		int count =0;
		
		HashMap<String, Integer> model = null;
		HashMap<Integer, HashMap<String,Integer>> year =  new HashMap<Integer, HashMap<String,Integer>>();
		
		HashMap<String, Integer> modelcount = null;
		HashMap<Integer, HashMap<String,Integer>> yearcount =  new HashMap<Integer, HashMap<String,Integer>>();
		
		File folder = new File("c:\\users\\vikas\\desktop\\out1");
		File listOFFiles[] = folder.listFiles();
		for(File manufacturer : listOFFiles)
		{	//sum
			//Model,Year,KMS,Price
			//Manufacturer,Model,Price,Year,KMS
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				String[] details = str.split(",");
				
				if(!year.containsKey(Integer.parseInt(details[3].trim())))
				{ 
					model = new HashMap<String, Integer>();
					sum= Integer.parseInt(details[2].trim());
					model.put(details[1].trim(),sum);
					year.put(Integer.parseInt(details[3].trim()), model);
				}
				else
				{
					model =year.get(Integer.parseInt(details[3].trim()));
					if(model.containsKey(details[1].trim()))
					{
						sum= model.get(details[1].trim());
						sum = sum+Integer.parseInt(details[2].trim());
						model.put(details[1],sum);
						year.put(Integer.parseInt(details[3]), model);
					}
					else
					{
					sum = Integer.parseInt(details[2].trim());
					model.put(details[1].trim(),sum);
					year.put(Integer.parseInt(details[3].trim()), model);
					}
				}
			}
			
			//count
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				String[] details = str.split(",");
				if(!yearcount.containsKey(Integer.parseInt(details[3].trim())))
				{
					modelcount = new HashMap<String, Integer>();
					count=1;
					modelcount.put(details[1].trim(),count);
					yearcount.put(Integer.parseInt(details[3].trim()), modelcount);
					
				}
				else
				{
					modelcount =yearcount.get(Integer.parseInt(details[3].trim()));
					if(modelcount.containsKey(details[1].trim()))
					{
						count =modelcount.get(details[1].trim());
						count = count+1;
						modelcount.put(details[1],count);
						yearcount.put(Integer.parseInt(details[3]), modelcount);
					}
					else
					{
					count = 1;
					modelcount.put(details[1].trim(),count);
					yearcount.put(Integer.parseInt(details[3]), modelcount);
					}
				}
			}
			
			PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\meanout1\\"+manufacturer.getName(),true));
			bo.println("Manufacturer,"+"Year,"+"Model,"+"count,"+"Sum,"+"Mean");
			System.out.println("Manufacturer,"+"Year,"+"Model,"+"count,"+"Sum,"+"Mean");
			for(Map.Entry<Integer, HashMap<String, Integer>> yearEntry : year.entrySet())
			{
				int yearout = yearEntry.getKey(); 
				HashMap<String, Integer> modelmap =yearEntry.getValue();
				HashMap<String, Integer> modelofcount =yearcount.get(yearout);
				for(Map.Entry<String, Integer> modelEntry : modelmap.entrySet())
				{
					String modelout = modelEntry.getKey();
					int sumout = modelEntry.getValue();
					int countout = modelofcount.get(modelout);
					
					int meanout = sumout/countout;
					bo.println(manufacturer.getName().replace(".csv", "")+","+yearout+","+modelout+","+countout+","+sumout+","+meanout);
					System.out.println(manufacturer.getName().replace(".csv", "")+","+yearout+","+modelout+","+countout+","+sumout+","+meanout);
					
				}
			}
			model.clear();
			year.clear();
			modelcount.clear();
			yearcount.clear();
			
		}

	}



}
