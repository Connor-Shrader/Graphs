// Graph.java
// Connor Shrader

// Implementation of a graph using adjacency map.
// This class was made based on information in my Computer Science 2 class.

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

class Vertex<T> implements Comparable
{
	T key;
	Integer value;
	
	Vertex(T key, Integer value)
	{
		this.key = key;
		this.value = value;
	}

	@Override
	public int compareTo(Object v)
	{
		try {
			if (v instanceof Vertex<?>)
			{
				if (this.value == null)
				{
					return 1;
				}
				else if (((Vertex<T>) v).value == null)
				{
					return -1;
				}
				return this.value - ((Vertex<T>) v).value;
			}
			return 1;
		}
		catch (Exception e)
		{
			return 1;
		}
	}
}

public class Graph<T extends Comparable<T>>
{
	// This HashMap associates names for each vertex with another HashMap.
	// Each smaller HashMap contains the vertices that the original vertex is
	// adjacent to, as well as an Integer representing the weight of that edge.
	private HashMap<T, HashMap<T, Integer>> map;
	
	// The size of a Graph is the number of vertices.
	private int size;
	
	// Default constructor that initializes map but does not add any
	// vertices or edges.
	public Graph()
	{
		 this.map = new HashMap<T, HashMap<T, Integer>>();
		 this.size = 0;
	}
	
	// This constructor reads a graph from a text file.
	public Graph(String filename)
	{
		// TODO: File read.
		this();
	}

	// This method returns the map variable.
	public HashMap<T, HashMap<T, Integer>> getMap()
	{
		return this.map;
	}
	
	// This method returns the number of vertices in the graph.
	public int getSize()
	{
		return this.size;
	}
	
	// This method returns a set containing the vertices in the graph.
	public ArrayList<T> getVertices()
	{
		return new ArrayList<T>(this.map.keySet());
	}
	
	// This method takes 'vertex' as a parameter and returns a HashMap
	// mapping all the vertices that 'vertex' is adjacent to the edge's weight.
	// If the 'vertex' is not in the graph, the method returns null.
	public HashMap<T, Integer> getEdgeMap(T vertex)
	{
		if (!this.containsVertex(vertex))
			return null;
		
		return this.map.get(vertex);
	}
	
	// This method takes 'vertex' as a parameter and returns an ArrayList
	// containing all the vertices that 'vertex' is adjacent to.
	// If the 'vertex' is not in the graph, the method returns null.
	public ArrayList<T> getEdgeList(T vertex)
	{
		if (!this.containsVertex(vertex))
			return null;
		
		return new ArrayList<T>(this.getEdgeMap(vertex).keySet());
	}
	
	// This method returns true if the graph contains
	// 'vertex', and false otherwise.
	public boolean containsVertex(T vertex)
	{
		if (this.map.containsKey(vertex))
			return true;
		
		return false;
	}
	
	// This method returns true if there is an edge from 'start' to 'end
	// in the graph, and false if there is not an edge. If either 'start'
	// or 'end' is not in the graph, this method returns false.
	public boolean containsEdge(T start, T end)
	{
		if (!this.containsVertex(start) || !this.containsVertex(end))
			return false;
		
		return this.getEdgeList(start).contains(end);
	}
	
	// This method adds a new vertex to the Graph, as long as there is not already a vertex
	// with the given name.
	public void addVertex(T vertex)
	{
		if (this.containsVertex(vertex))
			return;
		
		this.map.put(vertex, new HashMap<T, Integer>());
		this.size++;
	}
	
	// This method removes 'vertex' from the graph, as well as all
	// edges associated with 'vertex'.
	public void removeVertex(T vertex)
	{
		ArrayList<T> vertices = this.getVertices();
		
		if (!this.containsVertex(vertex))
			return;
		
		for (T start : vertices)
		{
			this.removeUndirectedEdge(start, vertex);
		}
		
		this.removeVertex(vertex);
	}
	
	// This method creates an edge with a given weight from
	// 'start' to 'end', as well as an edge from end to start.
	public void addUndirectedEdge(T start, T end, Integer weight)
	{
		this.addDirectedEdge(start, end, weight);
		this.addDirectedEdge(end, start, weight);
	}
	
	// This method creates an edge from 'start' to 'end with the given weight.
	public void addDirectedEdge(T start, T end, Integer weight)
	{
		HashMap<T, Integer> startMap = this.map.get(start);
		if (startMap.containsKey(end))
			return;
		
		startMap.put(end, weight);
	}
	
	// This method removes any edges between 'start' and 'end'.
	// Note that this method will remove both edges between the vertices
	// regardless of whether those edges were made using addUndirectedEdges
	// or addDirectedEdges. It will also remove both edges even if the weights
	// differ.
	public void removeUndirectedEdge(T start, T end)
	{
		this.removeDirectedEdge(start, end);
		this.removeDirectedEdge(end, start);
	}
	
	// This method removes an edge between 'start' and 'end', if one exists.
	// If there is also an edge from 'end' to 'start', it is not altered or deleted.
	public void removeDirectedEdge(T start, T end)
	{
		if (!this.containsVertex(start) || !this.containsVertex(end))
			return;
		
		this.getEdgeMap(start).remove(end);
	}
	
	// This method inputs a vertex 'start' and performs an iterative depth-first traversal
	// of the graph. This method returns an ArrayList<T> containing every vertex
	// reached by the traversal in the order that they were visited.
	// This method will traverse the connected component that 'start' is in, but
	// it will not traverse the entire graph if the graph is disconnected.
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
			
			ArrayList<T> edges = this.getEdgeList(vertex);
			for (T edge : edges)
			{
				if (!visited.contains(edge))
				{
					visited.add(edge);
					s.push(edge);
				}
			}
		}
		
		return traversal;
	}
	
	// This method inputs a vertex 'start' and performs an iterative breadth-first traversal
	// of the graph. This method returns an ArrayList<T> containing every vertex
	// reached by the traversal in the order that they were visited.
	// This method will traverse the connected component that 'start' is in, but
	// it will not traverse the entire graph if the graph is disconnected.
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
			
			ArrayList<T> edges = this.getEdgeList(vertex);
			for (T edge : edges)
			{
				if (!visited.contains(edge))
				{
					visited.add(edge);
					q.add(edge);
				}
			}
		}
		
		return traversal;
	}
	
	// This method counts the number of connected components in the graph.
	public int countConnectedComponents()
	{
		HashSet<T> visited = new HashSet<T>();
		ArrayList<T> traversal = new ArrayList<T>();
		ArrayList<T> vertices = this.getVertices();
		int count = 0;
		
		for (T vertex : vertices)
		{
			if (visited.contains(vertex))
				continue;
			
			count++;
			traversal = this.depthFirstTraversal(vertex);
			for (int i = 0; i < traversal.size(); i++)
				visited.add(traversal.get(i));
		}
		
		return count;
	}
	
	// This method returns an ArrayList representing a valid topological sort
	// of the graph. The method returns null if there is no valid topological
	// sort.
	public ArrayList<T> topologicalSort()
	{
		ArrayList<T> vertices = this.getVertices();
		ArrayList<T> traversal = new ArrayList<T>();
		HashMap<T, Integer> incomingEdges = new HashMap<T, Integer>();
		Queue<T> q = new LinkedList<T>();
		int count = 0;
		
		// Initialize all vertices to have zero incoming edges.
		for (T vertex : vertices)
		{
			incomingEdges.put(vertex, 0);
		}
		
		// Calculate the number of incoming edges for all vertices.
		for (T vertex : vertices)
		{
			ArrayList<T> edges = this.getEdgeList(vertex);
			for (T edge : edges)
			{
				incomingEdges.put(edge, incomingEdges.get(edge) + 1);
			}
		}
		
		// Put all vertices with 0 incoming edges into the queue.
		for (T vertex : vertices)
		{
			if (incomingEdges.get(vertex) == 0)
			{
				q.add(vertex);
			}
		}
		
		// Compute the topological sort.
		while(!q.isEmpty())
		{
			T vertex = q.remove();
			traversal.add(vertex);
			count++;
			
			ArrayList<T> edges = this.getEdgeList(vertex);
			for (T edge : edges)
			{
				if (incomingEdges.put(edge, incomingEdges.get(edge) - 1) - 1 == 0)
				{
					q.add(edge);
				}
			}
		}
		
		// If not all vertices were traversed, there is a cycle and
		// no valid topological sort exists.
		if (count != this.getSize())
		{
			return null;
		}
		
		return traversal;
	}
	
	public HashMap<T, T> runDijkstra(T start)
	{
		int oo = (int)1e9;
		ArrayList<T> vertices = this.getVertices();
		HashMap<T, Integer> dist = new HashMap<T, Integer>();
		HashMap<T, T> prevVertex = new HashMap<T, T>();
		HashSet<T> visited = new HashSet<T>();
		PriorityQueue<Vertex<T>> q = new PriorityQueue<Vertex<T>>();
		
		for (T vertex : vertices)
		{
			dist.put(vertex, oo);
			q.add(new Vertex<T>(vertex, oo));
		}
		
		q.add(new Vertex<T>(start, 0));
		
		while (!q.isEmpty())
		{
			Vertex<T> vertex = q.remove();
			System.out.println ("Just pulled " + vertex.key + " from the queue.");
			if (visited.contains(vertex.key))
				continue;
			
			System.out.println("It is not already visited.");
			
			visited.add(vertex.key);
			
			HashMap<T, Integer> edgeMap = this.getEdgeMap(vertex.key);
			for (Entry<T, Integer> edge : edgeMap.entrySet())
			{
				System.out.println("\tLooking at the edge to " + edge.getKey());
				System.out.println("\tEdge weight = " + edge.getValue() + ", Vertex value = " + vertex.value);
				int pathWeight = edge.getValue() + vertex.value;
				if (!visited.contains(edge.getKey()))
				{
					if (dist.get(edge.getKey()) != null && pathWeight < dist.get(edge.getKey()))
					{
						dist.put(edge.getKey(), pathWeight);
						prevVertex.put(edge.getKey(), vertex.key);
						q.add(new Vertex<T>(edge.getKey(), pathWeight));
					}
				}
			}
		}
		
		return prevVertex;
	}
	
	// This method generates a random integer between 'min' and 'max', inclusive.
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
				if (edgeDensity > Math.random() && i != j)
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
		/*Set<Entry<T, HashMap<T, Integer>>> vertices = this.map.entrySet();
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
			HashMap<T, Integer> edgeMap = this.getEdgeMap(vertex);
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
			HashMap<T, Integer> edgeMap = this.getEdgeMap(vertex);
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
		Graph<Integer> g = Graph.randomDirectedGraph(10, 0.25, 1, 10);
		
		g.print();
		
		HashMap<Integer, Integer> dijkstra = g.runDijkstra(0);
		
		for (Entry<Integer, Integer> pair : dijkstra.entrySet())
		{
			System.out.println(pair.getValue() + " -> " + pair.getKey());
		}
	}
}
