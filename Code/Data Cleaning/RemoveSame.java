package major;
import java.io.*;
import java.util.*;
public class RemoveSame {

	public static void main(String[] args) throws Exception {
		
		BufferedReader line=null;
		String str =null;
		
		TreeSet<String> model = new TreeSet<String>();
		
		File folder = new File("c:\\users\\vikas\\desktop\\separate");
		File listOFFiles[] = folder.listFiles();
		
		for(File manufacturer : listOFFiles)
		{
			line = new BufferedReader(new FileReader(manufacturer));
			str = line.readLine();
			while((str = line.readLine())!=null)
			{
				model.add(str);
			}
			
			PrintStream bo = new PrintStream(new FileOutputStream("c:\\users\\vikas\\desktop\\separatenew\\"+manufacturer.getName()));
			str = "Model,"+"Year,"+"KMS,"+"Price";
			bo.println(str);
			bo.flush();
			for(String m:model)
			{
				bo.println(m);
				bo.flush();
			}
			model.clear();
		}

	}

}
