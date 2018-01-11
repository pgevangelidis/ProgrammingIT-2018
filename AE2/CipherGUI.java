import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;
	
	//private char [] keywordArray;
	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	
	//private MonoCipher mcipher;
	//private VCipher vcipher;
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{		
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{ 
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
	    String keyword="", fileName="";
	    boolean validKey=false, validFile=false, actionB=false;	    

	    while (validKey!=true && validFile!=true)
	    {
	    	// Firstly, I store the Keyword in a string
	    	keyword = this.keyField.getText().trim();
	    	fileName = this.messageField.getText().trim();
	    	if (keyword.length()!=0 && fileName.length()!=0) { 
	    		// Next, is the validation of the Keyword
	    		validKey = this.getKeyword(keyword);
	    		validFile = this.processFileName(fileName);
	    	}else {	
	    		JOptionPane.showMessageDialog(null,"The keyword or the message field is empty.\nPlease enter a keyword or a message:","Empty Type",JOptionPane.ERROR_MESSAGE);
	    		break;
	    	}
	    }
	    if (validKey==true && validFile==true)
	    {	
	    	char lastLetter = fileName.charAt((int)fileName.length()-1); 
	    	if (lastLetter=='C')
	    	{
	    		actionB = true;
	    	}
	    	if(e.getSource()==this.monoButton) {
	    		new MonoCipher(keyword,fileName,actionB);
	    	}else if (e.getSource()==this.vigenereButton) {
	    		new VCipher(keyword,fileName,actionB);
	    	}
		
	    	// check if the necessary files have been created
	    	boolean vigenere = false;
	    	vigenere = this.processFile(vigenere,fileName);
	    	if (vigenere== true)
	    	{
	    		JOptionPane.showMessageDialog(null, "The Encrypted/Decrypted file and the report have been created.\nDo you want to proceed with another file?","Files created successfully",JOptionPane.YES_NO_OPTION);
	    		keyField.setText("");
	    		messageField.setText("");
	    	}else {
	    		JOptionPane.showMessageDialog(null, "The files have not been created.\n Do you want to try again?","Files not created",JOptionPane.YES_NO_OPTION);
	    	}
	    }
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword(String kw)
	{
		boolean validKey = false;
		char key;
		StringBuilder keyBuilder = new StringBuilder(kw);
		// If the user enters a keyword that contains anything else than capital letters then it sends an exception
	    for (int i=0; i< keyBuilder.length(); i++)
	    {
	    	key = keyBuilder.charAt(i);
	    	if (key<'A' || key>'Z')
	    	{
	    		JOptionPane.showMessageDialog(null,"The Keyword must contain only Capital letters.\nOnly.","Keyword Error",JOptionPane.ERROR_MESSAGE);
	    		keyField.setText("");
	    		return validKey;
	    	}
	    		int pace =(int)keyBuilder.length()-1;
	    		//If the user enters a keyword with duplicate letters, then an exception is caught
	    		while (pace>i)
	    		{
	    			if (key == keyBuilder.charAt(pace))
	    			{
	    				JOptionPane.showMessageDialog(null,"The Keyword contains duplicate letters.\nChoose another keyword.","Keyword Error",JOptionPane.ERROR_MESSAGE);
	    				keyField.setText("");
	    				return validKey;
	    			}
	    			pace --;
	    		}
	    }
	    // if the keyword passes all the exception then it will return to the program.
	    validKey = true;
	    return validKey;  
	}	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName(String fname)
	{
		boolean validFile = false;
		char file;
		StringBuilder fileBuilder = new StringBuilder(fname);
		file = fileBuilder.charAt((int)fileBuilder.length()-1);
		if (file!='P' && file!='C')
		{
			JOptionPane.showMessageDialog(null, "The message file must end with one of the letters P or C.\nP: to be encrypted.\nC: to be decrypted.","File name error",JOptionPane.ERROR_MESSAGE);
			messageField.setText("");
		}else {
			validFile = true;	
		}
		return validFile; 
	}	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere, String fn)
	{
	    boolean filecheck = vigenere;
	    String message="", report="";
		StringBuilder fBuilder = new StringBuilder(fn);
		char ch = fBuilder.charAt((int)fBuilder.length()-1);
		if (ch=='P')
		{
			message = fBuilder.substring(0, fBuilder.length()-1)+"C.txt";
		}else if (ch=='C')
		{
			message = fBuilder.substring(0, fBuilder.length()-1)+"D.txt";
		}
		report = fBuilder.substring(0, fBuilder.length()-1)+"F.txt";
		try {
			FileReader check1 = new FileReader(message);
			FileReader check2 = new FileReader(report);
			check1.close();
			check2.close();
		}catch (IOException e) {
			filecheck = false;
		}
		filecheck = true;
		return filecheck;
	}
}
