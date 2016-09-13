/**
 * 
 * @author:			Mikhail Zulkarneev
 * Created:			09.09.2016
 * Last modified:	13.09.2016
 * 
 */

import edu.princeton.cs.algs4.*;

public class PercolationStats
{
	private int T;
	private double[] probs;
	
	/**
	 * Performs independent experiments on a grid
	 * @param n -		size of grid
	 * @param trials - 	number of experiments
	 */
	public PercolationStats(int n, int trials)
	{
		if (n <= 0)
			throw new java.lang.IllegalArgumentException();
		
		if (trials <= 0)
			throw new java.lang.IllegalArgumentException();
		
		
		T = trials;
		probs = new double[T];		
		
		for (int t = 0; t < T; t++)
		{
			Percolation percolation = new Percolation(n);
			int openSitesNum = 0;
			while (!percolation.percolates())
			{
				int i = StdRandom.uniform(n) + 1;
				int j = StdRandom.uniform(n) + 1;
				if (!percolation.isOpen(i, j))
				{
					openSitesNum++;
					percolation.open(i, j);
				}
			}
			probs[t] = 1.0 * openSitesNum / (n*n);
		}
	}
	
	/**
	 * @return Sample mean of percolation threshold
	 */
	public double mean()
	{
		return StdStats.mean(probs);
	}
	
	/**
	 * @return Sample standard deviation of percolation threshold
	 */
	public double stddev()
	{
		return StdStats.stddev(probs);
	}
	
	/**
	 * @return Low  endpoint of 95% confidence interval
	 */
	public double confidenceLo()
	{
		return mean() - 1.96*stddev() / Math.sqrt(T);
	}
	
	/**
	 * @return High endpoint of 95% confidence interval
	 */
	public double confidenceHi()
	{
		return mean() + 1.96*stddev() / Math.sqrt(T);
	}

	public static void main(String[] args)
	{
		int n = StdIn.readInt();
		int trials = StdIn.readInt();
		PercolationStats ps = new PercolationStats(n, trials);
		StdOut.printf("mean\t\t\t= %f\n", ps.mean());
		StdOut.printf("stddev\t\t\t= %f\n", ps.stddev());
		StdOut.printf("95%% confidence interval\t= %f, %f\n", ps.confidenceLo(), ps.confidenceHi());		
	}
}
