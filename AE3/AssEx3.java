import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * The main class
 */
public class AssEx3 {
	/**
	 * The main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		FitnessProgram fProgram = new FitnessProgram();

		SportsCentreGUI display = new SportsCentreGUI(fProgram);
		display.setVisible(true);
	}
}
