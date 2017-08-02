import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Board {

	private int[][] board;
	private Coordinates blank;
	private Hashtable<String, Board> returnTable; //cache

	public Board(int[][] board) {
		this.board = board;
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(board[i][j] == 0) {
					blank = new Coordinates(i, j);
				}
			}
		}
	}
	
	private Board(int[][] board, Coordinates previousBlank, Coordinates newBlank) {
		this.board = board;
		blank = previousBlank;
		swap(newBlank.getX(), newBlank.getY());
	}
	
	public List<Coordinates> getAdjacent() {
		ArrayList<Coordinates> returnList = new ArrayList<Coordinates>();
		int x = blank.getX();
		int y = blank.getY();
		
		if (x+1 < board.length) {
			returnList.add(new Coordinates(x+1, y, "Right"));
		}
		
		if (x-1 >= 0) {
			returnList.add(new Coordinates(x-1, y, "Left"));
		}
		
		if (y+1 < board[x].length) {
			returnList.add(new Coordinates(x, y+1, "Up"));
		}
		
		if (y-1 >= 0) {
			returnList.add(new Coordinates(x, y-1, "Down"));
		}
		return returnList;
	}
	
	public Hashtable<String, Board> getPossibleStates() {
		if (returnTable == null) {
			returnTable = new Hashtable<String,Board>();
			List<Coordinates> list = getAdjacent();
			for(Coordinates coords : list) {
				returnTable.put(coords.getActionIdentifier(), new Board(board, blank, coords));
			}
		}
		return returnTable;
	}
	
	private void swap(int x2, int y2) {
		int x = blank.getX();
		int y = blank.getY();
		
		int holder = board[x][y];
		board[x][y] = board[x2][y2];
		board[x][y] = holder;
		blank.setX(x2);
		blank.setY(y2);
	}
	
	public boolean isGoal() {
		return Arrays.equals(board, new int[][] {{0,1,2},
												 {3,4,5},
												 {6,7,8}});
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				sb.append(board[i][j] + " ");
			}
		}
		return sb.toString();
	}
	
	public boolean equals(Board otherBoard) {
		return Arrays.equals(board, otherBoard.board);
	}
}


