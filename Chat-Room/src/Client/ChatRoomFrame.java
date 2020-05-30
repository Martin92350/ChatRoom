package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;//this is the socket package
/*dont under any circumstance remove this import XD*/
import java.net.UnknownHostException;
///////////////////////////////////////
//our scanner import
import java.util.Scanner;
///////////////////////////////////////

@SuppressWarnings("serial")
public class ChatRoomFrame extends JFrame{

	private JTextField message;
	private JTextField username;
	private JTextField channel;
	private JTextArea displayMessages;
	private JTextArea listUserNames;
	private Container container;
	MultiConnexion ClientThread;
	private JLabel labelChannel;
	private JLabel labelUsername;
	private final static String newline = "\n";

	public ChatRoomFrame(){
		super("ChatRoom");
		
		
		//ajout et allignement des éléments message/name/channel/displaymessages/listUsernames sur interface
		getContentPane().setLayout(new FlowLayout());
		
		//creation d'une zone de texte avec la taille
		displayMessages = new JTextArea(30, 30);
		//affichage déroulant
		JScrollPane scrollPane = new JScrollPane(displayMessages);
		//permet de ne pas écrire dans la zone de texte 
		displayMessages.setEditable(false);
		
		//ajout de la barre déroulante sur l'interface graphique
		getContentPane().add(scrollPane);
		
		listUserNames = new JTextArea(30, 10);
		JScrollPane scrollPane3 = new JScrollPane(listUserNames); 
		listUserNames.setEditable(false);
		
		getContentPane().add(scrollPane3);
		
		message = new JTextField(20);
		message.setEditable(false);
		getContentPane().add(message);

        username = new JTextField(20);
        username.setEditable(true);
		getContentPane().add(username);

		//affichage texte sur interface graphique 
		labelChannel=new JLabel("channel number");
		labelUsername=new JLabel("Name");
		
		//creation du container qui englobe tous les elements de l'interface 
		container = this.getContentPane(); 
		container.setLayout(new FlowLayout());
		container.add(labelUsername);
		container.add(username);
		
		channel = new JTextField(20);
		channel.setEditable(false);
			
		container = this.getContentPane(); 
		container.setLayout(new FlowLayout());
		container.add(labelChannel);
		container.add(channel);
		
		//creation handler
		thehandler handler = new thehandler();
		//faire interagir le clavier et la souris sur les élémentes ci-dessous
		message.addActionListener(handler);
		username.addActionListener(handler);
		channel.addActionListener(handler);
		
		//gestion d'exception
		//creation d'un serveur socket
		try {
			//construction et initailisation d'un socket serveur 
			Socket s = new Socket("localhost",3333);
			ClientThread = new MultiConnexion(s,this);
			ClientThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private class thehandler implements ActionListener{
		public void actionPerformed(ActionEvent event){

			String string = "";

			//on test où a eu lieu l'interaction 
			if(event.getSource()==message)
			{
				//recupère le message String rentré par utilisateur
				string=String.format("%s", event.getActionCommand());
				String text= message.getText();
				//envoie au serveur l'info
				ClientThread.ClientOutServerIn(text);
				//remet la zone de texte à zero 
				message.setText("");
			}
			else if(event.getSource()==username) {
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[0-9]*"))
				{
					JOptionPane.showMessageDialog(null,"formate not allowed");
					username.setText("");
				}
				else
				{
					ClientThread.setName(string);
					ClientThread.SetClient("channel0",string);
					JOptionPane.showMessageDialog(null, "name has been set: "+string);
					username.setText("");
					username.setEditable(false);
					message.setEditable(true);
					channel.setEditable(true);
					ClientThread.ClientOutServerIn("new user");
					labelUsername.setVisible(false);
				}
			}
			else if(event.getSource()==channel) {
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[a-z A-Z]"))
				{
					JOptionPane.showMessageDialog(null,"formate not allowed");
					channel.setText("");
				}
				else
				{
					ClientThread.clientData.SetChannel("channel"+string);
					JOptionPane.showMessageDialog(null, "Channel has been set: channel"+string);
					channel.setText("");
					ClientThread.ClientOutServerIn("change channel");
				}
			}
			//JOptionPane.showMessageDialog(null, string);
		}
	}
	
	
	public void setDisplay(String x)
	{
		displayMessages.append(x + newline); 
	}
	public void setUserInChannel(String x)
	{
		listUserNames.append(x + newline);
	}
	public void ClearDisplay()
	{
		listUserNames.setText("");
	}
	public void setDisplay1(String x)
	{
		displayMessages.append(x); 
	}
	
}
