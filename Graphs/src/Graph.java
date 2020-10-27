// Graph.java
// Connor Shrader

// Implementation of a graph using adjacency matrices.
// This class was made based on information in my Computer Science 2 class.

import java.util.*;
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
		return list;
	}
	
	public Set<T> getVertices()
	{
		return list.keySet();
	}
	
	// This method adds a new vertex to the Graph, as long as there is not already a vertex
	// with the given name.
	public void addVertex(T name)
	{
		if (list.containsKey(name))
			return;
		
		list.put(name, new HashMap<T, Integer>());
	}
	
	// This method creates an edge from vertex start to vertex end with the given weight
	// (unless an edge already exists between them). If directed is false, then an edge
	// is also added from end to start (if there is not already an edge).
	public void addEdge(T start, T end, Integer weight, boolean directed)
	{
		if (directed == false)
		{
			this.addEdge(start, end, weight, true);
			this.addEdge(end, start, weight, true);
		}
		else
		{
			HashMap<T, Integer> startList = this.list.get(start);
			if (startList.containsKey(end))
				return;
			
			startList.put(end, weight);
		}
	}
}
