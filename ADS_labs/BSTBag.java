import java.util.Iterator;

public class BSTBag<E> implements Bag<E>{
	
	private BSTBag.Node<E> root;

	public BSTBag() {
		// Construct an empty BST.
		root = null;
	}
	
	
	// //////// Inner class //////////
	private static class Node<E> {
		protected E element;
		protected Node<E> left, right;

		protected Node(E elem) {
			element = elem;
			left = null;
			right = null;
		}

		public Node<E> deleteTopmost() {
			if (this.left == null)
				return this.right;
			else if (this.right == null)
				return this.left;
			else { // this node has two children
				this.element = this.right.getLeftmost();
				this.right = this.right.deleteLeftmost();
				return this;
			}
		}

		private E getLeftmost() {
			Node<E> curr = this;
			while (curr.left != null)
				curr = curr.left;
			return curr.element;
		}

		public Node<E> deleteLeftmost() {
			if (this.left == null)
				return this.right;
			else {
				Node<E> parent = this, curr = this.left;
				while (curr.left != null) {
					parent = curr;
					curr = curr.left;
				}
				parent.left = curr.right;
				return this;
			}
		}

	}
	
	/////////////// End of Inner Class////////////////////////////
	
	
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public boolean contains(E element) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean equals(Bag<E> that) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	
	public void add(E element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(E element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
