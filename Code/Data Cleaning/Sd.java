package major;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class Sd {

	public static void main(String[] args) throws Exception{
	BufferedReader line=null;
	String str =null;
	int meanmap =0;
	int sum =0;
	int count =0;
	long sd =0;
	HashMap<String, Integer> meanmodel = null;
	HashMap<Integer, HashMap<String,Integer>> meanyear =  new HashMap<Integer, HashMap<String,Integer>>();
	
	
	HashMap<String, Integer> model = null;
	HashMap<Integer, HashMap<String,Integer>> year =  new HashMap<Integer, HashMap<String,Integer>>();
	
	HashMap<String, Integer> modelcount = null;
	HashMap<Integer, HashMap<String,Integer>> yearcount =  new HashMap<Integer, HashMap<String,Integer>>();
	
	File folder = new File("c:\\users\\vikas\\desktop\\separate");
	File listOFFiles[] = folder.listFiles();
	for(File manufacturer : listOFFiles)
	{
		//sum
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
		
		
		//mean
		for(Map.Entry<Integer, HashMap<String, Integer>> yearEntry : yearcount.entrySet())
		{
			int yearout = yearEntry.getKey(); 
			HashMap<String, Integer> modelofcount =yearEntry.getValue();
			HashMap<String, Integer> modelmap =year.get(yearout);
			for(Map.Entry<String, Integer> modelEntry : modelofcount.entrySet())
			{
				String modelout = modelEntry.getKey();
				int sumout = modelmap.get(modelout);
				
				int countout = modelEntry.getValue();
				
				int meanout = sumout/countout;
				if(!meanyear.containsKey(yearout))
				{
				meanmodel = new HashMap<String, Integer>();
				meanmodel.put(modelout, meanout);
				meanyear.put(yearout, meanmodel);
				}
				else
				{
					meanmodel=meanyear.get(yearout);
					meanmodel.put(modelout, meanout);
				}
			}
		}
		
		//standard deviation subtract and square only
		HashMap<String, Long> sdmodel = null;
		HashMap<Integer, HashMap<String,Long>> sdyear =  new HashMap<Integer, HashMap<String,Long>>();
		
		line = new BufferedReader(new FileReader(manufacturer));
		str = line.readLine();
		while((str = line.readLine())!=null)
		{
			String[] details = str.split(",");
			if(!sdyear.containsKey(Integer.parseInt(details[1].trim())))
			{
				sdmodel = new HashMap<String, Long>();
				System.out.println(Integer.parseInt(details[1].trim())+details[0].trim());
				meanmap=meanyear.get(Integer.parseInt(details[1].trim())).get(details[0].trim());
				sd= Integer.parseInt(details[3].trim())- meanmap;
				sd= sd*sd;
				sdmodel.put(details[0].trim(),sd);
				sdyear.put(Integer.parseInt(details[1].trim()), sdmodel);
			}
			else
			{
				sdmodel =sdyear.get(Integer.parseInt(details[1].trim()));
				if(sdmodel.containsKey(details[0].trim()))
				{
					sd= sdmodel.get(details[0].trim());
					meanmap=meanyear.get(Integer.parseInt(details[1].trim())).get(details[0].trim());
					long tempsd= Integer.parseInt(details[3].trim())- meanmap;
					tempsd= tempsd*tempsd;
					sd = sd+tempsd;
					sdmodel.put(details[0].trim(),sd);
					sdyear.put(Integer.parseInt(details[1].trim()), sdmodel);
				}
				else
				{
					meanmap=meanyear.get(Integer.parseInt(details[1].trim())).get(details[0].trim());
					sd= Integer.parseInt(details[3].trim())- meanmap;
					sd= sd*sd;
					sdmodel.put(details[0].trim(),sd);
					sdyear.put(Integer.parseInt(details[1].trim()), sdmodel);
				}
			}
		}
		
		// file output
		PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\sd\\"+manufacturer.getName()));
		bo.println("Manufacturer,"+"Year,"+"Model,"+"count,"+"S.D,"+"Mean");
		System.out.println("Manufacturer,"+"Year,"+"Model,"+"count,"+"S.D,"+"Mean");
		for(Map.Entry<Integer, HashMap<String, Long>> yearEntry : sdyear.entrySet())
		{
			int yearout = yearEntry.getKey(); 
			HashMap<String, Long> modelofsd =yearEntry.getValue();
			HashMap<String, Integer> modelofcount =yearcount.get(yearout);
			HashMap<String, Integer> modelofmean =meanyear.get(yearout);
			for(Map.Entry<String, Long> modelEntry : modelofsd.entrySet())
			{
				String modelout = modelEntry.getKey();
				long sdinter = modelEntry.getValue();
				int countout = modelofcount.get(modelout);
				int meanout = modelofmean.get(modelout);
				int sdout =  0;
				if(countout==1)
					sdout=(int)(Math.sqrt(sdinter/(countout))); //unbaised
				else
					sdout=(int)(Math.sqrt(sdinter/(countout-1))); //unbaised
				
				bo.println(manufacturer.getName().replace(".csv", "")+","+yearout+","+modelout+","+countout+","+sdout+","+meanout);
				System.out.println(manufacturer.getName().replace(".csv", "")+","+yearout+","+modelout+","+countout+","+sdout+","+meanout);
				
			}
		}
		
		meanmodel.clear();
		meanyear.clear();
		model.clear();
		year.clear();
		modelcount.clear();
		yearcount.clear();
		
	}

	}

}
