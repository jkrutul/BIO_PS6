package pl.edu.pb.wi.bio.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserKeyboardScan {
	private String userName;
	private Map<Character,Long> keyboardScan;
	
	public UserKeyboardScan(String userName) {
		super();
		this.userName = userName;
		this.keyboardScan = new HashMap<Character,Long>();
	}
	
	public void addDataFromFile(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
	    try {
	        String line = br.readLine();
	        while (line != null) {
	        	String[] row = line.split("\\s");
	        	Character c = new Character(row[0].charAt(0));
	        	Long time = new Long(row[1]);
	        	
	        	Long mean = keyboardScan.get(c);
	        	if (mean != null) {
	        		keyboardScan.put(c, (mean+time)/2);
	        	} else {
	        		keyboardScan.put(c, time);
	        	}

	            line = br.readLine();
	        }

	    } finally {
	        br.close();
	    }
	}

	@Override
	public String toString() {
		return "UserKeyboardScan [userName=" + userName + ", keyboardScan="
				+ keyboardScan + ", size="+keyboardScan.size()+"]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Map<Character, Long> getKeyboardScan() {
		return keyboardScan;
	}

	public void setKeyboardScan(Map<Character, Long> keyboardScan) {
		this.keyboardScan = keyboardScan;
	}

}
