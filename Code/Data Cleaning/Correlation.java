package major;
import java.io.*;
import java.util.*;
public class Correlation {
	
	static String modelFileName=null;
	//mean function
	static float mean(ArrayList<Integer> al)
	{
		float meanval=0;
		for(int i: al)
		{
			meanval+=i;
		}
		return meanval/al.size();
	}
	//correlation function
	static void correlation(ArrayList<Integer> year ,ArrayList<Integer> price,String modelname, PrintStream corout) throws Exception
	{
		double numerator=0;
		double denominator =0;
		double meanyear= mean(year);
		double meanprice= mean(price);
		
		
		double[] yearcal = new double[year.size()];
		for(int i=0; i<year.size();++i)
		{
			yearcal[i]=year.get(i)-meanyear;	
		}
		
		double squareyearcal = 0;
		for(int i=0; i<year.size();++i)
		{
			squareyearcal+=(yearcal[i]*yearcal[i]);	
		}
		
		double[] pricecal = new double[price.size()];
		for(int i=0; i<price.size();++i)
		{
			pricecal[i]=price.get(i)-meanprice;
			
		}
		
		double squarepricecal = 0;
		for(int i=0; i<price.size();++i)
		{
			squarepricecal+=(pricecal[i]*pricecal[i]);	
		}
		
		
		for(int i=0; i<pricecal.length;++i)
		{
			numerator+=(yearcal[i]*pricecal[i]);
		}

		denominator =Math.sqrt(squarepricecal*squareyearcal);
	//	System.out.println(numerator/denominator+"\n");
		corout.print(modelname+",");
		corout.println(numerator/denominator);
	}

	//main function
	public static void main(String... args) throws Exception
	{
		PrintStream corout = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\correlationcoefficient.csv",true));
		corout.println("Manufacturer,Model,r-value");
		
		
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
			PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\clustermean\\"+manufacturer.getName(),true));
			bo.println("Manufacturer,Year,Model,Price");
			
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
					//int length =yearentry.getValue().toString().length();
					int theyear = (2017-yearentry.getKey()+2017);
					//int price = (int) (yearentry.getValue()/Math.pow(10, length-4));
					yearal.add(theyear);
					priceal.add(yearentry.getValue());		
				}
				
				line = new BufferedReader(new FileReader("C:\\Users\\Vikas\\Desktop\\car_list.csv"));
				str = line.readLine();
				while((str = line.readLine())!=null)
				{
					//COMPANY NAME,CAR NAME,price
					String[] details = str.split(",");
					String carwalemodel =details[1].replace(details[0].trim(),"").trim();
					
					if(carwalemodel.equals(modelout))
					{
						System.out.println(carwalemodel);
						yearal.add(2017);
						double d = Double.parseDouble(details[2].trim());
						priceal.add((int) d);	
					}
				
				
				}
				
				if(yearal.contains(2018)&&yearal.contains(2017))
				{
					double k=0;
					while((k=Math.random())<0.6);
					int indexyearal = yearal.indexOf(2017);
					int pric=priceal.get(indexyearal);
					pric=pric-(int)(pric*k/100);
					indexyearal = yearal.indexOf(2018);
					priceal.set(indexyearal,pric);
						
				}
				
				//printing clustermean
				for(int i =0; i<yearal.size();++i)
				{
				//	System.out.println(modelout+" "+yearal.get(i)+" "+priceal.get(i));
					
					//removing mean of 2017
					if(yearal.get(i)!=2017)
					{bo.println(manufacturer.getName().replace(".csv", "")+","+yearal.get(i)+","+modelout+","+priceal.get(i));
					}
						}
				
				corout.print(manufacturer.getName().replace(".csv", "")+",");
				Correlation.correlation(yearal, priceal,modelout,corout);
				yearal.clear();
				priceal.clear();
								
							
			}
			
			
			year.clear();
			model.clear();
			
		}
		
	}
}
