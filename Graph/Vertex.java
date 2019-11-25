import java.util.Map;
import java.util.TreeMap;

/**
 * Vertex.java November 21, 2019
 * 
 * @author wilcda01 Vertex implementation for graph
 */
public class Vertex {
	int index;
	private static int nVert = 0;
	String label;
	Vertex pred;
	double cost;
	boolean visited;
	final double INFINITY = Double.POSITIVE_INFINITY;
	TreeMap<String, Edge> adj;

	public Vertex(String label) {
		index = nVert;
		nVert++;
		StringComp comp = new StringComp();
		this.label = label;
		cost = INFINITY;
		visited = false;
		pred = null;
		adj = new TreeMap<>(comp);
	}

	/**
	 * Resets this Vertex for graph algorithms (visited, cost, pred)
	 */
	public void reset() {
		visited = false;
		cost = INFINITY;
		pred = null;
	}

	/**
	 * Marks this Vertex as processed (visited; no need to check)
	 */
	public void mark() {
		visited = true;
	}

	/**
	 * Marks this Vertex as _not_ processed; no need to check
	 */
	public void unmark() {
		visited = false;
	}

	/**
	 * Adds c to the current cost of this Vertex ('s path)
	 * 
	 * @param c cost to be added
	 */
	public void addCost(double c) {
		cost += c;
	}

	/**
	 * Returns whether this Vertex is processed (marked, or visited) or not.
	 * 
	 * @return true if marked, false otherwise
	 */
	public boolean isMarked() {
		return visited;
	}

	/**
	 * Returns the number of neighbors this vertex has (size of adjacency list)
	 * 
	 * @return number of neighbors vertex has
	 */
	public int adjSize() {
		return adj.size();
	}

	/**
	 * Returns the edge connecting this vertex to the Vertex labeled dst, or returns
	 * null if no such edge exists.
	 * 
	 * @param dst other vertex
	 * @return edge connecting vert
	 */
	public Edge getAdj(String dst) {
		return adj.get(dst);
	}

	/**
	 * Returns string representation of vertex by neighbors and edge cost
	 */
	public String toString() {
		String s = label + " (" + index + ", " + adj.size() + "): ";
		for (Map.Entry<String, Edge> e : adj.entrySet()) {
			String label = e.getKey();
			Edge edge = e.getValue();

			s += label + " (" + edge.cost + ") ";
		}

		return s;
	}

	/**
	 * Creates and adds a new Edge with vdst and w to this Vertex's neighbor list.
	 * 
	 * @param vdst distance
	 * @param w    cost
	 */
	public void addAdj(Vertex vdst, double w) {
		adj.put(vdst.label, new Edge(this, vdst, w));
	}

	/**
	 * Adds the given Edge to this Vertex's neighbor list.
	 * 
	 * @param e edge
	 */
	public void addAdj(Edge e) {
		adj.put(e.dst.label, e);
	}
}
