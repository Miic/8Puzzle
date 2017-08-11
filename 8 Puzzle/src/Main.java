import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) {
		System.out.println("8 Puzzle Solver\n\nWritten Michael Cruz\nCPP CS420 Artifical Intelligence\nProject 1\n\n");
		Scanner kb = new Scanner(System.in);
		
		String response = "";
		while (true) {
			System.out.println("\n\nOptions:\n    (1) Entr(y|ies) from input.txt\n    (2) Manual Entry\n    (3) Generate Random Puzzle\n    (4) Exit\n\nEnter Option:");
			response = kb.nextLine();
			int[][] parsedArray;
			
			switch (response) {
			case "1":
				parsedArray = new int[3][3];
				Scanner fileReader = null;
				try {
					fileReader = new Scanner(new File("input.txt"));
				} catch (IOException e) { 
					System.out.println("Missing input.txt!");
					continue;
				}
				while (fileReader.hasNextInt()) {
					int token = fileReader.nextInt();
					if (token > 8) {
						for(int i = 2; i >= 0; i--) {
							for(int j = 2; j >= 0; j--) {
								parsedArray[i][j] = token % 10;
								token /= 10;
							}
						}
					} else {
						parsedArray[0][0] = token;
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								if (fileReader.hasNextInt() && !(i == 0 && j == 0)) {
									parsedArray[i][j] = fileReader.nextInt();
								}
							}
						}
					}
					BoardGraph tree = new BoardGraph(new Board(parsedArray));
					test(tree);
				}
				break;
			case "2":
				System.out.println("Enter Puzzle String (no spaces): ");
				int token = Integer.parseInt(kb.nextLine());
				parsedArray = new int[3][3];
				for(int i = 2; i >= 0; i--) {
					for(int j = 2; j >= 0; j--) {
						parsedArray[i][j] = token % 10;
						token /= 10;
					}
				}
				BoardGraph tree = new BoardGraph(new Board(parsedArray));
				test(tree);
				break;
			case "3":
				System.out.println("Enter Depth of Randomly Generated Puzzle: ");
				test(new BoardGraph(generatePuzzle(Integer.parseInt(kb.nextLine()))));
				break;
			case "4":
				System.out.println("Okay. Goodbye.");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Option! Try again!");
				continue;
			}
			
		
		}
	}
	
	public static void test(BoardGraph tree) {
		List<BoardNode> solution;
		
		System.out.println("Beginning A*(h1) Search...");
		long startTime1 = System.nanoTime();
		solution = tree.aStar(new H1Comparator());
		startTime1 = System.nanoTime() - startTime1;
		if (solution != null) {
			System.out.println("\nNote: Step 0 is initial state re-cap");
			for(int i = 0; i < solution.size(); i++) {
				System.out.println("=========[ Step " + i + " ]==========\n\nAction - " + solution.get(i).getAction() + "\n\nResult:\n" + solution.get(i) + "\n==============================\n");
			}
		} else {
			System.out.println("Board is unsolvable!");
		}
		
		System.out.println("Beginning A*(h2) Search...");
		long startTime2 = System.nanoTime();
		solution = tree.aStar(new H2Comparator());
		startTime2 = System.nanoTime() - startTime2;
		if (solution != null) {
			System.out.println("\nNote: Step 0 is initial state re-cap");
			for(int i = 0; i < solution.size(); i++) {
				System.out.println("=========[ Step " + i + " ]==========\n\nAction - " + solution.get(i).getAction() + "\n\nResult:\n" + solution.get(i) + "\n==============================\n");
			}
			System.out.println("H1 Solution found in : " + startTime1 + "ns" );
			System.out.println("H2 Solution found in : " + startTime2 + "ns" );
		} else {
			System.out.println("Board is unsolvable!");
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
			Board lastState = null;
			Board newState = null;
			for(int i = 0; i < depth; i++) {
				newState = board.getPossibleStates().get(board.getAdjacent().get(ThreadLocalRandom.current().nextInt(0, board.getPossibleStates().size() - 1)).getActionIdentifier());
				while (lastState != null && lastState.equals(newState)) {
					newState = board.getPossibleStates().get(board.getAdjacent().get(ThreadLocalRandom.current().nextInt(0, board.getPossibleStates().size() - 1)).getActionIdentifier());
				}
				lastState = board;
				board = newState;
			}
		}
		return board;
	}
}
