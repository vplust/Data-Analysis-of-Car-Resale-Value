package major;
import java.io.*;
import java.util.*;
public class CountModels {
	public static void main(String[] args) throws Exception 
	{
		PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\"+"OutlierComparison.csv"));
		int exception =0;
		int outindex=0;
		int totalSeparate=0; 
		int totalout1=0;
		BufferedReader line=null;
		String str =null;
		String details[] =null;
		HashMap<String, Integer> modelcount = null;
		HashMap<Integer, HashMap<String,Integer>> yearcount =  new HashMap<Integer, HashMap<String,Integer>>();
		int count =0;
		HashMap<String, Integer> outmodelcount = null;
		HashMap<Integer, HashMap<String,Integer>> outyearcount =  new HashMap<Integer, HashMap<String,Integer>>();
		int outcount =0;
		File folder = new File("c:\\users\\vikas\\desktop\\separate");
		File listOFFiles[] = folder.listFiles();
		File outfolder = new File("c:\\users\\vikas\\desktop\\out1"); //read from
		File outListOFFiles[] = outfolder.listFiles();
		for(File manufacturer : listOFFiles)
		{
			
			//seprate
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				++totalSeparate;
				//model, year , kms price
				details = str.split(",");
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

				//out
				line = new BufferedReader(new FileReader(outListOFFiles[outindex]));
				str = line.readLine();
				while((str = line.readLine())!=null)
				{
					++totalout1;
					//Manufacturer,1Model,Price,3Year,KMS
					details = str.split(",");
					if(!outyearcount.containsKey(Integer.parseInt(details[3].trim())))
					{
						outmodelcount = new HashMap<String, Integer>();
						outcount=1;
						outmodelcount.put(details[1].trim(),outcount);
						outyearcount.put(Integer.parseInt(details[3].trim()), outmodelcount);

					}
					else
					{
						outmodelcount =outyearcount.get(Integer.parseInt(details[3].trim()));
						if(outmodelcount.containsKey(details[1].trim()))
						{
							outcount =outmodelcount.get(details[1].trim());
							outcount = outcount+1;
							outmodelcount.put(details[1],outcount);
							outyearcount.put(Integer.parseInt(details[3]), outmodelcount);
						}
						else
						{
							outcount = 1;
							outmodelcount.put(details[1].trim(),outcount);
							outyearcount.put(Integer.parseInt(details[3]), outmodelcount);
						}
					}

				}
				++outindex;
				
				bo.println(manufacturer.getName().replace(".csv", ""));
				System.out.println(manufacturer.getName().replace(".csv", ""));
				bo.println("Model,"+"Year,"+"Seprate,"+"Out");
				System.out.println("Model "+" Year "+" Seprate "+" Out");
				for(Map.Entry<Integer, HashMap<String, Integer>> yearEntry : yearcount.entrySet())
				{
					int yearout = yearEntry.getKey(); 
					HashMap<String, Integer> modelseprate =yearEntry.getValue();
					HashMap<String, Integer> modelout =outyearcount.get(yearout);
					for(Map.Entry<String, Integer> modelEntry : modelseprate.entrySet())
					{
						try //single car outliers removed from separate cause nullpointer exception
						{
							String modeltypeseprate = modelEntry.getKey();
							int countseprate = modelEntry.getValue();
							int countout = modelout.get(modeltypeseprate);
							bo.println(modeltypeseprate+","+yearout+","+countseprate+","+countout);
							System.out.println(modeltypeseprate+" "+yearout+" "+countseprate+"  "+countout);
						}
						catch(Exception e)
						{++exception;
						
							continue;
							
						}
					}
				}
				System.out.println("******");
				modelcount.clear();
				yearcount.clear();
				outmodelcount.clear();
				outyearcount.clear();

			}
		System.out.println("Exceptions="+exception);
		System.out.println("Total Separate="+totalSeparate);
		System.out.println("Total Out1 ="+totalout1);
		

			
		}
		
	}

