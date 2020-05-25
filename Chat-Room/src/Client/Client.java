package Client;
import java.io.IOException;
import java.net.Socket;//this is the socket package
/*dont under any circumstance remove this import XD*/
import java.net.UnknownHostException;
///////////////////////////////////////
//our scanner import
import java.util.Scanner;

import javax.swing.JFrame;

///////////////////////////////////////
//our GUI libraries
////////////////////////////////////////
public class Client{
	/////////////////////////////////////
	//better than sending them as an arguments to each function
	MultiConnexion ClientThread;
	////////////////////////////////////
	public static void main(String[] args) {
		new Client();
	}
	public Client()
	{	
		//configuration du format d'affichage
		ChatRoomFrame crape = new ChatRoomFrame();
		crape.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crape.setSize(500,600);
		crape.setVisible(true);
		/*try {
			Socket s=new Socket("localhost",3333);
			ClientThread =new MultiClients(s);
			System.out.println("Enter Your Anonomus Name:");
			@SuppressWarnings("resource")
			Scanner UserThreadName=new Scanner(System.in);
			String UserName=UserThreadName.nextLine();
			ClientThread.setName(UserName);
			ClientThread.SetClient("channel0",UserName);
			ClientThread.start();
			for(int i=0;i<50;i++)
			{
				System.out.print("\n");
			}
			ListenForInput();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	//recoit entrée par utilisateurs et la reponse du serveur
	/*public void ListenForInput()
	{
		//pour convertire en java les entrées au clavier faites par l'utilisateur
		@SuppressWarnings("resource")
		Scanner console=new Scanner(System.in);
		while(true)
		{
			//En attentte d'une ligne 
			while(!console.hasNextLine())//only run upon pressing run
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String input=console.nextLine();//recupere ce que l'utilisateurs a rentrer dans console 
			
			if(input.toLowerCase().equals("quit"))
			{
				break;
			}
			if(input.toLowerCase().equals("change channel"))
			{
				input=console.nextLine();
				ClientThread.c.SetChannel(input);
			}
			else
			{
				ClientThread.ClientOutServerIn(input);
			}
		}
		ClientThread.CloseClient();
	}*/
}