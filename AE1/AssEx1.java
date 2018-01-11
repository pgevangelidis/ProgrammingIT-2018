/*	The main class of the program
 * 	Contains the two prompt dialog messages with the Customer name and the initial Balance
 * 	Also contains the exceptions for wrong entries
 * 	Two objects are created and are used as parameters to the LWMGUI method
 */

import javax.swing.*;
public class AssEx1 {
	public static void main(String [] args)
	{
		String customerName="";
		double oldBalance=0;
		Wine mdlWine = new Wine();
		CustomerAccount cAccount = new CustomerAccount();
// Exception of an empty Customer Name
		customerName = JOptionPane.showInputDialog(null,"Enter Customer Name: ","");	
		if (customerName.length() != 0) {	
			cAccount.getCustomerName(customerName);
		}else if(customerName.length() == 0){	
			JOptionPane.showMessageDialog(null,"You enter an empty name. The program will terminate.\nThank you", "Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}	
// try to catch the initial balance
	for (;;) {
		try {
			String balnString = JOptionPane.showInputDialog(null,"Enter previous balance: ","");
			oldBalance = Double.parseDouble(balnString);
			cAccount.oldBalance(oldBalance);
			break;
		}catch (NumberFormatException nfx) {
			JOptionPane.showMessageDialog(null,"Enter a type Double", "Old Balance", JOptionPane.ERROR_MESSAGE);
		}
	}	
		LWMGUI view = new LWMGUI(mdlWine, cAccount);	
		view.setVisible(true);
	}

}
