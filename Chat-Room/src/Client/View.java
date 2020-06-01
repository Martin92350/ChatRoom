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
public class View extends JFrame{

	private JTextField message;
	private JTextField username;
	private JTextField channel;
	private JTextArea displayMessages;
	private JTextArea listUserNames;
	private Container container;
	Controller controller;
	private JLabel labelChannel;
	private JLabel labelUsername;
	private final static String newline = "\n";
	private JPanel panel;
	private JButton buttonGeneric;
	private JButton buttonGroup;

	public View(){
		super("ChatRoom");
		
		
		//ajout et allignement des éléments message/name/channel/displaymessages/listUsernames sur interface
		getContentPane().setLayout(new FlowLayout());
		
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
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
			controller = new Controller(s,this);
			controller.start();
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
			thehandler handlerButton = new thehandler();
			//on test où a eu lieu l'interaction 
			if(event.getSource()==message)
			{
				//recupère le message String rentré par utilisateur
				string=String.format("%s", event.getActionCommand());
				String text= message.getText();
				//envoie au serveur l'info
				controller.ClientOutServerIn(text);
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
					controller.setName(string);
					controller.SetClient("channel0",string);
					controller.callSetChannelSelected("channel0");
					JOptionPane.showMessageDialog(null, "name has been set: "+string);
					buttonGeneric = new JButton("channel0");
					buttonGeneric.addActionListener(handlerButton);
					panel.add(buttonGeneric);
					username.setText("");
					username.setEditable(false);
					message.setEditable(true);
					channel.setEditable(true);
					controller.ClientOutServerIn("new user");
					labelUsername.setVisible(false);
				}
			}
			else if(event.getSource()==channel) {
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[0-9]*"))
				{
					JOptionPane.showMessageDialog(null,"formate not allowed");
					channel.setText("");
				}
				else
				{
					controller.callSetChannel(string);
					controller.callSetChannelSelected(string);
					controller.callAddChannels(string);
					
					JOptionPane.showMessageDialog(null, "Channel has been set: channel"+string);
					//conversations.append("channel"+string + newline);
							
					buttonGroup = new JButton(string);
					buttonGroup.addActionListener(handlerButton);
					panel.add(buttonGroup);
									
					panel.revalidate();
					channel.setText("");
										
					controller.ClientOutServerIn("change channel");
				}
			}
			
			else if((event.getSource() == buttonGroup) || (event.getSource() == buttonGeneric)) {
			    Object source = event.getSource();
		        JButton btn = (JButton)source;
				string = btn.getText();

				System.out.println("ca marche : "+ string);
				controller.callSetChannelSelected(string);
				System.out.println("Nouvelle channel : " + controller.donnéesUtilisateur.getChannelSelected());

				controller.ClientOutServerIn("button selected : "+string);
			}
			
		}
	}
	
	
	public void setDisplay(String x) {
		displayMessages.append(x + newline); 
	}
	
	public void displaySavedMessaged(String recordedMessages) {
		displayMessages.append(recordedMessages);
		panel.revalidate();
	}
	
	public void displaySavedUsers(String oldUsers) {
		listUserNames.append(oldUsers);
	}
	
	public void setUserInChannel(String x) {
		listUserNames.append(x + newline);
	}
	
	public void clearChat() {
		displayMessages.setText("");
	}
	
	public void ClearDisplay() {
		listUserNames.setText("");
	}

	
}
