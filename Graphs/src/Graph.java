// Graph.java
// Connor Shrader

// Implementation of a graph using adjacency matrices.
// This class was made based on information in my Computer Science 2 class.

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class Graph<T extends Comparable<T>>
{
	private HashMap<T, HashMap<T, Integer>> list;
	
	private int size;
	
	public Graph()
	{
		 this.list = new HashMap<T, HashMap<T, Integer>>();
		 this.size = 0;
	}
	
	public Graph(String filename)
	{
		// TODO: File read.
		this();
	}

	public HashMap<T, HashMap<T, Integer>> getList()
	{
		return this.list;
	}
	
	// This method returns a set containing the vertices in the graph.
	public Set<T> getVertices()
	{
		return this.list.keySet();
	}
	
	// This method adds a new vertex to the Graph, as long as there is not already a vertex
	// with the given name.
	public void addVertex(T name)
	{
		if (this.list.containsKey(name))
			return;
		
		this.list.put(name, new HashMap<T, Integer>());
	}
	
	// This method creates an edge from vertex start to vertex end with the given weight
	// (unless an edge already exists between them). If directed is false, then an edge
	// is also added from end to start (if there is not already an edge).
	public void addUndirectedEdge(T start, T end, Integer weight)
	{
		this.addDirectedEdge(start, end, weight);
		this.addDirectedEdge(end, start, weight);
	}
	
	public void addDirectedEdge(T start, T end, Integer weight)
	{
		HashMap<T, Integer> startList = this.list.get(start);
		if (startList.containsKey(end))
			return;
		
		startList.put(end, weight);
	}
	
	public static Integer generateRandomInteger(int min, int max)
	{
		return min + (int)(Math.random() * (max - min + 1));
	}
	
	// This method generates a random undirected graph with a given number of vertices. The graph
	// uses the Integer type. The parameter edgeDensity dictates the probability that
	// any possible edge will be included in the graph. The weight of each edge
	// is chosen uniformly between minWeight and maxWeight, inclusive.
	public static Graph<Integer> randomUndirectedGraph(int vertices, double edgeDensity, int minWeight, int maxWeight)
	{
		Graph<Integer> g = new Graph<Integer>();
		
		for (int i = 0; i < vertices; i++)
			g.addVertex(i);
		
		for (int i = 0; i < vertices; i++)
			for (int j = i + 1; j < vertices; j++)
				if (edgeDensity > Math.random())
					g.addUndirectedEdge(i, j, generateRandomInteger(minWeight, maxWeight));
		
		return g;
	}
	
	// This method generates a random directed graph with a given number of vertices. The graph
	// uses the Integer type. The parameter edgeDensity dictates the probability that
	// any possible edge will be included in the graph. The weight of each edge
	// is chosen uniformly between minWeight and maxWeight, inclusive.
	public static Graph<Integer> randomDirectedGraph(int vertices, double edgeDensity, int minWeight, int maxWeight)
	{
		Graph<Integer> g = new Graph<Integer>();
		
		for (int i = 0; i < vertices; i++)
			g.addVertex(i);
		
		for (int i = 0; i < vertices; i++)
			for (int j = 0; j < vertices; j++)
				if (edgeDensity > Math.random())
					g.addDirectedEdge(i, j, generateRandomInteger(minWeight, maxWeight));
		
		return g;
	}
	
	public void save(String filename)
	{
		// TODO: Save graph to a file
		return;
	}
	
	// This method prints the vertices in the graph. For each vertex, this method also prints
	// the vertices adjacent to it as well as the edge weight.
	
	// The runtime for this method is O(|V|+|E|)
	public void print()
	{
		Set<Entry<T, HashMap<T, Integer>>> vertices = this.list.entrySet();
		for (Entry vertex : vertices)
		{
			System.out.println(vertex.getKey());
			Set<Entry<T, Integer>> edges = ((HashMap<T, Integer>) vertex.getValue()).entrySet();
			for (Entry edge : edges)
			{
				System.out.println("   -> (" + edge.getKey() + ", " + edge.getValue() + ")");
			}
		}
	}
	
	// This method is the same as print(), but the vertices and edges are sorted
	// before printing.
	
	// The runtime for this method is O(|V|log(|V|) + |E|log(|E|))
	public void printSorted()
	{
		ArrayList<T> vertices = new ArrayList<T>(this.list.keySet());
		Collections.sort(vertices);
		for (T vertex : vertices)
		{
			System.out.println(vertex);
			HashMap<T, Integer> edgeMap = this.list.get(vertex);
			ArrayList<T> edges = new ArrayList<T>(edgeMap.keySet());
			Collections.sort(edges);
			for (T edge : edges)
			{
				System.out.println("   -> (" + edge + ", " + edgeMap.get(edge) + ")");
			}
		}
	}
	
	public static void main(String [] args)
	{
		Graph<Integer> g = Graph.randomUndirectedGraph(5, 0.5, 1, 4);
		
		g.print();
	}
}
