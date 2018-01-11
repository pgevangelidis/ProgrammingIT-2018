/*
 * 
 */

public class Wine {
// Declaration & initialization of variables 
	private static String wineName;
	private static int wineQuantity;
	private static double winePrice;
// Erase all initial variables	
	public Wine()
	{
		eraseFields();
	}
	public void eraseFields()
	{
		wineName = "";
		wineQuantity = 0;
		winePrice = 0.00;
	}
// stores the wine name from the main GUI 
	public String getWineName(String wname)
	{
		wineName = wname;
		return wineName;
	}
	public String getWineName()
	{
		return wineName;
	}
// stores the quantity value from the main GUI
	public int getWineQuantity(int quant)
	{ 
		wineQuantity = quant;
		return wineQuantity;
	}
	static int getWineQuantity() {
		return wineQuantity;
	}
//Stores the Price from the main GUI
	public double getWinePrice(double price)
	{
		winePrice = price;
		return winePrice;
	}
	static double getWinePrice() {
		return winePrice;
	}
}
