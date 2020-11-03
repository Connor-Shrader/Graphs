
public class Permutation implements Comparable<Permutation>
{
	int [] array;
	
	public Permutation(int [] array)
	{
		this.array = array;
	}
	
	public String toString()
	{
		return "";
	}
	
	public int compareTo(Permutation p) {
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
