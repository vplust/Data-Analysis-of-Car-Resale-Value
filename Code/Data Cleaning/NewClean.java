package major;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class NewClean {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		int countnew =0;
		int countnewclean=0;
		Set<String> scar = new HashSet<String>();
		BufferedReader line = new BufferedReader(new FileReader("C:\\Users\\Vikas\\Desktop\\car_list.csv"));
		String str = line.readLine();
		while((str = line.readLine())!=null)
		{
			//COMPANY NAME,CAR NAME,price
			String[] details = str.split(",");
			String carwalemodel =details[1].replace(details[0].trim(),"").trim();
			scar.add(carwalemodel.toLowerCase());
		}	
		
		//0MANUFACTURER,1MODEL,2YEAR,3FUELTYPE,4KMSDRIVEN,5PRICE
		File folder = new File("c:\\users\\vikas\\desktop\\new.csv");
		line = new BufferedReader(new FileReader(folder));
		 str = line.readLine();
		 PrintStream p = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\newclean.csv"));
		 PrintStream premoved = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\newcleanremoved.csv"));
		 p.println(str.trim());
		 premoved.println(str.trim());
			
		 while((str = line.readLine())!=null)
		{	++countnew;
			String[] details = str.split(",");
			if(scar.contains(details[1].toLowerCase()))
			{
				++countnewclean;
				p.println(str.trim());
			}
			else
				premoved.println(str.trim());
			
		}
		System.out.println(countnew+" "+countnewclean);
	}

}
