package Client;
import java.io.IOException;
import java.net.Socket;//this is the socket package
/*dont under any circumstance remove this import XD*/
import java.net.UnknownHostException;
///////////////////////////////////////
//our scanner import
import java.util.Scanner;

import javax.swing.JFrame;

public class Client{
	
	Controller ClientThread;
	
	public static void main(String[] args) {
		new Client();
	}
	public Client()
	{	
		//configuration du format d'affichage
		View crape = new View();
		crape.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crape.setSize(600,650);
		crape.setVisible(true);
	
	}
	
}