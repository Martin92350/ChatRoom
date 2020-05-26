package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ServerSocket serversocket;
	boolean quite=false;
	ArrayList<MultiServerConnexion> threadList=new ArrayList<MultiServerConnexion>();
	
	public static void main(String[] args) {
		new Server();

	}
	public Server() {
		try {
			
			//creation d'un seruveur sur le port 3333
			serversocket=new ServerSocket(3333);
			while(!quite)
			{
				//accepte la connexion dans le domaine de connexion
				Socket s=serversocket.accept();
				MultiServerConnexion OurConnection = new MultiServerConnexion(s,this);
				//démarre le processus
				OurConnection.start();
				//ajout d'une connexion dans le domaine de connexion
				threadList.add(OurConnection);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//make sure its bloody same with client it took my 15 min to realize that XD
	}
}