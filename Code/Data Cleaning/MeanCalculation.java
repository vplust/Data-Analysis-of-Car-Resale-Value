package major;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class MeanCalculation {

	public static void main(String[] args) throws Exception{
		
		BufferedReader line=null;
		String str =null;
		int mean[] =null;
		int sum =0;
		int count =0;
		//0Model,1Year,2KMS,3Price
		HashMap<String, Integer> model = null;
		HashMap<Integer, HashMap<String,Integer>> year =  new HashMap<Integer, HashMap<String,Integer>>();
		
		HashMap<String, Integer> modelcount = null;
		HashMap<Integer, HashMap<String,Integer>> yearcount =  new HashMap<Integer, HashMap<String,Integer>>();
		
		File folder = new File("c:\\users\\vikas\\desktop\\separate");
		File listOFFiles[] = folder.listFiles();
		for(File manufacturer : listOFFiles)
		{	//sum
			//0Model,1Year,2KMS,3Price
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				String[] details = str.split(",");
				
				if(!year.containsKey(Integer.parseInt(details[1].trim())))
				{ 
					model = new HashMap<String, Integer>();
					sum= Integer.parseInt(details[3].trim());
					model.put(details[0].trim(),sum);
					year.put(Integer.parseInt(details[1].trim()), model);
				}
				else
				{
					model =year.get(Integer.parseInt(details[1].trim()));
					if(model.containsKey(details[0].trim()))
					{
						sum= model.get(details[0].trim());
						sum = sum+Integer.parseInt(details[3].trim());
						model.put(details[0],sum);
						year.put(Integer.parseInt(details[1]), model);
					}
					else
					{
					sum = Integer.parseInt(details[3].trim());
					model.put(details[0].trim(),sum);
					year.put(Integer.parseInt(details[1].trim()), model);
					}
				}
			}
			
			//count
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				String[] details = str.split(",");
				if(!yearcount.containsKey(Integer.parseInt(details[1].trim())))
				{
					modelcount = new HashMap<String, Integer>();
					count=1;
					modelcount.put(details[0].trim(),count);
					yearcount.put(Integer.parseInt(details[1].trim()), modelcount);
					
				}
				else
				{
					modelcount =yearcount.get(Integer.parseInt(details[1].trim()));
					if(modelcount.containsKey(details[0].trim()))
					{
						count =modelcount.get(details[0].trim());
						count = count+1;
						modelcount.put(details[0],count);
						yearcount.put(Integer.parseInt(details[1]), modelcount);
					}
					else
					{
					count = 1;
					modelcount.put(details[0].trim(),count);
					yearcount.put(Integer.parseInt(details[1]), modelcount);
					}
				}
			}
			
			PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\mean\\"+manufacturer.getName(),true));
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
