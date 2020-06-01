package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Controller extends Thread {
	
	Socket socket;
	//lecture de type primitif
	DataInputStream in;
	//ecriture de type primitif
	DataOutputStream out;
	boolean quite=false;
	public Model donnéesUtilisateur;
	public View GUI;
	public Sauvegarde sauvegarde ;
	
	public Controller(Socket OurMultiSocket, View gui)
	{
		socket=OurMultiSocket;
		//creation d'un utilisateur
		donnéesUtilisateur=new Model();
		GUI=gui;
		sauvegarde = new Sauvegarde();
		
		try {
			this.out=new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.in=new DataInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//permet utilisateur d'envoyer tout type d'info au serveur
	public void ClientOutServerIn(String Text)
	{
		//envoie au serveur la saisie du clavier par l'utilisateur 
		try {
			if(Text.equals("change channel"))
			{
				System.out.println("sending changing channel: "+Text+"\n");
				out.writeUTF(Text);
				out.flush();
				sauvegarde.setUsersChannelInFile(donnéesUtilisateur.GetName(), donnéesUtilisateur.getChannelSelected());
			}
			else if(Text.equals("new user"))
			{
				System.out.println("sending new user: "+ Text+"\n");
				//"new user : le nom saisie par user = sur quel channel il se trouve"
				out.writeUTF(Text+":"+donnéesUtilisateur.GetName()+"="+donnéesUtilisateur.GetChannel());
				out.flush();
				sauvegarde.setUsersChannelInFile(donnéesUtilisateur.GetName(), donnéesUtilisateur.getChannelSelected());
			}
			else if(Text.matches("button selected : (.*)"))
			{
				System.out.println("text : " + Text);
				out.writeUTF(Text);
				out.flush();
				
				changeFileName(Text);
				displayMessagesPerChannel(Text);
				
				changeUsersChannel(Text);
				displayUsernamePerChannel(donnéesUtilisateur.getChannelSelected());
			}
			else
			{
				//message saisie par utilisateur sur serveur
				String message = donnéesUtilisateur.GetChannel()+"="+this.getName()+": "+Text;
				out.writeUTF(message);
				out.flush();
				sauvegardeFichier(message);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
	}
	//modifie channel ou le nom sur serveur 
	public void SetClient(String channel,String Name)
	{
		donnéesUtilisateur.SetName(Name);
		donnéesUtilisateur.SetChannel(channel);
	}
	//reception d'info du client
	public void run()
	{
		try {
			affichageConversationPrincipale();
			displayUsersChannel();
			sauvegardeUtilisateurs();
			System.out.println("run");
			//instance permettant d'avoir un flux d'entrée et de sortie (échange)
			in=new DataInputStream(socket.getInputStream());
			out=new DataOutputStream(socket.getOutputStream());
			while(!quite)
			{
				try {
					//tant qu'il y a plus iren à lire 
					while(in.available()==0)
					{
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
					}
					//recupere la saisie
					String reply=in.readUTF();
					// recupere le canal
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
						//afficher dans la convsole  si dans meme canal
						PrintReply(Chan,reply);
						//sauvegarde du contenu 
					
					}
					//System.out.println(reply);
				} catch (IOException e) {
					e.printStackTrace();
					try {
						in.close();
						out.close();
						socket.close();
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}	
			}
		} catch (IOException e) {
			
			e.printStackTrace();
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException x) {
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
			
			e.printStackTrace();
		}
	}
	
	public void callSetChannelSelected(String newGroupSelected) {
		donnéesUtilisateur.setChannelSelected(newGroupSelected);
	}
	
	public void callSetChannel(String newGroup) {
		donnéesUtilisateur.SetChannel(newGroup);
	}
	
	public void callAddChannels(String newGroup) {
		donnéesUtilisateur.addInListGroup(newGroup);
	}
	
	public Boolean callCheckExistingGroup(String group) {
		return donnéesUtilisateur.checkExistingGroup(group);
	}
	
	public String callGetPseudo() {
		return donnéesUtilisateur.GetName();
	}
	
	public String callGetGroup() {
		return donnéesUtilisateur.GetChannel();
	}
	
	public String callGetGroupSelected() {
		return donnéesUtilisateur.getChannelSelected();
	}
	
	public ArrayList<String> callGetListGroup() {
		return donnéesUtilisateur.getListGroup();
	}
	
	public void displayMessagesPerChannel(String text){
		
		GUI.clearChat();
		String[]Y=text.split(": ");
		String X = sauvegarde.getInFile("sauvegarde-"+Y[1]+".txt");
		GUI.setDisplay(X);
	}
	
	public void displayUsernamePerChannel(String text){
	
		GUI.ClearDisplay();
		String X = sauvegarde.getUsersChannelInFile("users-"+text+".txt");
		GUI.displaySavedUsers(X);
	}
	
	public void affichageConversationPrincipale() {
		
		String oldMessages = sauvegarde.getInFile("sauvegarde-channel0.txt");
		GUI.displaySavedMessaged(oldMessages);
	}
	
public void displayUsersChannel() {
		
		String users = sauvegarde.getUsersChannelInFile("users-channel0.txt");
		GUI.displaySavedUsers(users);
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
	
	public void changeFileName(String text) {
		String[]Y=text.split(": ");
		sauvegarde.setnomDeFichier(Y[1]);
	}
	
	public void changeUsersChannel(String text) {
		String[]Y=text.split(": ");
		sauvegarde.setSaveUsers(Y[1]);
	}
	
	public void PrintReply(String Chan,String Rep)
	{
		if(Chan.contentEquals(donnéesUtilisateur.getChannelSelected())) {
			System.out.println("Rep => "+ Rep);
			String []Y=Rep.split("=");
			System.out.println("Y[1] => "+ Y[1]);
			
			GUI.setDisplay(Y[1]);
		}
		
	}
	
	public void sauvegardeFichier(String Rep) {
		
		if(!Rep.contentEquals("change channel")) {
			String []Y=Rep.split("=");
			sauvegarde.setInFile(Y[1]);
		}
	}
	

	
//	public void affichageNomUtilisateurDeChannel() {
//		
//		String UsersChannel = sauvegarde.getUsersChannelInFile("sauvegarde-usersnames.txt");
//		GUI.displaySavedMessaged(UsersChannel);
//	}
//	
	private void sauvegardeUtilisateurs() {
		String oldUsers = sauvegarde.getNameInFile();
		GUI.displaySavedUsers(oldUsers);
	}
	
	/*private void saveUsersChannel() {
		String UsersChannel = sauvegarde.getUsersChannelInFile();
		GUI.displaySavedUsers(UsersChannel);
	}*/
	
	public void setChannel(String x)
	{
		String []Y=x.split(":");
		String []Z=Y[1].split("=");
		System.out.print("setting "+Z[0]+" channel to "+Z[1]+"\n");
		//rajoute un nom d'utilisateur dans la liste 
		GUI.setUserInChannel(Z[0]);
	}
	public void setChangedChannel()
	{
		GUI.setUserInChannel(donnéesUtilisateur.GetName()+": "+donnéesUtilisateur.GetChannel());
	}
	
	
	
}