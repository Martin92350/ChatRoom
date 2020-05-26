package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServerConnexion extends Thread {
	
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	Server server;
	boolean quite=false;
	
	public MultiServerConnexion(Socket OurSocket,Server OurServer)
	{
		//recupere methode de classe mere Thread
		super("MultiServerConnection");
		this.socket=OurSocket;
		this.server=OurServer;
	}
	
	 
	public void ServerOutClientIn(String OutText)
	{
		try {
			long ThreadID=this.getId();
			//envoie info du serveur au client
			out.writeUTF(OutText);
			//pour forcer l'ecriture
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void ServerOutAllClientIn(String OutText)
	{
		for(int i=0;i<server.threadList.size();i++)
		{
			MultiServerConnexion Connection=server.threadList.get(i);
			Connection.ServerOutClientIn(OutText);
		}
	}
	
	public void run()
	{
		try {
			in=new DataInputStream(socket.getInputStream());
			out=new DataOutputStream(socket.getOutputStream());
			
			while(!quite)
			{
				while(in.available()==0)
				{
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					
						e.printStackTrace();
					}
				}
				String ComingText=in.readUTF();
				ServerOutAllClientIn(ComingText);
			}
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}