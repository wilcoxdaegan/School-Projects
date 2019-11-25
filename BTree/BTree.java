import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * BTree.java Sunday, October 20, 2019
 * 
 * @author wilcda01 Implementation of a BTree using BTreeNode
 *
 * @param <E>
 */
@SuppressWarnings("unused")
public class BTree<E> {
	int degree;
	Comparator<E> comp;
	BTreeNode<E> root;

	/**
	 * BTree constructor, takes a degree and a comparator
	 * 
	 * @param c   comparator
	 * @param deg degree
	 */
	BTree(Comparator<E> c, int deg) {
		comp = c;
		degree = deg;
		root = null;
	}

	/**
	 * Looks through data in ptr and returns child to which target belongs
	 * 
	 * @param ptr    pointer value
	 * @param target target data
	 * @return if ptr is leaf, returns null, otherwise, returns child
	 */
	private static <E> BTreeNode<E> stepDown(BTreeNode<E> ptr, E target) {
		if (ptr.isLeaf()) {
			return null;
		}

		int i;

		for (i = 0; i < ptr.getData().size(); i++) {
			if (ptr.comp.compare(target, ptr.getData(i)) < 0) {
				return ptr.getChild(i);
			}
		}

		return ptr.getChild(i);
	}

	/**
	 * Finds and returns leaf node to which target should added OR belong by calling
	 * step down
	 * 
	 * @param target
	 * @return the ptr where target belongs
	 */
	public BTreeNode<E> findLeaf(E target) {
		BTreeNode<E> ptr = root;
		BTreeNode<E> temp = ptr;
		while (ptr != null) {
			temp = ptr;
			ptr = stepDown(ptr, target);
		}

		return temp;
	}

	/**
	 * Adds newData to the tree, assuming no duplicates
	 * 
	 * @param newData data
	 */
	public void add(E newData) {
		if (root == null) {
			root = new BTreeNode<E>(null, comp, degree);
			root.add(newData);
		} else {
			BTreeNode<E> ptr = findLeaf(newData);
			BTreeNode<E> par = ptr.add(newData);

			if (par != null) {
				root = par;
			}
		}

	}

	/**
	 * Finds and returns the node containing target if it exists in the tree, null
	 * otherwise.
	 * 
	 * @param target data
	 * @return node of target, or null if not found
	 */
	private BTreeNode<E> getNode(E target) {
		BTreeNode<E> ptr = root;

		while (ptr != null) {
			if (ptr.contains(target)) {
				return ptr;
			}

			ptr = stepDown(ptr, target);
		}

		return null;
	}

	/**
	 * String representation of the tree, level order
	 */
	public String toString() {
		String s = "";

		Queue<BTreeNode<E>> q = new LinkedList<BTreeNode<E>>();
		q.add(root);

		if (root == null || root.dataSize() == 0) {
			return "[]";
		} else {
			int count = q.size();

			if (count == 0) {
				return s;
			}

			while (count > 0) {
				BTreeNode<E> n = q.remove();
				if (!n.toString().equals("[]")) {
					s = s + n.data.toString();
				}
					
				if (!n.isLeaf()) {
					for (int i = 0; i < n.children.size(); i++) {
						q.add(n.getChild(i));
						count++;
					}
				}
				count--;
			}

			return s;
		}
	}

	/**
	 * Returns degree of tree
	 * 
	 * @return degree of tree
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Returns the pointer to the root of this true
	 * 
	 * @return pointer to the root of tree
	 */
	public BTreeNode<E> getRoot() {
		return root;
	}

	/********************/

	/**
	 * Returns true if target is found in the tree
	 * 
	 * @param target data
	 * @return true if target found, false otherwise.
	 */
	public boolean contains(E target) { 
		BTreeNode<E> ptr = root;

		while (ptr != null) {
			int i = 0;
			for (E d : ptr.data) {
				if (d == target) {
					return true;
				}

				if (comp.compare(target, d) < 0) {
					ptr = ptr.getChild(i);
				} else if (i == ptr.dataSize() - 1) {
					ptr = ptr.getChild(i + 1);
				} else {
					i++;
				}
			}
		}

		return false;
	}

	/**
	 * Removes target in tree
	 * 
	 * @param target data to be removed
	 * @return true if removed, false if does not exist
	 */
	public boolean remove(E target) {
		BTreeNode<E> node = getNode(target);
		
		if (node == null) {
			return false;
		}
		
		if (node.isLeaf()) {
			BTreeNode<E> nn = node.remove(target);

			if (nn != null && nn.parent == null) {
				root = nn;
			}
			
			return true;
		} else {
			BTreeNode<E> ptr = node.getChild(0);
			BTreeNode<E> trail = node.getChild(0);
			
			while (ptr != null) {
				trail = ptr;
				ptr = ptr.getChild(ptr.children.size() - 1);
			}
			
			E succ = trail.getData(trail.dataSize() - 1);
			
			
			node.data.set(node.indexOf(target), succ);
			
			BTreeNode<E> nn = trail.remove(succ);
			
			if (nn != null && nn.parent == null) {
				root = nn;
			}
		}
		
		return false;
	}
}