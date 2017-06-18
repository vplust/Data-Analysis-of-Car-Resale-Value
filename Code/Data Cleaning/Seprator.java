package major;
import java.io.*;
import java.util.*;
public class Seprator {

	public static void main(String... args) throws Exception
	{
		String location ="c:\\users\\vikas\\desktop\\separate\\";
		File f = new File("C:\\Users\\Vikas\\Desktop\\newclean.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		ArrayList<String> manufacturer = new ArrayList<String>();
		HashSet<String> hs = new HashSet<String>();
		
		String onecar = br.readLine();
		while((onecar=br.readLine())!=null)
		{
			String detail[]=onecar.split(",");
			hs.add(detail[0]);
		}
		for(String s : hs)
		{	 manufacturer.add(s);
		}
		
		int index=0;
		br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		onecar = br.readLine();
		while((onecar=br.readLine())!=null)
		{//MANUFACTURER,MODEL,YEAR,FUELTYPE,KMSDRIVEN,PRICE
			String detail[]=onecar.split(",");
			File firstrun = new File(location+detail[0]+".csv");
			FileOutputStream carfile = new FileOutputStream(firstrun,true);
			PrintStream output = new PrintStream(carfile);
			if(firstrun.length()==0)
			{
				String line = "Model,"+"Year,"+"KMS,"+"Price";
				output.println(line);
				output.flush();
			}
			else
			{

				String line = detail[1]+","+detail[2]+","+detail[4]+","+detail[5];
				
				output.println(line);
				output.flush();
			}
		}
	}
}
