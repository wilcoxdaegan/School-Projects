import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Graph.java November 21, 2019
 * 
 * @author wilcda01 Graph data structure using vertex and edge classes
 */
public class Graph {
	ArrayList<Vertex> verts = new ArrayList<>();
	double[][] adjmat;
	HashMap<String, Vertex> mapLabel = new HashMap<>();
	ArrayList<Edge> edges = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param vfile
	 * @param efile
	 * @throws FileNotFoundException
	 */
	public Graph(String vfile, String efile) throws FileNotFoundException {
		Scanner vFile = new Scanner(new File(vfile));
		Scanner eFile = new Scanner(new File(efile));

		while (vFile.hasNext()) {
			Vertex v = new Vertex(vFile.next());
			verts.add(v);
			mapLabel.put(v.label, v);
		}

		ArrayList<Double> list = new ArrayList<Double>();
		while (eFile.hasNextDouble()) {
			list.add(eFile.nextDouble());
		}

		int size = (int) Math.pow(list.size(), .5);
		adjmat = new double[(int) size][(int) size];

		for (int i = 0; i < size * size; i++) {
			adjmat[i / size][i % size] = list.get(i);
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				double cost = adjmat[i][j];
				if (cost != 0) {
					Vertex src = verts.get(i);
					Edge e = new Edge(src, verts.get(j), cost);
					edges.add(e);
					src.addAdj(e);
				}
			}
		}

		vFile.close();
		eFile.close();
	}

	/**
	 * Resets predecessor, cost, and visited fields of all vertices in this graph.
	 */
	public void resetVerts() {
		for (Vertex v : verts) {
			v.reset();
		}
	}

	/**
	 * returns the list of vertices
	 * 
	 * @return list of vertices
	 */
	public ArrayList<Vertex> getVerts() {
		return verts;
	}

	/**
	 * returns the number of vertices in this graph
	 * 
	 * @return number of vertices in graph
	 */
	public int nVerts() {
		return verts.size();
	}

	/**
	 * Returns the vertex associated with label
	 * 
	 * @param label vertex label to be found
	 * @return vertex with label, null if does not exist
	 */
	public Vertex getVertex(String label) {
		return mapLabel.get(label);
	}

	/**
	 * Returns the index-th vertex (0-based)
	 * 
	 * @param index index of vertex
	 * @return vertex, null if does not exist
	 */
	public Vertex getVertex(int index) {
		return verts.get(index);
	}

	/**
	 * Returns the edge from vsrc to vdst.
	 * 
	 * @param vsrc source
	 * @param vdst distance
	 * @return edge from vsrc to vdst, null if does not exist
	 */
	public Edge getEdge(Vertex vsrc, Vertex vdst) {
		return mapLabel.get(vsrc.label).getAdj(vdst.label);
	}

	/**
	 * Returns the edge from src to dst
	 * 
	 * @param src source
	 * @param dst distance
	 * @return edge from src to dst, null if does not exist
	 */
	public Edge getEdge(String src, String dst) {
		return mapLabel.get(src).getAdj(dst);
	}

	/**
	 * Returns the adjacency matrix of this graph
	 * 
	 * @return adjacency matrix built in constructor
	 */
	public double[][] adjMatrix() {
		return adjmat;
	}

	/**
	 * Returns string of graph
	 */
	public String toString() {
		String s = "";

		for (Vertex v : verts) {
			s += v + "\n";
		}

		return s;
	}

}
