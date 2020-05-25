package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ServerSocket ss;
	boolean quite=false;
	ArrayList<MultiServerConnexion> OurDomainsConnections=new ArrayList<MultiServerConnexion>();
	
	public static void main(String[] args) {
		new Server();

	}
	public Server() {
		try {
			//TODO use method to take this as an input from user)
			ss=new ServerSocket(3333);//here we are using connection 3333 (change as you want
			while(!quite)
			{
				Socket s=ss.accept();//when a connection to the domain is found we accept it
				MultiServerConnexion OurConnection = new MultiServerConnexion(s,this);
				OurConnection.start();//Start Thread
				OurDomainsConnections.add(OurConnection);//add connection to our Domain Connection
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//make sure its bloody same with client it took my 15 min to realize that XD
	}
}