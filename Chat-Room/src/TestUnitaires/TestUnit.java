package TestUnitaires;



import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

//import org.junit.Test;
import org.junit.jupiter.api.Test;

import Client.Controller;
import Client.Model;
import Client.View;

public class TestUnit{
	

	@Test
	void test_setClient() {
		View testView = new View();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller testController = new Controller(socket, testView);
		
		String pseudo = "user1";
		String groupe = "Général";
		testController.SetClient(groupe, pseudo);
		
		ArrayList<String> list = testController.callGetListGroup();
		
		assertEquals("user1", testController.callGetPseudo());
		assertEquals("Général", testController.callGetGroup());
		assertEquals("Général", list.get(list.size() - 1));
	}
	
	@Test
	void test_call_setChannelSelected() {
		View testView = new View();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller testController = new Controller(socket, testView);
		
		String newGroupSelected = "Général";
		testController.callSetChannelSelected(newGroupSelected);
				
		assertEquals("Général", testController.callGetGroupSelected());
	}
	
	@Test
	void test_call_setChannel() {
		View testView = new View();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller testController = new Controller(socket, testView);
		
		String groupe = "Other";
		testController.callSetChannel(groupe);
				
		assertEquals("Other", testController.callGetGroup());
	}
	
	@Test
	void test_call_addChannels() {
		View testView = new View();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller testController = new Controller(socket, testView);
		
		String groupe = "Other";
		testController.callAddChannels(groupe);
		
		ArrayList<String> list = testController.callGetListGroup();
				
		assertEquals("Other", list.get(list.size() - 1));
	}
	
	@Test
	void test_call_checkExistingGroup() {
		View testView = new View();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller testController = new Controller(socket, testView);
		
		String groupe1 = "Général";
		String groupe2 = "Other";
		
		testController.callAddChannels(groupe1);
		testController.callAddChannels(groupe2);
				
		String checkExistingGroup = "Other";
		String checkNonExistingGroup = "Nope";
		
		
		assertTrue(testController.callCheckExistingGroup(checkExistingGroup));
		assertFalse(testController.callCheckExistingGroup(checkNonExistingGroup));
		
	}
	
	@Test
	void test_ClientOutServerIn() {
		View testView = new View();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller testController = new Controller(socket, testView);
		
		String data1 = "change channel";
		testController.ClientOutServerIn(data1);
		
		String data2 = "new user";
		testController.ClientOutServerIn(data2);

		String data3 = "button selected : Général";
		testController.ClientOutServerIn(data3);
		
		String data4 = "message de user1";
		testController.ClientOutServerIn(data4);
		
	}
	
	@Test
	void test_run() {
		View testView = new View();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller testController = new Controller(socket, testView);
		
		String data1 = "switch group";
		testController.ClientOutServerIn(data1);
		
		String data2 = "new user";
		testController.ClientOutServerIn(data2);

		String data3 = "group selected : Général";
		testController.ClientOutServerIn(data3);
		
		String data4 = "message de user1";
		testController.ClientOutServerIn(data4);
		
	}

}