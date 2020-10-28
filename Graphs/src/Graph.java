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
	
	public int getSize()
	{
		return this.size;
	}
	
	// This method returns a set containing the vertices in the graph.
	public Set<T> getVertices()
	{
		return this.list.keySet();
	}
	
	public boolean containsVertex(T name)
	{
		if (this.list.containsKey(name))
			return true;
		
		return false;
	}
	
	// This method adds a new vertex to the Graph, as long as there is not already a vertex
	// with the given name.
	public void addVertex(T name)
	{
		if (this.containsVertex(name))
			return;
		
		this.list.put(name, new HashMap<T, Integer>());
		this.size++;
	}
	
	public void removeVertex(T name)
	{
		Set<Entry<T, HashMap<T, Integer>>> vertices = this.list.entrySet();
		for (Entry vertex : vertices)
		{
			((HashMap<T, Integer>) vertex.getValue()).remove(name);
		}
		
		this.list.remove(name);
	}
	
	public HashMap<T, Integer> getEdges(T name)
	{
		if (!this.containsVertex(name))
			return null;
		
		return this.list.get(name);
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
	
	public ArrayList<T> depthFirstTraversal(T start)
	{
		HashSet<T> visited = new HashSet<T>();
		ArrayList<T> traversal = new ArrayList<T>();
		Stack<T> s = new Stack<T>();
		
		visited.add(start);
		s.push(start);
		
		while(!s.isEmpty())
		{
			T vertex = s.pop();
			traversal.add(vertex);
			
			HashMap<T, Integer> edgeMap = this.getEdges(vertex);
			for (Entry<T, Integer> edge : edgeMap.entrySet())
			{
				if (!visited.contains(edge.getKey()))
				{
					visited.add(edge.getKey());
					s.push(edge.getKey());
				}
			}
		}
		
		return traversal;
	}
	
	public ArrayList<T> breadthFirstTraversal(T start)
	{
		HashSet<T> visited = new HashSet<T>();
		ArrayList<T> traversal = new ArrayList<T>();
		Queue<T> q = new LinkedList<T>();
		
		visited.add(start);
		q.add(start);
		
		while(!q.isEmpty())
		{
			T vertex = q.remove();
			traversal.add(vertex);
			
			HashMap<T, Integer> edgeMap = this.getEdges(vertex);
			for (Entry<T, Integer> edge : edgeMap.entrySet())
			{
				if (!visited.contains(edge.getKey()))
				{
					visited.add(edge.getKey());
					q.add(edge.getKey());
				}
			}
		}
		
		return traversal;
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
		/*Set<Entry<T, HashMap<T, Integer>>> vertices = this.list.entrySet();
		for (Entry vertex : vertices)
		{
			System.out.println(vertex.getKey());
			Set<Entry<T, Integer>> edges = ((HashMap<T, Integer>) vertex.getValue()).entrySet();
			for (Entry edge : edges)
			{
				System.out.println("   -> (" + edge.getKey() + ", " + edge.getValue() + ")");
			}
		}*/
		
		ArrayList<T> vertices = new ArrayList<T>(this.getVertices());
		for (T vertex : vertices)
		{
			System.out.println(vertex);
			HashMap<T, Integer> edgeMap = this.getEdges(vertex);
			for (Entry<T, Integer> edge : edgeMap.entrySet())
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
		ArrayList<T> vertices = new ArrayList<T>(this.getVertices());
		Collections.sort(vertices);
		for (T vertex : vertices)
		{
			System.out.println(vertex);
			HashMap<T, Integer> edgeMap = this.getEdges(vertex);
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
		Graph<Integer> g = Graph.randomUndirectedGraph(10, 0.3, 1, 4);
		
		g.print();
		
		ArrayList<Integer> traversal = g.depthFirstTraversal(0);
		
		for (int i = 0; i < traversal.size(); i++)
			System.out.print(traversal.get(i) + " ");
	}
}
