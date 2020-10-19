// Graph.java
// Connor Shrader

// Implementation of a graph using adjacency matrices.
// This class was made based on information in my Computer Science 2 class.

import java.util.*;
import java.io.*;

public class Graph
{
	private boolean [][] matrix;
	private int size;
	
	public Graph(String filename)
	{
		try
		{
			Scanner s = new Scanner(new File(filename));
			int size = s.nextInt();
			matrix = new boolean[size][size];
			
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					matrix[i][j] = (s.nextInt() != 0);
					
		}
		catch(FileNotFoundException e)
		{
			return;
		}
	}
	
	public void depthFirstSearch(int start)
	{
		boolean [] visited = new boolean[this.size];
		depthFirstSearch(start, visited);
		
	}
	
	private void depthFirstSearch(int start, boolean [] visited)
	{
		visited[start] = true;
		System.out.println(start);
		
		for (int i = 0; i < this.size; i++)
			if (matrix[start][i] && !visited[i])
				depthFirstSearch(i, visited);
	}
}
