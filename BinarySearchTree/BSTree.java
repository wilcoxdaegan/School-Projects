import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * BSTree.java Friday October 18, 2019
 * 
 * @author wilcda01 An implementation of a Binary Search Tree using a comparator
 *         and BTNode
 *
 * @param <E>
 */
public class BSTree<E> {
	private BTNode<E> root;
	private Comparator<E> cmp;
	private int count;

	public BSTree(Comparator<E> c) {
		cmp = c;
	}

	/**
	 * Add method that inserts data according to the binary search algorithm.
	 * 
	 * @param data information to be inserted
	 */
	public void add(E data) {
		BTNode<E> nn = new BTNode<>(data, null, null);
		if (root == null) {
			root = nn;
			count++;
			return;
		}

		BTNode<E> ptr = root;
		BTNode<E> trail = root;
		boolean isRight = false;

		while (ptr != null) {
			int c = cmp.compare(data, ptr.data);

			if (c > 0) {
				trail = ptr;
				ptr = ptr.right;
				isRight = true;
			} else if (c <= 0) {
				trail = ptr;
				ptr = ptr.left;
				isRight = false;
			}
		}

		if (isRight) {
			trail.right = nn;
			count++;
		} else {
			trail.left = nn;
			count++;
		}
	}

	/**
	 * Method that checks whether the tree has at least one instance of the target
	 * data. Stops on said first instance
	 * 
	 * @param target data to be found
	 * @return whether it was found or not
	 */
	public boolean contains(E target) {
		BTNode<E> ptr = root;
		while (ptr != null) {
			int c = cmp.compare(target, ptr.data);

			if (c >= 1) {
				ptr = ptr.right;
			} else if (c < 0) {
				ptr = ptr.left;
			} else {
				return true;
			}
		}

		return false;
	}

	/**
	 * Removes data from tree if found, and properly re-aligns the tree when parents
	 * are deleted.
	 * 
	 * @param target data to be deleted
	 * @return target if found, null otherwise
	 */
	@SuppressWarnings("unchecked")
	public E remove(E target) {
		if (root == null) {
			return null;
		}

		if (count == 1 && cmp.compare(target, root.data) == 0) {
			root = null;
			count--;
			return target;
		} else if (count == 1) {
			return null;
		}

		BTNode<E> ptr = root;
		BTNode<E> trail = root;
		boolean isRight = false;

		while (ptr != null) {
			int c = cmp.compare(target, ptr.data);

			if (c > 0) {
				trail = ptr;
				ptr = ptr.right;
				isRight = true;
			} else if (c < 0) {
				trail = ptr;
				ptr = ptr.left;
				isRight = false;
			} else if (c == 0) {
				break;
			}
		}

		if (ptr == null) {
			return null;
		}

		if (ptr == root) {
			if (!ptr.hasLeft() && !ptr.hasRight()) {
				root = null;
				count--;
				return target;
			} else if (ptr.hasLeft()) {
				BTNode<E> largest = removeLargestFromLeft(ptr);
				ptr.data = largest.data;
				count--;
				return target;
			} else if (ptr.hasRight()) {
				root = root.right;
				count--;
				return target;
			}
		}

		if (!ptr.hasLeft() && !ptr.hasRight()) {
			if (isRight) {
				trail.right = null;
				count--;
				return target;
			} else {
				trail.left = null;
				count--;
				return target;
			}
		} else if (ptr.hasLeft()) {
			BTNode<E> largest = removeLargestFromLeft(ptr);
			ptr.data = largest.data;
			count--;
			return target;
		} else if (ptr.hasRight()) {
			if (isRight) {
				trail.right = ptr.right;
				count--;
				return target;
			} else {
				trail.left = ptr.right;
				count--;
				return target;
			}
		}

		return null;
	}

	/**
	 * Gives root node
	 * 
	 * @return root node
	 */
	public BTNode<E> getRoot() {
		return root;
	}

	/**
	 * Amount of items in the list
	 * 
	 * @return count items in list
	 */
	public int size() {
		return count;
	}

	/**
	 * Checks if the tree is empty or not
	 * 
	 * @return if the tree is empty or not
	 */
	public boolean empty() {
		return count == 0;
	}

	/**
	 * Clears the tree of all values and resets the count
	 */
	public void clear() {
		root = null;
		count = 0;
	}

	/**
	 * Returns a string representation of the tree
	 */
	public String toString() {
		String s = "";

		Queue<BTNode<E>> q = new LinkedList<BTNode<E>>();
		q.add(root);

		if (root == null) {
			return "[]";
		} else {
			int count = q.size();

			if (count == 0) {
				return s;
			}

			while (count > 0) {
				BTNode<E> n = q.peek();
				s = s + "[" + n.data.toString() + "]";
				q.remove();

				if (n.hasLeft()) {
					q.add(n.left);
					count++;
				}

				if (n.hasRight()) {
					q.add(n.right);
					count++;
				}
				count--;
			}

			return s;
		}
	}

	/**
	 * If a node has both a left and a right node. Not used
	 * 
	 * @param node node given
	 * @return if it has both children or not
	 */
	public boolean hasBoth(BTNode<E> node) {
		return node.hasLeft() && node.hasRight();
	}

	/**
	 * Finds the largest from the left of a node, and removes it, then returns it
	 * 
	 * @param node the node to be checked, usually the ptr
	 * @return the largest node found in the left of the original node
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static BTNode removeLargestFromLeft(BTNode node) {
		BTNode ptr = node;
		BTNode largest = ptr.left;
		BTNode trailL = ptr;
		boolean isRight = false;

		while (largest.hasRight()) {
			trailL = largest;
			largest = largest.right;
			isRight = true;
		}

		if (isRight) {
			if (largest.hasLeft()) {
				trailL.right = largest.left;
				return largest;
			}

			trailL.right = null;
			return largest;
		} else {
			if (largest.hasLeft()) {
				trailL.left = largest.left;
				return largest;
			}

			trailL.left = null;
			return largest;
		}

	}
}
