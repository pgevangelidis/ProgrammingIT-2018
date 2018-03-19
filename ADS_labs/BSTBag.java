import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTBag<E extends Comparable<E>> implements Bag<E>{
	
	private BSTBag.Node<E> root;
	private int size;

	public BSTBag() {
		// Construct an empty BST.
		root = null;
		size = 0;
	}
	
	
	// //////// Inner class //////////
	private static class Node<E extends Comparable<E>> {
		
		protected E element;
		protected Node<E> left, right;
		
		
		protected Node(E elem) {
			element = elem;
			left = null;
			right = null;
		}
		
		/*@SuppressWarnings("unchecked")
		public boolean contains(E elem)	{
			
			CountedElement<E> other= (CountedElement<E>) elem;
			int counter = other.getCount();
			
			int comp= element.compareTo(elem);
			if(comp ==0 && counter>0)
				return true;
			if(comp <0 && left!= null && left.contains(elem))
				return true;
			if(comp >0 && right!= null && right.contains(elem))
				return true;
			return false;
		}*/
	}
	
	/////////////// End of Inner Class////////////////////////////
	
	
	public boolean isEmpty() {
		// The method should check the size of the BSTBag
		// If the root is null, then the BSTBag is empty.
		// It doesn't matter how many elements are. 
		if (root == null)
			return true;
		else
			return false;
	}

	
	public int size() {
		// In order to find the size of the Bag, I have to iterate
		//with the iterable all the elements and count the number of them.
		return size;
	}

	
	@SuppressWarnings("unchecked")
	public boolean contains(E elem) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		if( root== null)
			return false;
		else
		{
			Iterator<E> iter = this.iterator();
			while(iter.hasNext())
			{
				CountedElement<E> check = (CountedElement<E>) iter.next();
				CountedElement<E> elem2 = (CountedElement<E>) elem;
				//flag = check.contains(elem);
				if(check.compareTo(elem2)==0)
				{
					int count = check.getCount();
					if(count > 0)
						flag = true;
						return flag;
				}
			}
			
		}
		
		return flag;
	}

	
	@SuppressWarnings("unchecked")
	public boolean equals(Bag<E> that) {
		boolean flag = true;
		Iterator<E> iterThis = this.iterator();
		Iterator<E> iterThat = that.iterator();
		
		while(iterThat.hasNext())
		{
			CountedElement<E> checkThat = (CountedElement<E>) iterThat.next();
			
			while(iterThis.hasNext())
			{
				CountedElement<E> checkThis = (CountedElement<E>) iterThis.next();
				if(checkThat.compareTo(checkThis)==0)
				{
					int countThat = checkThat.getCount();
					int countThis = checkThis.getCount();
					if(countThat==countThis)
						break;
				}else
				{
					flag = false;
					return flag;
				}
			}
			
		}
		
		
		return flag;
	}

	
	public void clear() {
		root = null;
	}
	
		
	@SuppressWarnings("unchecked")
	public void add(E elem) {
		int direction = 0;
		BSTBag.Node<E> parent = null, curr = root;
		//whatever is added.
		size++;
		
		for (;;) {
			if (curr == null) {
				BSTBag.Node<E> ins = new BSTBag.Node<E> (elem);
				if (root == null)
					root = ins;
				else if (direction < 0)
					parent.left = ins;
				else
					parent.right = ins;
				return;
			}
			
			direction = elem.compareTo(curr.element);
			
			if (direction == 0)
			{
				CountedElement<E> element2 = (CountedElement<E>) curr.element;
				element2.setCount(element2.getCount()+1);
				return;
			}
			parent = curr;
			if (direction < 0)
				curr = curr.left;
			else
				curr = curr.right;
		}
	}

	
	@SuppressWarnings("unchecked")
	public void remove(E elem) {
		int direction=0;
		BSTBag.Node<E> parent = null, curr = root;
		//what ever is removed 
		
		
		for(;;)
		{
			if (curr == null)
			{
				return;
			}
			
			direction = curr.element.compareTo(elem);
			
			if(direction == 0)
			{
				CountedElement<E> element2 = (CountedElement<E>) curr.element;
				element2.setCount(element2.getCount()-1);
				size--;
				return;
			}
			
			parent = curr;
			if (direction > 0)
				curr = parent.left;
			else
				curr = parent.right;
		}
	}

	
	public Iterator<E> iterator() {		
		return new InOrderIterator();
	}
	
	////////////////// Inner Class /////////////////////////
	private class InOrderIterator implements Iterator<E>{
		// A BSTSet.InOrderIterator object is an object that will traverse
		// in in-order, the BSTSet object representing a set
		// iterator is represented by a stack of references to nodes still to be 
		// visited, track
		private Stack<BSTBag.Node<E>> track;
		
		private InOrderIterator(){
			track = new LinkedStack<BSTBag.Node<E>>();
			for (BSTBag.Node<E> curr = root; curr!=null; curr = curr.left)
			track.push(curr);
		}
		
		public boolean hasNext(){
			return (!track.empty());
			
		}
		
		public E next(){
			if (track.empty())
				throw new NoSuchElementException();
			BSTBag.Node<E> place = track.pop();
			for(BSTBag.Node<E> curr = place.right; curr !=null;curr = curr.left)
				track.push(curr);
			return (E) place.element;
		}
		
		
	}
}
