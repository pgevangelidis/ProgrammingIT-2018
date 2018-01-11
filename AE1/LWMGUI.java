/*	This class contains the main GUI class and the design of the Jframe
 * 	All the required text fields, labels and buttons
 * 	Along with the ActionListener - ActionPerform class, which handles the two actions, sale and return
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LWMGUI extends JFrame implements ActionListener
{
	public JButton saleButton, returnButton;
	public JTextField numBottles, costBottle,wineName;
	private JTextField  totalCost, balance;
	private JLabel wineNameLabel, numBottlesLabel, costBottleLabel, totalCostLabel, balanceLabel, wineTypeLabel;
	private Wine wineObject;
	private CustomerAccount accountObject;
// This is the main GUI class	
	public LWMGUI(Wine mdlWine, CustomerAccount cAccount) {
		wineObject = mdlWine;
		accountObject = cAccount;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,170);
		setLocation(500,300);
		String title = String.format("LilyBank Wine Merchants: %s", accountObject.getCustomerName());
		setTitle(title);		
		this.setLayout(new FlowLayout());
		this.layoutComponents();
	}
/*	Here I set the layout of the GUI
 *  In order to present it accordingly to the exercise
 */ 	
	private void layoutComponents()
	{
		this.layoutTop();
		this.layoutMiddle();
		this.layoutMiddleType();
		this.layoutBottom();
	}	
/*	this are all the necessary buttons and text fields 
 *  for the user interface
 */	
	private void layoutTop()
	{
		wineName = new JTextField("",14);
		wineName.addActionListener(this);
		numBottles = new JTextField("",4);
		numBottles.addActionListener(this);
		costBottle = new JTextField("",4);
		costBottle.addActionListener(this);
		wineNameLabel = new JLabel("Name:");
		numBottlesLabel = new JLabel("Quantity:");
		costBottleLabel = new JLabel("Price £:");
		
		JPanel top = new JPanel();
		top.add(wineNameLabel);
		top.add(wineName);
		top.add(numBottlesLabel);
		top.add(numBottles);
		top.add(costBottleLabel);
		top.add(costBottle);
		this.add(top);
	}	
	private void layoutMiddle()
	{
		JPanel middle = new JPanel();		
		saleButton = new JButton("Process Sale");
		saleButton.addActionListener(this);
		returnButton = new JButton("Process Return");
		returnButton.addActionListener(this);
		middle.add(saleButton);
		middle.add(returnButton);
		this.add(middle);
	}	
	private void layoutMiddleType()
	{
		JPanel midtype = new JPanel();
		midtype.setLayout(new BorderLayout());
		wineTypeLabel = new JLabel("Wine purchased:                                                         ");
		midtype.add(wineTypeLabel, BorderLayout.WEST);
		this.add(midtype);	
	}
// I have block the text field for the "total cost" & " current balance", because the user does not interact with them.
	private void layoutBottom()
	{
		String blnString="";
		double bln=0.00;
		
		JPanel bottom = new JPanel();			
		totalCost = new JTextField("",10);
		balance = new JTextField("",10);
		totalCostLabel = new JLabel("Amount of Transaction:");
		balanceLabel = new JLabel("Current balance:");		
		bottom.add(totalCostLabel);
		bottom.add(totalCost);
		totalCost.setEditable(false);
		bottom.add(balanceLabel);
		bottom.add(balance);
		balance.setEditable(false);
// Handles the negative current balance 		
		bln = accountObject.oldBalance();
		if (bln<0) {
			bln = Math.abs(Math.round(bln*100.00)/100.00);
			blnString = String.format("£ " + bln +"CR");
		}else {
			bln = Math.round(bln*100.00)/100.00;
			blnString = String.format("£ " + bln);
		}
		balance.setText(""+blnString);		
		this.add(bottom);		
	}
//	The Action Events 
	public void actionPerformed(ActionEvent p)
	{
		String wnameString = "", quantString = "", priceString="";
		int quant=0;
		double price = 0.00; 		
// Handling the buttons
//Sale Button
		if (p.getSource() == this.saleButton) {
/*	The infinite loop is for the continuous wrong value entries 
*  First is the Wine Type, then the quantity with the Price
*  the quantity and the price have two exceptions 1) string error 2) negative value
*/			
			for (;;) 
			{
				wnameString = this.wineName.getText().trim();
				if (wnameString.length()==0){
					JOptionPane.showMessageDialog(null,"The wine type cannot be empty.\nPlease enter a type of Wine", "Empty Type", JOptionPane.ERROR_MESSAGE);
					clearFields();
					wineObject.eraseFields();
					break;
				}else {
					wineObject.getWineName(wnameString);
					try {
						quantString = this.numBottles.getText().trim();
						priceString = this.costBottle.getText().trim();
				// parse the string to numbers
						quant = Integer.parseInt(quantString);
						price = Double.parseDouble(priceString);
					}catch (NumberFormatException nfx) {
						JOptionPane.showMessageDialog(null,"The quantity  must be an integer & the price must be a type double", "String error", JOptionPane.ERROR_MESSAGE);
						clearFields();
						wineObject.eraseFields();
					}
				if (quant<0 || price<0) {
					JOptionPane.showMessageDialog(null,"The quantity or the price cannot be negative values.\nPlease enter a positive value", "Negative Number", JOptionPane.ERROR_MESSAGE);
					clearFields();
					wineObject.eraseFields();
				}else {
					wineObject.getWineQuantity(quant);
					wineObject.getWinePrice(price);
					updateSaleField(wnameString);
					clearFields();
				}
				break;
				}
			}						
		}
//Return Button
		else if (p.getSource() == this.returnButton){
/*	The infinite loop is for the continuous wrong value entries 
 *  First is the Wine Type, then the quantity with the Price
 *  the quantity and the price have two exceptions 1) string error 2) negative value
 */ 
			for (;;) 
			{
				wnameString = this.wineName.getText().trim();
				if (wnameString.length()==0){
					JOptionPane.showMessageDialog(null,"The wine type cannot be empty.\nPlease enter a type of Wine", "Empty Type", JOptionPane.ERROR_MESSAGE);
					clearFields();
					wineObject.eraseFields();
					break;
				}else {
					wineObject.getWineName(wnameString);
					try {
						quantString = this.numBottles.getText().trim();
						priceString = this.costBottle.getText().trim();
				// parse the string to numbers
						quant = Integer.parseInt(quantString);
						price = Double.parseDouble(priceString);
					}catch (NumberFormatException nfx) {
						JOptionPane.showMessageDialog(null,"The quantity  must be an integer & the price must be a type double.\nPlease enter a number", "String error", JOptionPane.ERROR_MESSAGE);
						clearFields();
						wineObject.eraseFields();
					}
				if (quant<0 || price<0) {
					JOptionPane.showMessageDialog(null,"The quantity or the price cannot be negative values.\nPlease enter a positive value", "Negative Number", JOptionPane.ERROR_MESSAGE);
					clearFields();
					wineObject.eraseFields();
				}else {
					wineObject.getWineQuantity(quant);
					wineObject.getWinePrice(price);
					updateReturnField(wnameString);
					clearFields();
				}
				break;
				}	
			}	
		}
	}
// after each button event the wine fields will erase
	private void clearFields()
	{
		wineName.setText("");
		numBottles.setText("");
		costBottle.setText("");
	}
// update the Wine Purchased, The total cost and the new balance field
	private void updateSaleField(String wname)
	{
		wineTypeLabel.setText("Wine Purchased: " + wname + "                                               ");
// rounding the total cost
		double tlc = CustomerAccount.Transaction();
		tlc = Math.round(tlc*100.00)/100.00;
		totalCost.setText("£ " + tlc);
// Here I deal with the negative balance		
		double bln = accountObject.getUpdateSaleBalance();
		bln = Math.round(bln*100.00)/100.00;
		if (bln<0) {
			bln = Math.abs(bln);
			balance.setText("£ " + bln +"CR");
		}else {
			balance.setText("£ " + bln);
		}
	}
	private void updateReturnField(String wname)
	{
		wineTypeLabel.setText("Wine Purchased: " + wname + "                                               ");
// rounding the total cost 
		double tlc = CustomerAccount.Transaction();
		tlc = Math.round(tlc*80.00)/100.00;
		totalCost.setText("£ " + tlc);
//rounding the balance
		double bln = accountObject.getUpdateReturnBalance();
		bln = Math.round(bln*100.00)/100.00;
// Deal with the negative balance
		if (bln<0) {
			bln = Math.abs(bln);
			balance.setText("£ " + bln +"CR");
		}else {
			balance.setText("£ " + bln);
		}
	}
}
