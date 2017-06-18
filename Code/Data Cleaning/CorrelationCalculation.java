package major;
import java.io.*;
import java.util.*;
public class CorrelationCalculation {
	static float mean(ArrayList<Integer> al)
	{
		float meanval=0;
		for(int i: al)
		{
			meanval+=i;
		}
		return meanval/al.size();
	}
	
	public static void main(String... args) throws Exception
	{
		ArrayList<Integer> yearal = new ArrayList<Integer>();
		ArrayList<Integer> priceal = new ArrayList<Integer>();
		double numerator=0;
		double denominator =0;

		BufferedReader line=null;
		String str =null;

		HashMap<Integer, Integer> year = null;
		HashMap<String, HashMap<Integer,Integer>> model =  new HashMap<String, HashMap<Integer,Integer>>();

		File folder = new File("c:\\users\\vikas\\desktop\\meanout1");
		File listOFFiles[] = folder.listFiles();

		for(File manufacturer : listOFFiles)
		{
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				String[] details = str.split(",");
				//0Manufacturer,1Year,2Model,3count,4Sum,5Mean
				if(!model.containsKey(details[2].trim()))
				{
					year = new HashMap<Integer, Integer>();
					year.put(Integer.parseInt(details[1].trim()), Integer.parseInt(details[5].trim()));
					model.put(details[2].trim(),year);
				}
				else
				{
					year =model.get(details[2].trim());
					if(year.containsKey(Integer.parseInt(details[1].trim())))
					{

					}
					else
					{

						year.put(Integer.parseInt(details[1].trim()), Integer.parseInt(details[5].trim()));
					}
				}
			}

			for(Map.Entry<String, HashMap<Integer,Integer>> entry: model.entrySet())
			{
				String modelout= entry.getKey();
				HashMap<Integer, Integer> yearmap =entry.getValue();
				for(Map.Entry<Integer, Integer> yearentry: yearmap.entrySet())
				{
					
					int theyear = (2017-yearentry.getKey()+2017);
					yearal.add(theyear);
					priceal.add(yearentry.getValue());		
				}

				line = new BufferedReader(new FileReader(manufacturer));
				str = line.readLine();
				while((str = line.readLine())!=null)
				{
					//COMPANY NAME,CAR NAME,price
					String[] details = str.split(",");
					String carwalemodel =details[1].replace(details[0].trim(),"").trim();
					if(carwalemodel==modelout)
					{
						yearal.add(2017);
						priceal.add(Integer.parseInt(details[2].trim()));	
					}


				}
				System.out.print(modelout+" ");
				double meanyear= mean(yearal);
				double meanprice= mean(priceal);
				
				
				double[] yearcal = new double[yearal.size()];
				for(int i=0; i<yearal.size();++i)
				{
					yearcal[i]=yearal.get(i)-meanyear;	
				}
				
				double squareyearcal = 0;
				for(int i=0; i<yearal.size();++i)
				{
					squareyearcal+=(yearcal[i]*yearcal[i]);	
				}
				
				double[] pricecal = new double[priceal.size()];
				for(int i=0; i<priceal.size();++i)
				{
					pricecal[i]=priceal.get(i)-meanprice;
					
				}
				
				double squarepricecal = 0;
				for(int i=0; i<priceal.size();++i)
				{
					squarepricecal+=(pricecal[i]*pricecal[i]);	
				}
				
				
				for(int i=0; i<pricecal.length;++i)
				{
					numerator+=(yearcal[i]*pricecal[i]);
				}

				denominator =Math.sqrt(squarepricecal*squareyearcal);
				System.out.println(numerator/denominator);
				

			}

		}
	}
}
