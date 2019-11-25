import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * GraphUtil.java November 21, 2019
 * 
 * @author wilcda01 Graph utility methods for searching algorithms
 */
public class GraphUtil {
	/**
	 * Lists the vertices (labels) visited by the Breadth-First-Search traversal
	 * algorithm on the graph g from the src vertex
	 * 
	 * @param g   graph
	 * @param src source vertex
	 */
	public static void bfs(Graph g, String src) {
		String s = "bfs(" + src + "): ";
		Queue<Vertex> q = new LinkedList<>();
		Vertex v = g.getVertex(src);
		q.add(v);

		while (!q.isEmpty()) {
			v = q.remove();
			s += v.label + "-";
			v.mark();

			for (Edge e : v.adj.values()) {
				Vertex newV = e.dst;
				if (!newV.isMarked()) {
					q.add(newV);
					newV.mark();
				}
			}
		}

		System.out.println(s);
	}

	/**
	 * Lists the vertices (labels) visited by the Depth-First-Search traversal
	 * algorithm on the graph g from the src vertex.
	 * 
	 * @param g   graph
	 * @param src source vertex
	 */
	public static void dfs(Graph g, String src) {
		Vertex v = g.getVertex(src);
		System.out.print(v.label + "-");
		v.mark();

		for (Edge e : v.adj.values()) {
			Vertex newV = e.dst;
			if (!newV.isMarked()) {
				dfs(g, newV.label);
			}
		}
	}

	/**
	 * Applies the Dijkstra's shortest path algorithm to the graph g from the src
	 * vertex and prints
	 * 
	 * @param g   graph
	 * @param src source vertex
	 */
	public static void dijkstra(Graph g, String src) {

		Vertex v = g.getVertex(src);
		Vertex n = v;
		v.cost = 0;

		ArrayList<Vertex> pq = new ArrayList<>();

		for (Vertex newV : g.getVerts()) {
			pq.add(newV);
		}

		while (!pq.isEmpty()) {
			v = removeMin(pq);
			v.mark();
			for (Edge e : v.adj.values()) {
				Vertex neighbor = e.dst;
				if (!neighbor.isMarked() && (neighbor.cost > v.cost + e.cost)) {
					neighbor.cost = v.cost + e.cost;
					neighbor.pred = v;
				}
			}
		}

		for (Vertex a : g.getVerts()) {
			if (!a.label.equals(src)) {
				System.out.print(src + " --> " + a.label + ": ");
				printPath(g, n.label, a.label);
			}
		}
	}

	/**
	 * Removes and returns the Vertex with the lowest cost.
	 * 
	 * @param pq priority queue
	 * @return vertex with lowest cost
	 */
	public static Vertex removeMin(ArrayList<Vertex> pq) {
		Vertex min = pq.get(0);

		for (Vertex v : pq) {
			if (v.cost < min.cost) {
				min = v;
			}
		}
		pq.remove(min);
		return min;
	}

	/**
	 * Prints the path from src to dst assuming a shortest path algorithm had been
	 * applied and vertices have proper information (cost and predecessor of the
	 * path, if exists).
	 * 
	 * @param g   graph
	 * @param src source
	 * @param dst distance
	 */
	public static void printPath(Graph g, String src, String dst) {
		Vertex vdst = g.getVertex(dst);
		double cost = vdst.cost;
		String s = "";
		while (vdst != null && vdst.label != src) {
			s = "-" + vdst.label + s; // I hate this stupid line. I spent 30 minutes checking the algorithm only to
										// realize I was just printing backwards.
			vdst = vdst.pred;

		}

		if (cost == Double.POSITIVE_INFINITY) {
			s = "(no path)";
			System.out.println(s);
		} 
		else {
			s = src + s;
			s = "(" + cost + ") " + s + "-(done)";
			System.out.println(s);
		}

	}
}
