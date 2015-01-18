package pl.edu.pb.wi.bio;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pl.edu.pb.wi.bio.models.KeyTime;
import pl.edu.pb.wi.bio.models.UserKeyboardScan;

public class MyKeyboardScanner extends JFrame {
	private static Long pressedTime = null;
	private static Character pressedKey;
	private static List<KeyTime> rawData = new LinkedList<>();
	private JTextField userNameFld = new JTextField(5);
	private JTextField textFld = new JTextField(25);
	
	private Weka weka;

	public MyKeyboardScanner(Map<String,UserKeyboardScan> data ){
		super();
		this.weka = new Weka(data);
		rawData = new LinkedList<>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		
		textFld.addKeyListener(new KeyAdapter() {
			  public void keyReleased(KeyEvent e) {
				  if(e.getKeyChar() != pressedKey) {
					  return;
				  }
				  Long keyBeenPressedFor = System.currentTimeMillis() - pressedTime;
				    rawData.add(new KeyTime(Character.toUpperCase(e.getKeyChar()), keyBeenPressedFor));
	            	//System.out.println("released: "+ keyBeenPressedFor);  
			  }
	 
	            public void keyTyped(KeyEvent e) {

	            }
	 
	            public void keyPressed(KeyEvent e) {
	            	pressedTime = System.currentTimeMillis();
	            	pressedKey = Character.toUpperCase(e.getKeyChar());
	            	//System.out.println("pressed: "+ pressedKey);
	            }
		});

		JButton button = new JButton("Add data");
		JButton traingBtn = new JButton("Train");
		JButton testBtn = new JButton("Test");
		final JLabel result = new JLabel ("result");
		testBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserKeyboardScan uks = getData(null);
				clear();
				
				weka.buildTestWekaFile(uks);
				try {
					result.setText(weka.test());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = userNameFld.getText();			
				App.data.put(userName, getData(userName));	
				App.printData();
				
				clear();
			}
		});
		
		traingBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				weka.buildTrainWekaFile();
				try {
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	
		panel.add(new JLabel("User Name:"));
		panel.add(userNameFld);
		panel.add(new JLabel("Text Input:"));
		panel.add(textFld);
		panel.add(button);
		panel.add(traingBtn);
		panel.add(testBtn);
		panel.add(result);
		
		
		add(panel);
		pack();
		setVisible(true);
	     
	}
	private void clear(){
		rawData = new LinkedList<>();
		userNameFld.setText("");
		textFld.setText("");
	}
	
	public static UserKeyboardScan getData(String user) {
		UserKeyboardScan uks = new UserKeyboardScan(user);
		Map<Character,Long> keyMap = new HashMap<>();
		for (KeyTime kt : rawData) {
			Long mean = keyMap.get(kt.getKey());
        	if (mean != null) {
        		keyMap.put(kt.getKey(), (mean+kt.getTime())/2);
        	} else {
        		keyMap.put(kt.getKey(), kt.getTime());
        	}
		}
		
		uks.setKeyboardScan(keyMap);
		return uks;
		
	}


	public static Long getPressedTime() {
		return pressedTime;
	}


	public static void setPressedTime(Long pressedTime) {
		MyKeyboardScanner.pressedTime = pressedTime;
	}


	public static Character getPressedKey() {
		return pressedKey;
	}


	public static void setPressedKey(Character pressedKey) {
		MyKeyboardScanner.pressedKey = pressedKey;
	}





	public static List<KeyTime> getRawData() {
		return rawData;
	}


	public static void setRawData(List<KeyTime> rawData) {
		MyKeyboardScanner.rawData = rawData;
	}
	
}
