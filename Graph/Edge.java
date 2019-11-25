/**
 * Edge.java November 21, 2019
 * 
 * @author wilcda01 Edge implementation for graph
 */
public class Edge {
	Vertex src;
	Vertex dst;
	double cost;

	/**
	 * Constructor
	 * 
	 * @param vsrc
	 * @param vdst
	 * @param w
	 */
	public Edge(Vertex vsrc, Vertex vdst, double w) {
		src = vsrc;
		dst = vdst;
		cost = w;
	}

	/**
	 * Constructor
	 * 
	 * @param vsrc
	 * @param vdst
	 */
	public Edge(Vertex vsrc, Vertex vdst) {
		src = vsrc;
		dst = vdst;
		cost = 1;
	}

	/**
	 * Overrides the equals method in Object class and returns true
	 * 
	 * @return if edge equals other edge, return true
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Edge && ((Edge) obj).src == this.src && ((Edge) obj).dst == this.dst;
	}

	/**
	 * Returns string representation of edge
	 */
	public String toString() {
		return src.label + "-" + dst.label + " (" + cost + ")";
	}

}
