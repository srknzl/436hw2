// Serkan Özel // 2015400123 // serkan.ozel@boun.edu.tr
// CMPE436-Assignment 2

package hw2part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length >= 3) {
			Worker.M = Integer.parseInt(args[0]);
			Worker.N = Integer.parseInt(args[1]);
			Worker.grid = new int[Worker.M][Worker.N];
			Worker.maxGenerations = Integer.parseInt(args[2]);
			Worker.barrier = new Barrier(Worker.M*Worker.N);
		} else {
			throw new Error("Syntax: java hw1part2.Main M N maxGenerations [file that contains cell]");
		}

		if (args.length == 4) { // File given
			File file = new File(args[3]);
			if (!file.exists()) {
				System.out.println(args[3] + " does not exist!");
				throw new FileNotFoundException();
			}
			Scanner scanner = new Scanner(file);
			for (int i = 0; i < Worker.M; i++) {
				for (int j = 0; j < Worker.N; j++) {
					Worker.grid[i][j] = scanner.nextInt(); // Read values from file
				}
			}
			scanner.close();
		} else { // Randomly fill otherwise
			for (int i = 0; i < Worker.M; i++) {
				for (int j = 0; j < Worker.N; j++) {
					if (Math.random() > 0.5)
						Worker.grid[i][j] = 0;
					else
						Worker.grid[i][j] = 1;
				}
			}
		}
		Vector<Worker> workers = new Vector<Worker>();
		// Spawn threads
		for(int i=0; i < Worker.M * Worker.N; i++) {
			Worker w = new Worker(i / Worker.N, i % Worker.N);
			// System.out.println(Worker.barrier.value);
			workers.add(w);
			w.run();
		}
		
		for(int i=0; i < Worker.M * Worker.N; i++) {
			try {
				workers.elementAt(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Outputting
		for (int j = 0; j < Worker.M; j++) {
			for (int k = 0; k < Worker.N; k++) {
				System.out.print(Worker.grid[j][k] + " ");
			}
			System.out.println("");
		}
	}

	
}
