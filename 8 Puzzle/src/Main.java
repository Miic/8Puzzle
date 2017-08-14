import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) {
		System.out.println("8 Puzzle Solver\n\nWritten By Michael Cruz\nCPP CS420 Artifical Intelligence\nSummer 2017 Project 1\n\n");
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		
		long totalTime1 = 0;
		int total = 0;
		long totalTime2 = 0;
		
		String response = "";
		mainLoop: while (true) {
			System.out.println("\n\nOptions:\n    (1) Entr(y|ies) from input.txt\n    (2) Manual Entry\n    (3) Generate Random Puzzle\n    (4) Get average time of current data points\n    (5) Exit\n\nEnter Option:");
			response = kb.nextLine();
			int[][] parsedArray;
			boolean[] arrayIndex;
			
			switch (response) {
			case "1":
				parsedArray = new int[3][3];
				arrayIndex = new boolean[9];
				Scanner fileReader = null;
				try {
					fileReader = new Scanner(new File("input.txt"));
				} catch (IOException e) { 
					System.out.println("Missing input.txt!");
					continue;
				}
				while (fileReader.hasNextInt()) {
					
					// Allows both Single Line format or 3x3 Format
					
					int token = fileReader.nextInt();
					if (token > 8) {
						for(int i = 2; i >= 0; i--) {
							for(int j = 2; j >= 0; j--) {
								parsedArray[i][j] = token % 10;
								token /= 10;
								if (parsedArray[i][j] >= 0 && parsedArray[i][j] < 9) {
									arrayIndex[parsedArray[i][j]] = true;
								}
							}
						}
					} else {
						parsedArray[0][0] = token;
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								if (fileReader.hasNextInt() && !(i == 0 && j == 0)) {
									parsedArray[i][j] = fileReader.nextInt();
								}
								if (parsedArray[i][j] >= 0 && parsedArray[i][j] < 9) {
									arrayIndex[parsedArray[i][j]] = true;
								}
							}
						}
					}
					for(int i = 0; i < arrayIndex.length; i++) {
						if (arrayIndex[i] != true) {
							System.out.println("Invalid Board!");
							continue mainLoop;
						}
					}
					BoardGraph tree = new BoardGraph(new Board(parsedArray));
					totalTime2 += test(tree, true);
					totalTime1 += test(tree, false);
					total++;
				}
				break;
			case "2":
				System.out.println("Enter Puzzle String (no spaces): ");
				int token = Integer.parseInt(kb.nextLine());
				parsedArray = new int[3][3];
				arrayIndex = new boolean[9];
				for(int i = 2; i >= 0; i--) {
					for(int j = 2; j >= 0; j--) {
						parsedArray[i][j] = token % 10;
						token /= 10;
						if (parsedArray[i][j] >= 0 && parsedArray[i][j] < 9) {
							arrayIndex[parsedArray[i][j]] = true;
						}
					}
				}
				for(int i = 0; i < arrayIndex.length; i++) {
					if (arrayIndex[i] != true) {
						System.out.println("Invalid Board!");
						continue mainLoop;
					}
				}
				BoardGraph tree = new BoardGraph(new Board(parsedArray));
				totalTime2 += test(tree, true);
				totalTime1 += test(tree, false);
				total++;
				break;
			case "3":
				System.out.println("Enter Depth of Randomly Generated Puzzle: ");
				try {
					totalTime2 += test(new BoardGraph(generatePuzzle(Integer.parseInt(kb.nextLine()))), true);
					totalTime1 += test(new BoardGraph(generatePuzzle(Integer.parseInt(kb.nextLine()))), false);
					total++;
				} catch (NumberFormatException e) {
					System.out.println("Invalid Format. Try again!");
					continue;
				}
				break;
			case "4":
				System.out.println("\nNumber of Tests Run: " + total);
				try {
					System.out.println("A*(h1) Average Time: " + totalTime1/total);
					System.out.println("A*(h2) Average Time: " + totalTime2/total);
					while(!response.equals("y") || !response.equals("n")) {
						System.out.println("\nWould you like to reset current recorded times? (y or n): ");
						response = kb.nextLine();
						switch(response.toLowerCase()) {
						case "y":
							total = 0;
							totalTime1 = 0;
							totalTime2 = 0;
							System.out.println("Recorded Times Reset!");
							break;
						case "n":
							break;
						default:
							System.out.println("Invalid response! Try again!");
						}
					}
				} catch (ArithmeticException e) {}
				break;
			case "5":
				System.out.println("Okay. Goodbye.");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Option! Try again!");
				continue;
			}
			
		
		}
	}
	
	public static long test(BoardGraph tree, boolean testType) {
		List<BoardNode> solution;
		if (testType == true) {
			
			System.out.println("Beginning A*(h2) Search...");
			long startTime2 = System.nanoTime();
			solution = tree.aStar(new H2Comparator());
			startTime2 = System.nanoTime() - startTime2;
			if (solution != null) {
				System.out.println("\nNote: Step 0 is initial state re-cap");
				for(int i = 0; i < solution.size(); i++) {
					System.out.println("=========[ Step " + i + " ]==========\n\nAction - " + solution.get(i).getAction() + "\n\nResult:\n" + solution.get(i) + "\n==============================\n");
				}
				System.out.println("A*(h2) Search for Depth " + (solution.size() - 1) + " completed in " + startTime2 + "ns");
			} else {
				System.out.println("Board is unsolvable!");
				return 0;
			}
				return startTime2;
		} else {
		
			System.out.println("Beginning A*(h1) Search...");
			long startTime1 = System.nanoTime();
			solution = tree.aStar(new H1Comparator());
			startTime1 = System.nanoTime() - startTime1;
			if (solution != null) {
				System.out.println("\nNote: Step 0 is initial state re-cap");
				for(int i = 0; i < solution.size(); i++) {
					System.out.println("=========[ Step " + i + " ]==========\n\nAction - " + solution.get(i).getAction() + "\n\nResult:\n" + solution.get(i) + "\n==============================\n");
				}
				System.out.println("A*(h1) Search for Depth " + (solution.size() - 1) + " completed in " + startTime1 + "ns");
			} else {
				System.out.println("Board is unsolvable!");
				return 0;
			}
				return startTime1;
		}
	}
	
	public static Board generatePuzzle(int depth) {
		int[][] table = new int[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				table[i][j] = (i*3) + j;
			}
		}
		Board board = new Board(table);
		if (depth > 0) {
			if (depth > 30) {
				depth = 30;
			}
			System.out.println("\nGenerating Random Board...");
			Stack<Board> states = new Stack<Board>();
			Board newState = null;
			for(int i = 0; i < depth; i++) {
				System.out.println("  Generated Depth: " + (i + 1));
				newState = board.getPossibleStates().get(board.getAdjacent().get(ThreadLocalRandom.current().nextInt(0, board.getPossibleStates().size())).getActionIdentifier());
				while (!states.isEmpty() && states.contains(newState)) {
					newState = board.getPossibleStates().get(board.getAdjacent().get(ThreadLocalRandom.current().nextInt(0, board.getPossibleStates().size())).getActionIdentifier());
				}
				states.push(board);
				board = newState;
			}
			System.out.println();
		}
		return board;
	}
}
