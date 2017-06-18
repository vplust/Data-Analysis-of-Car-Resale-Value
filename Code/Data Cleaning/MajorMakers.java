package major;
import java.io.*;
import java.util.*;
public class MajorMakers {

	public static void main(String[] args) throws Exception {
		String location ="c:\\users\\vikas\\desktop\\";
		File f = new File("C:\\Users\\Vikas\\Desktop\\new.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		ArrayList<String> manufacturer = new ArrayList<String>();
		HashSet<String> hs = new HashSet<String>();
		HashMap model[] = new HashMap[19];
		for(int i=0;i<19;++i)
			model[i]= new HashMap<String,Integer>();		
		
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
		{
			String detail[]=onecar.split(",");
			index =manufacturer.indexOf(detail[0]);
			if(!model[index].containsKey(detail[1]))
			{	
				model[index].put(detail[1], 1);
			}
			
			else
			{
				int value = (int)model[index].get(detail[1]);
				model[index].put(detail[1], value+1);
				
			}
		
		}
		index=0;
		for(HashMap<String,Integer> allcars : model)
		{	System.out.println(manufacturer.get(index));
			for(Map.Entry<String,Integer> ent : allcars.entrySet())
			{
				System.out.println(ent.getKey() +" "+ ent.getValue());
			}
			++index;System.out.print("**************\n ");
		}
		
	}

}
