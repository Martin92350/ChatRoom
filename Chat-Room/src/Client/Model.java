package Client;

import java.util.ArrayList;

public class Model {

	
			public String ClientName;
			public String channel;
			public ArrayList<String> channels = new ArrayList<String>();
			private String channelSelected = ""; 


			
			public void SetChannel(String Chan) {
				channel=Chan;
			}
			
			public void SetName(String name) {
				ClientName=name;
			}
			
			public String GetChannel() {
				return channel;
			}
			
			public String GetName() {
				return ClientName;
			}
			
			public void setChannelSelected(String channel) {
				channelSelected = channel;
			}
			
			public String getChannelSelected() {
				return channelSelected;
			}
			
			public void addChannels(String newChannel) {
				channels.add(newChannel);
				System.out.println("Channels : ");
				for(String chan : channels) {
					System.out.println(chan);
				}
			}
			
			public ArrayList<String> getListGroup() {
				return channels;
			}
			public void addInListGroup(String newGroup) {
				this.channels.add(newGroup);
			}
			
			public Boolean checkExistingGroup(String group) {
				for(String existingGroup : this.channels) {
					if(existingGroup.equals(group)) {
						return true;
					}
				}
				return false;
			}
			
			
}

