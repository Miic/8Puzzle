import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		int[][] parsedArray = new int[3][3];
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(new File("input.txt"));
		} catch (IOException e) { 
			System.out.println("Missing input.txt!");
			System.exit(1);
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				parsedArray[i][j] = fileReader.nextInt();
			}
		}
		
		BoardGraph tree = new BoardGraph(new Board(parsedArray));
		
		System.out.println("Beginning Uniform Cost Search...");
		float startTime = System.currentTimeMillis();
		List<BoardNode> solution = tree.uniformCostSearch();
		System.out.println("Solution found in : " + (System.currentTimeMillis() - startTime) + "ms");
		
		for(int i = 0; i < solution.size(); i++) {
			System.out.println("\n=========[ Step " + i + " ]==========\n\nAction - " + solution.get(i).getAction() + "\n\n" + solution.get(i) + "\n==============================\n");
		}
	}
}
