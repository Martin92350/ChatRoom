package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Sauvegarde {
	
	
	
	public void setInFile(String results) {
		
		System.out.println("wsh "+results);
		
		//ecris dans fichier txt tout les  messages qui appraissent sur interface
		try  {
			BufferedWriter out = new BufferedWriter(new FileWriter("BackUpChat.txt", true));
			out.write(results+"\n");
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getInFile() {
		
		InputStream is;
		try {
			is = new FileInputStream("BackUpChat.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
			String line;
			try {
				line = buf.readLine();
				StringBuilder sb = new StringBuilder(); 
				while(line != null){ 
					sb.append(line).append("\n"); 
					line = buf.readLine(); 
				} 
				String fileAsString = sb.toString(); 
				System.out.println("Contents (before Java 7) : " + fileAsString);
				//GUI.setDisplay("bonjour");
				return fileAsString ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	return "echec" ;
	}
	
}