/**
 * 
 * @author:			Mikhail Zulkarneev
 * Created:			08.09.2016
 * Last modified:	09.09.2016
 * 
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
	private WeightedQuickUnionUF uf, fullUF;
	private boolean[][] opened;
	int N;
	
	/**
	 * Create n-by-n grid, with all sites blocked
	 * @param n - Size of grid
	 */
	public Percolation(int n)
	{
		N = n;
		
		opened = new boolean[N][N];
		for (int i=0; i<N; i++)
			for(int j=0; j<n; j++)
				opened[i][j] = false;
		
		uf = new WeightedQuickUnionUF(N+2);
		fullUF = new WeightedQuickUnionUF(N+1);
		
		for (int i=1; i<=N; i++)
		{
			uf.union(0, i);
			uf.union(index(N,i), N*N+1);
			fullUF.union(0, i);
		}
	}
	
	private int index(int i, int j)
	{
		return (i-1)*N + j;
	}
	
	/**
	 * Open site (row i, column j) if it is not open already
	 * @param i - Row index of the site
	 * @param j - Column index of the site
	 */
	public void open(int i, int j)
	{
		if (isOpen(i, j)) return;
		
		opened[i-1][j-1] = true;
		
		for (int k=i-1; k<=i+1; k+=2)
			if (k>=1 && k<=N)
			{
				uf.union(index(k,j), index(i,j));
				fullUF.union(index(k,j), index(i,j));				
			}
				
		
		for (int l=j-1; l<=j+1; l+=2)
			if (j>=1 && j<=N)
			{
				uf.union(index(i,l), index(i,j));
				fullUF.union(index(i,l), index(i,j));				
			}						
	}
	
	/**
	 * Checks if the site is opened
	 * @param i - Row index of the site
	 * @param j - Column index of the site
	 * @return - true if site is opened else return false
	 */
	public boolean isOpen(int i, int j)
	{
		if (i<1 || i>=N || j<1 || j>=N)
			throw new java.lang.IndexOutOfBoundsException(); 
		
		return opened[i+1][j+1];
	}
	
	/**
	 * Checks if the site if full
	 * @param i - Row index of the site
	 * @param j - Column index of the site
	 * @return - true if the site if full else return false
	 */
	public boolean isFull(int i, int j)
	{
		return isOpen(i,j) && fullUF.connected(0, index(i,j));
	}
	
	/**
	 * Checks if the system percolates
	 * @return - true is the system percolates else return false
	 */
	public boolean percolates()
	{
		return uf.connected(0, N*N+1);
	}
	
	public static void main(String[] args)
	{		
	}
}