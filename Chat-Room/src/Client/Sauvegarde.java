package Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Sauvegarde {
	
	
	public FileOutputStream fos ;
	

	public Sauvegarde() {
		
		this.fos =null ;
		
	}
	
	public void setInFile(String results) {
		
		System.out.println("wsh "+results);
		
		try  {
			BufferedWriter out = new BufferedWriter(new FileWriter("BackUpChat.txt", true));
			out.write(results+"\n");
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}