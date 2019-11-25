import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * BTreeNode.java Sunday, October 20, 2019
 * 
 * @author wilcda01 Node class for the BTree implementation
 *
 * @param <E>
 */
public class BTreeNode<E> {

	BTreeNode<E> parent;
	Comparator<E> comp;
	ArrayList<E> data;
	ArrayList<BTreeNode<E>> children;
	int degree;

	/**
	 * BTreeNode constructor using a comparator. Takes a node and a degree.
	 * 
	 * @param p   node
	 * @param c   comparator
	 * @param deg degree
	 */
	BTreeNode(BTreeNode<E> p, Comparator<E> c, int deg) {
		parent = p;
		comp = c;
		degree = deg;
		data = new ArrayList<E>();
		children = new ArrayList<BTreeNode<E>>();
	}

	/**
	 * Returns the degree of the node
	 * 
	 * @return degree of node
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Return the data content of the node as a string
	 */
	public String toString() {
		if (data.isEmpty()) {
			return "[]";
		}

		String s = "[" + data.get(0);

		for (int i = 1; i < data.size(); i++) {
			s = s + "," + data.get(i);
		}
		return s + "]";
	}

	/**
	 * Returns whether the node is a leaf node
	 * 
	 * @return true if a leaf, false otherwise
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}

	/**
	 * Adds a child to the end of the list
	 * 
	 * @param newChild another child
	 */
	public void addChild(BTreeNode<E> newChild) {
		newChild.parent = this;
		children.add(newChild);
	}

	/**
	 * Helper method - a slightly simpler and more functional way of adding a child
	 * at a specific index
	 * 
	 * @param newChild
	 * @param index
	 */
	private void addChild(BTreeNode<E> newChild, int index) {
		newChild.parent = this;
		children.add(index, newChild);
	}

	/**
	 * Populates the children list with the children in newchildren
	 * 
	 * @param newChildren all new children
	 */
	public void setChildren(List<BTreeNode<E>> newChildren) {
		for (BTreeNode<E> c : newChildren) {
			c.parent = this;
			children.add(c);
		}
	}

	/**
	 * Returns the child of this node at position pos
	 * 
	 * @param pos index
	 * @return the child at pos
	 */
	public BTreeNode<E> getChild(int pos) {
		if (pos >= children.size() || pos < 0) {
			return null;
		}
		// ArrayList.get(index) returns null if not found
		return children.get(pos);
	}

	/**
	 * Returns the children of this node in an ArrayList
	 * 
	 * @return children of node
	 */
	public ArrayList<BTreeNode<E>> getChildren() {
		return children;
	}

	/**
	 * Returns the number of elements in the node
	 * 
	 * @return number of elements
	 */
	public int dataSize() {
		return data.size();
	}

	/**
	 * Returns the content of this node in an arraylist
	 * 
	 * @return content of node
	 */
	public ArrayList<E> getData() {
		return data;
	}

	/**
	 * Returns the content at position pos in this node
	 * 
	 * @param pos index
	 * @return content at pos
	 */
	public E getData(int pos) {
		if (pos >= data.size() || pos < 0) {
			return null;
		}
		return data.get(pos);
	}

	/**
	 * Populates the data list with the elements in newDataSet
	 * 
	 * @param newDataSet set of new data
	 */
	public void setData(List<E> newDataSet) {
		data.addAll(newDataSet);
	}

	/**
	 * Returns the position at which newData should be placed
	 * 
	 * @param newData given data
	 * @return position where newData should go
	 */
	public int findInsertPos(E newData) {
		int i;

		for (i = 0; i < data.size(); i++) {
			if (comp.compare(newData, data.get(i)) < 0) { // not meant to handle duplicates
				return i;
			}
		}

		return i;
	}

	/**
	 * Returns the index in which target is found in this nodes ArrayList
	 * 
	 * @param target
	 * @return index where target is found
	 */
	public int indexOf(E target) {
		int c = Collections.binarySearch(data, target, comp);
		return c < 0 ? -1 : c; // if search did not find target, return -1, otherwise, return search value
	}

	/**
	 * Returns true if this node has target among its data
	 * 
	 * @param target data
	 * @return true if found, false otherwise
	 */
	public boolean contains(E target) {
		int c = Collections.binarySearch(data, target, comp);
		return c >= 0;
	}

	/**
	 * Returns true if there is an overflow in the node
	 * 
	 * @return true if overflow, false otherwise
	 */
	public boolean isOverflow() {
		return data.size() >= degree;
	}

	/**
	 * Handles overflow by splitting the nodes
	 * 
	 * @return new parent
	 */
	private BTreeNode<E> split() {
		BTreeNode<E> right = new BTreeNode<E>(parent, comp, degree);
		int mid = data.size() / 2;
		for (int i = mid + 1; i < data.size(); i++) { // this shouldn't work??? Ex. 3 - 1 = 2, so the loop runs twice,
														// but only one item can be removed. It works so I'm not
														// changing it, but I'm not sure how it does.
			right.add(data.remove(mid + 1));
		}

		if (!isLeaf()) {
			for (int j = mid + 1; j < children.size() + 1; j++) {
				right.addChild(children.remove(mid + 1));
			}
		}

		E d = data.remove(mid);

		if (parent == null) {
			parent = new BTreeNode<E>(null, comp, degree);
			parent.addChild(this);
			parent.addChild(right);
			parent.add(d);
			return parent;
		}

		if (hasSiblings()) { // this wasn't in the specification, but I literally could not get my code to
								// work without it. It simply checks if the node has siblings, and if so,
			// removes all siblings to the right, adds the right node, and then adds the
			// siblings back. This ensures they are all in order
			ArrayList<BTreeNode<E>> temp = new ArrayList<>();
			boolean isThis = false;
			int t = 0;
			int size = parent.children.size();
			for (int k = 0; k < size - 1; k++) {
				if (parent.getChild(k) == this && !isThis) {
					isThis = true;
					t = k + 1;
				}

				if (isThis) {
					temp.add(parent.children.remove(t));
				}
			}

			parent.addChild(right);

			for (int l = 0; l < temp.size(); l++) {
				parent.addChild(temp.get(l));
			}

			return parent.add(d);
		}

		parent.addChild(right);
		return parent.add(d);

	}

	/**
	 * Adds d to node's data collection in the proper place, aka ordered
	 * 
	 * @param d
	 * @return null if no overflow after adding. new node if split
	 */
	public BTreeNode<E> add(E d) {

		if (data.size() == 0) {
			data.add(d);
		} else {
			data.add(findInsertPos(d), d);
		}

		if (isOverflow()) {
			return split();
		}

		return null;
	}

	/**
	 * Utility method, checks if the node has siblings
	 * 
	 * @return true if parent exists and has children besides this node
	 */
	private boolean hasSiblings() {
		return parent != null && parent.children.size() > 1;
	}

	/****************/

	/**
	 * Checks left and right siblings for a donor post-removal
	 * 
	 * @return richer sibling, left if both have same amount of data values
	 */
	public BTreeNode<E> findDonor() {
		int i = 0;

		if (parent == null) {
			return null;
		}

		int pSize = parent.children.size();
		for (BTreeNode<E> child : parent.children) {
			if (child == this) { // this node found
				if (i == 0) { // first index, only right sibling possible
					return parent.getChild(1);
				} else if (i > 0 && i < pSize - 1) {
					BTreeNode<E> left = parent.getChild(i - 1);
					BTreeNode<E> right = parent.getChild(i + 1);
					return left.dataSize() < right.dataSize() ? right : left;
				} else if (i == pSize - 1) { // last index, only left sibling possible
					return parent.getChild(i - 1);
				}
			}
			i++;
		}

		return null;
	}

	/**
	 * Checks if data can be removed from node without underflow
	 * 
	 * @return true if can donate without underflow, false otherwise
	 */
	public boolean canDonate() {
		return data.size() > (Math.ceil(degree / 2.0) - 1);
	}

	/**
	 * Rotates the B-tree when node has sibling which can donate without underflow
	 * 
	 * @param sib          donating sibling
	 * @param myGainIndex  index to place data
	 * @param sibLostIndex index to take data
	 * @param pSepIndex    parent index
	 */
	private void rotate(BTreeNode<E> sib, int myGainIndex, int sibLostIndex, int pSepIndex) {
		E parentVal = parent.getData(pSepIndex);
		E sibVal = sib.getData(sibLostIndex);
		data.add(myGainIndex, parentVal);
		E myVal = getData(myGainIndex);
		parent.data.set(pSepIndex, sibVal);
		sib.remove(sibVal);
		if (!sib.isLeaf()) {
			if (comp.compare(myVal, parentVal) > 0) { // left donor
				BTreeNode<E> n = sib.children.remove(sib.children.size() - 1);
				addChild(n, 0);
			} else { // right donor
				BTreeNode<E> n = sib.children.remove(0);
				addChild(n, children.size()); // I don't know how this works, size = 3, index of 3 should be null. But
												// it fixes my code? Somehow?
			}
		}
	}

	/**
	 * Rotates the B-tree when node does not have a sibling which can donate without
	 * underflow
	 * 
	 * @param left      node
	 * @param right     node
	 * @param pSepIndex parent index
	 * @return joined node
	 */
	public static <E> BTreeNode<E> join(BTreeNode<E> left, BTreeNode<E> right, int pSepIndex) {
		BTreeNode<E> parent = left.parent;
		if (pSepIndex == -1) { // odd? ran into weird cases with this
			pSepIndex = 0;
		}
		
		E parentVal = parent.getData(pSepIndex);
		
		left.add(parentVal);

		int dSize = right.dataSize();
		for (int j = 0; j < dSize; j++) {
			E data = right.getData(0);
			left.add(data);
			right.data.remove(data);
		}

		int cSize = right.children.size();
		for (int i = 0; i < cSize; i++) {
			BTreeNode<E> child = right.getChild(0);
			left.addChild(child);
			right.children.remove(child);
		}

		parent.children.remove(right);
		parent.remove(parentVal);
		
		if (parent.parent == null && parent.data.isEmpty()) {
			left.parent = null;
			return left;
		} else {
			return null;
		}

	}

	/**
	 * Rebalances the tree
	 * 
	 * @return joined node
	 */
	public BTreeNode<E> handleUnderflow() {
		return null;
	}

	/**
	 * Removes target if was found in b tree
	 * 
	 * @param target
	 * @return null if no underflow, otherwise return joined node
	 */
	public BTreeNode<E> remove(E target) {
		int index = data.indexOf(target);

		data.remove(target);
		BTreeNode<E> donor = findDonor();

		if (isUnderflow()) {
			if (donor != null) {
				int c = comp.compare(donor.getData(0), target);
				if (donor.canDonate()) { // rotate
					if (c < 0) { // donor is left
						rotate(donor, index, donor.dataSize() - 1, parent.children.indexOf(this) - 1);
					} else { // donor is right
						rotate(donor, index, 0, parent.children.indexOf(this));
					}
				} else { // join ; if donor is bigger, empty is left
					if (c < 0) {
						return join(donor, this, parent.children.indexOf(this) - 1);
					} else { // donor is right
						return join(this, donor, parent.children.indexOf(this));
					}

				}
			}
		}
		return null;
	}

	/**
	 * Helper method - basically a !canDonate()
	 * 
	 * @return if the node is underflowed
	 */
	private boolean isUnderflow() {
		return data.size() < (Math.ceil(degree / 2.0) - 1);
	}
}
