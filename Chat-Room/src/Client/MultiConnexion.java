package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiConnexion extends Thread {
	
	Socket socket;
	//lecture de type primitif
	DataInputStream in;
	//ecriture de type primitif
	DataOutputStream out;
	boolean quite=false;
	public ClientData clientData;
	public ChatRoomFrame GUI;
	
	public MultiConnexion(Socket OurMultiSocket, ChatRoomFrame gui)
	{
		socket=OurMultiSocket;
		//creation d'un utilisateur
		clientData=new ClientData();
		GUI=gui;
	}
	public void ClientOutServerIn(String Text)
	{
		//envoie au serveur la saisie du clavier par l'utilisateur 
		try {
			if(Text.equals("change channel"))
			{
				System.out.println("sending changing channel: "+Text+"\n");
				out.writeUTF(Text);
				out.flush();
			}
			else if(Text.equals("new user"))
			{
				System.out.println("sending new user: "+ Text+"\n");
				out.writeUTF(Text+":"+clientData.GetName()+"="+clientData.GetChannel());
				out.flush();
			}
			else
			{
				out.writeUTF(clientData.GetChannel()+"="+this.getName()+": "+Text);
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void SetClient(String channel,String Name)
	{
		clientData.SetName(Name);
		clientData.SetChannel(channel);
	}
	public void run()
	{
		try {
			//instance permettant d'avoir un flux d'entrée et de sortie (échange)
			in=new DataInputStream(socket.getInputStream());
			out=new DataOutputStream(socket.getOutputStream());
			while(!quite)
			{
				try {
					while(in.available()==0)
					{
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//if there is something just show it on console
					//and then go back and do the same
					String reply=in.readUTF();
					String Chan=ExtractChannel(reply);
					String name=ExtractName(reply);
					/*if (reply.equals("change channel"))
					{
						System.out.print("changing channel in body: "+reply+"\n");
						//GUI.ClearDisplay();
						setChangedChannel();
					}*/
					if(name.equals("new user"))
					{
						System.out.print("new user in body: "+reply+"\n");
						//GUI.ClearDisplay();
						setChannel(reply);
					}
					else
					{
						PrintReply(Chan,reply);
					}
					//System.out.println(reply);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						in.close();
						out.close();
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException x) {
				// TODO Auto-generated catch block
				x.printStackTrace();
			}
		}
	}
	public void CloseClient()
	{
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String ExtractName(String x)
	{
		String[]Y=x.split(":");
		return Y[0];
	}
	public String ExtractChannel(String X)
	{
		String[]Y=X.split("=");
		return Y[0];
	}
	public void PrintReply(String Chan,String Rep)
	{
		if(clientData.GetChannel().equals(Chan))
		{
			String []Y=Rep.split("=");
			GUI.setDisplay(Y[1]);
			//System.out.println(Y[1]+"\n \n \n \n");
		}
		
	}
	public void setChannel(String x)
	{
		String []Y=x.split(":");
		String []Z=Y[1].split("=");
		System.out.print("setting "+Z[0]+" channel to "+Z[1]+"\n");
		GUI.setUserInChannel(Z[0]);
	}
	public void setChangedChannel()
	{
		GUI.setUserInChannel(clientData.GetName()+": "+clientData.GetChannel());
	}
	class ClientData
	{
		public String ClientName;
		public String channel;
		
		public void SetChannel(String Chan)
		{
			channel=Chan;
		}
		public void SetName(String name)
		{
			ClientName=name;
		}
		public String GetChannel()
		{
			return channel;
		}
		public String GetName()
		{
			return ClientName;
		}
	}
	
}