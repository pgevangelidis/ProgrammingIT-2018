/*	The CustomerAccount class contains the methods to store and calculate the 
 * 	Customer Name, the initial balance, the update balance and the two performed actions
 * 	Sale process and return process
 */
public class CustomerAccount {	
// Declaration and initialization of variables	
	private static double totalCost, balance;
	private String customerName;
// This method resets the main GUI fields	
	public CustomerAccount()
	{
		resetFields();
	}	
	public void resetFields() {
		totalCost = 0.00;
		balance = 0;
	}	
// Executes the total cost multiplication
	 static double Transaction()	
	{
		totalCost = Wine.getWinePrice()*Wine.getWineQuantity();
		return totalCost;
	}
// stores the initial balance from the AssEx1 main class
	public double oldBalance(double baln) {
		balance = baln;
		return balance;
	}
	public double oldBalance()
	{
		return balance;
	}
// stores the customer name from the main class	
	public String getCustomerName(String name)
	{
		customerName = name;
		return customerName;		
	}
	public String getCustomerName()
	{	
		return customerName;
	}
// Each method handles the two different actions, sale and return	
	public double getUpdateSaleBalance()
	{
		balance = this.oldBalance() + CustomerAccount.Transaction();	
		return balance;
	}
	public double getUpdateReturnBalance()
	{	
// There is a levy 20% for each return
		double trans = CustomerAccount.Transaction();
		trans = Math.round(trans*80)/100.00;
		balance = this.oldBalance() - trans;
		return balance;
	}
}
