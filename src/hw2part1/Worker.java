package hw2part1;

public class Worker extends Thread {
	static int[][] grid;
	static int M;
	static int N;
	static int maxGenerations;
	static Barrier barrier;

	int j; // row
	int k; // column

	public Worker(int j, int k) {
		this.j = j;
		this.k = k;
	}
	@Override
	public void run() {
		for (int i = 0; i < maxGenerations; i++) {
			int[] result = findNeighbors(j, k); // first = # of neighbor 0's, second = # of neighbor 1's
			System.out.println(barrier.value);
			barrier.await();
			if (grid[j][k] == 1) { // Checks are based on previous grid
				if (result[1] < 2 || result[1] > 3)
					grid[j][k] = 0; // Updates done to the copy
			} else if (grid[j][k] == 0) {
				if (result[1] == 3)
					grid[j][k] = 1;
			} else {
				throw new Error("Grid has values other than 0 and 1");
			}
			barrier.await();
		}
	}
	public static int[] findNeighbors(int row, int column) {
		int[] result = { 0, 0 }; // number of 0 neighbors, number of 1 neighbors

		// All possible neighbors
		checkValidBlock(result, row - 1, column);
		checkValidBlock(result, row + 1, column);
		checkValidBlock(result, row, column - 1);
		checkValidBlock(result, row, column + 1);
		checkValidBlock(result, row - 1, column - 1);
		checkValidBlock(result, row - 1, column + 1);
		checkValidBlock(result, row + 1, column - 1);
		checkValidBlock(result, row + 1, column + 1);

		return result;
	}

	// This checks if a neighbor is in the grid and checks its value.
	// If it is 0 increment result[0], if 1, increment result[1].
	public static void checkValidBlock(int[] result, int first, int second) {
		if (first >= 0 && first < M && second >= 0 && second < N) {
			if (grid[first][second] == 0)
				result[0]++;
			else if (grid[first][second] == 1)
				result[1]++;
			else
				throw new Error("Grid has values other than 0 and 1");
		}
	}
}
