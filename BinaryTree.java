//Reynaldo Garcia Sidaya, Jr.
//BSCS 2-A

package Tree;

public class BinaryTree<E> {
	protected static class Node<E> {
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		
		public E getElement() { return element; }
		public Node<E> getParent() { return parent; }
		public Node<E> getLeft() { return left; }
		public Node<E> getRight() { return right; }
		
		public void setElement(E e) { element = e; }
		public void setParent(Node<E> parentNode) { parent = parentNode; }
		public void setLeft(Node<E> leftChild) { left = leftChild; }
		public void setRight(Node<E> rightChild) { right = rightChild; }
	}
	
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
		return new Node<E>(e, parent, left, right);
	}
	
	public int numChildren(Position<E> p) {
		int count = 0;
		if (left(p) != null)
			count++;
		if (right(p)!= null)
			count++;
		return count;
	}
	
	public boolean isEmpty() {	return size() == 0;	}
	public boolean isInternal(Position<E> p) {	return numChildren(p) > 0;	}
	public boolean isExternal(Position<E> p) {	return numChildren(p) == 0;	}
	public boolean isRoot(Position<E> p) {	return p == root();	}
	
	protected Node<E> root = null;
	private int size = 0;
	
	public BinaryTree() { }
	
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node))
			throw new IllegalArgumentException("Not valid position type");
		@SuppressWarnings("unchecked")
		Node<E> node = (Node<E>) p;
		if (node.getParent() == node)
			throw new IllegalArgumentException("p is no longer in the tree");
		return node;
	}
	
	public int size() {
		return size;
	}
	
	public Node<E> root(){
		return root;
	}
	
	public Node<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}
	
	public Node<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	}
	
	public Node<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight();
	}
	
	public Node<E> addRoot(E e) throws IllegalStateException {
		if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null);
		size = 1;
		return root;
	}

	public Node<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getLeft() != null)
			throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e, parent, null, null);
		parent.setLeft(child);
		size++;
		return child;
	}

	public Node<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getRight() != null)
			throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e, parent, null, null);
		parent.setRight(child);
		size++;
		return child;
	}
	
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E temp = node.getElement();
		node.setElement(e);
		return temp;
	}
	
	public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
		size +=t1.size() + t2.size();
		if (!t1.isEmpty()) {
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}
		if (!t2.isEmpty()) {
			t2.root.setParent(node);
			node.setLeft(t2.root);
			t2.root = null;
			t2.size = 0;
		}
	}
	
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if (numChildren(p) == 2)
			throw new IllegalArgumentException("p has two children");
		Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
		if (child != null)
			child.setParent(node.getParent());
		else {
			Node<E> parent = node.getParent();
			if (node == parent.getLeft())
				parent.setLeft(child);
			else
				parent.setRight(child);
		}
		size--;
		E temp = node.getElement();
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(node);
		return temp;
	}
}