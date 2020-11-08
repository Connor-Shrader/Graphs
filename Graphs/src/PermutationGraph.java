import java.util.ArrayList;
import java.util.HashMap;

class Permutation implements Comparable<Permutation>
{
	int [] array;
	
	public Permutation(int [] array)
	{
		this.array = array;
	}
	
	public int getSize()
	{
		return this.array.length;
	}
	
	public int [] getArray()
	{
		return this.array;
	}
	
	public int get(int index)
	{
		if (index < 0 || index > this.getSize())
			return index;
		else
			return this.getArray()[index];
	}
	
	public String toString()
	{
		return "";
	}
	
	public int compareTo(Permutation p) {
		if (this.getSize() > p.getSize())
		{
			return 1;
		}
		else if (this.getSize() < p.getSize())
		{
			return -1;
		}
		else
		{
			for (int i = 0; i < this.getSize(); i++)
			{
				if (this.get(i) != p.get(i))
				{
					return this.get(i) - p.get(i);
				}
			}
		}
		
		return 0;
	}
	
	public static Permutation multiply(Permutation left, Permutation right)
	{
		return left;
	}
	
	public Permutation leftMultiply(Permutation left)
	{
		return Permutation.multiply(left, this);
	}
	
	public Permutation rightMultiply(Permutation right)
	{
		return Permutation.multiply(this, right);
	}
}

public class PermutationGraph
{
	Graph<Permutation> graph;
	HashMap<Integer, Permutation> idToPermutation;
	HashMap<Permutation, Integer> permutationToID;
	
	public PermutationGraph()
	{
		this.graph = new Graph<Permutation>();
		this.idToPermutation = new HashMap<Integer, Permutation>();
		this.permutationToID = new HashMap<Permutation, Integer>();
	}
	
	public PermutationGraph(ArrayList<Permutation> generators)
	{
		this();
		for (int i = 0; i < generators.size(); i++)
		{
			idToPermutation.put(i, generators.get(i));
			permutationToID.put(generators.get(i), i);
		}
	}
}
