package major;
import java.io.*;
import java.util.*;
public class TestCorrelation {
	
	static void correlation(ArrayList<Integer> year ,ArrayList<Integer> price,String modelname) throws Exception
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
		System.out.println(numerator/denominator+"\n");
		PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\correlation2.csv",true));
		//bo.print(modelname+",");
		//bo.println(numerator/denominator);
	}

	static float mean(ArrayList<Integer> al)
	{
		float meanval=0;
		for(int i: al)
		{
			meanval+=i;
		}
		return meanval/al.size();
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader line=null;
		String str=null;
		double numerator=0;
		double denominator =0;
		double sdx=0;
		double sdy=0;
		ArrayList<Integer> year = new ArrayList<Integer>();
		ArrayList<Integer> price = new ArrayList<Integer>();
		
		line = new BufferedReader(new FileReader("c:\\users\\vikas\\desktop\\test.csv"));
		
		while((str = line.readLine())!=null)
		{
			String [] details = str.split(",");
		//	int strlen= details[1].length();
		//	int normprice= Integer.parseInt(details[1].trim())/(10*(strlen-3));
		//	System.out.println(normprice);
			//price.add(normprice);	//-0.9587054956889467
			
			year.add(Integer.parseInt(details[0].trim()));
			price.add(Integer.parseInt(details[1].trim())); // 0.9587038810078334
		}
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
		System.out.println(numerator/denominator);
		double r =numerator/denominator;
		for(int i=0; i<pricecal.length;++i)
		{
			sdy+=(pricecal[i]*pricecal[i]);
		}
		for(int i=0; i<yearcal.length;++i)
		{
			sdx+=(yearcal[i]*yearcal[i]);
		}
		
		double slope = r*sdy/sdx;
		System.out.println(slope);
		slope=numerator/squarepricecal;
		System.out.println(slope);
		
	}

}
