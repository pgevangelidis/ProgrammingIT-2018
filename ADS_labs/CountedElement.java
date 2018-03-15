public class CountedElement<E extends Comparable<E>> implements Comparable<CountedElement<E>> {
	private E element;
	private int count;


	public CountedElement(E e, int count){
		//constructor - to complete
		this.element = e ; 
		this.count = count;
	}
	
	public CountedElement(E e){
		//constructor - to complete
		element = e;
		this.count = 1;
	}

	//add getters and setters
	public void setElement(E e)
	{
		this.element = e;
	}
	
	public void setCount(int c)
	{
		this.count = c;
	}
	
	public E getElement()
	{
		return this.element;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	//add toString() method
	public String toString()
	{
		String pair = "(" + getElement() + "," + getCount() + ")";
		return pair;
	}
	
	public int compareTo(CountedElement<E> sC1) {
    
		if(element.compareTo(sC1.element)>0)
			return 1;
		else if(element.compareTo(sC1.element)==0)
			return 0;							
		else
			return -1;
	}

}
