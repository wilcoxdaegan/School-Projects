import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author wilcda01
 *
 * represents a node in a generic binary tree
 *
 * @param <E>
 */

public class BTNode<E> {
	// package level access modifier: (none) or default
	E data;
	BTNode<E> left, right;
	
	public BTNode() {
		
	}

	public BTNode(E d, BTNode<E> l, BTNode<E> r) {
		data = d;
		left = l;
		right = r;
	}
	
	public String toString() {
		return (data == null) ? "" : data.toString();  
	}
	
	// static utility methods
	// getHeight, countNodes, traversal methods
	
	/**
	 * Count of all nodes
	 * @param <E>
	 * @param node
	 * @return amount of all nodes
	 */
	public static <E> int countNodes(BTNode<E> node) {
		if (node == null) return 0;
		
		return countNodes(node.left) + countNodes(node.right) + 1; 
	}
	
	/**
	 * Gives height of tree
	 * @param <E>
	 * @param node 
	 * @return tree height
	 */
	public static <E> int getHeight(BTNode<E> node) {
		if (node == null) return 0;
		
		int hL = getHeight(node.left);
		int hR = getHeight(node.right);
		
		return (1 + Math.max(hL, hR));
	}
	
	// tree traversal - returns a print friendly string representation of the tree visited in preorder
	public static <E> String preorder(BTNode<E> node) {
		if (node == null) return "";
		
		return node + " " + preorder(node.left) + " " +  preorder(node.left);
	}
	
	/**
	 * Orders by inorder
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> String inorder(BTNode<E> node) {
		if (node == null) return "";
		
		return inorder(node.left) + " " + node + " " + inorder(node.right);
	}
	
	/**
	 * Orders by post order
	 * @param <E>
	 * @param node
	 * @return
	 */
	public static <E> String postorder(BTNode<E> node) {
		if (node == null) return "";
		
		return postorder(node.left) + " " + postorder(node.right) + " " + node;
	}
	
	/**
	 * Puts the node in level order
	 * @param <E>
	 * @param node node to be ordered
	 * @return return string
	 */
	public static <E> String levelorder(BTNode<E> node) {
		if (node == null) return "";
		
		String str = "";
		Queue<BTNode<E>> q = new LinkedList<>();
		q.add(node);
		
		while(!q.isEmpty()) {
			BTNode<E> curr = q.remove();
			str += curr + "";
			
			if (curr.hasLeft()) {
				q.add(curr.left);
			}
			
			if (curr.hasRight()) {
				q.add(curr.right);
			}
			
		}
		return null;
		
	}
	
	/**
	 * Checks if node has a right child
	 * @return true if right child, false otherwise
	 */
	public boolean hasRight() {
		return (right != null); 
	}
	
	/**
	 * Checks if node has a left child
	 * @return true if left child, false otherwise
	 */
	public boolean hasLeft() {
		return (left != null);
	}
	
	/**
	 * Checks if the node is a leaf, aka no children
	 * @return true if no children, false otherwise
	 */
	public boolean isLeaf() {
		return (right == null && left == null);
	}
	
}
