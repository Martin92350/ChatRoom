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
	
	public String nomDeFichier ;
	
	public Sauvegarde () {
		
		nomDeFichier = "sauvegarde-channel0.txt"; 
	}
	
	public void setnomDeFichier(String results) {
		
		nomDeFichier = "sauvegarde-"+results+".txt";
		
	}
	
	public String getnomDeFichier() {
		
		return nomDeFichier;
	}
	
	public void setInFile(String results) {
		
		System.out.println("save "+results);
		
		//ecris dans fichier txt tout les  messages qui appraissent sur interface
		try  {
			BufferedWriter out = new BufferedWriter(new FileWriter(getnomDeFichier(), true));
			out.write(results+"\n");
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setNameInFile(String results) {
		
		System.out.println("save "+results);
		
		//ecris dans fichier txt tout les  messages qui appraissent sur interface
		try  {
			BufferedWriter out = new BufferedWriter(new FileWriter("saveUser.txt", true));
			out.write(results+"\n");
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getInFile(String nomDuFichier) {
		
		InputStream is;
		try {
			is = new FileInputStream(nomDuFichier);
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
	
	public String getNameInFile() {
		
		InputStream is;
		try {
			is = new FileInputStream("saveUser.txt");
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