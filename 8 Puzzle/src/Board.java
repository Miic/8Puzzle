import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Board {

	private int[][] board;
	private Coordinates blank;

	public Board(int[][] board) {
		this.board = board;
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(board[i][j] == 0) {
					blank = new Coordinates(i, j);
					break;
				}
			}
		}
	}
	
	private Board(int[][] board, Coordinates previousBlank, Coordinates newBlank) {
		this.board = new int[3][3];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				this.board[i][j] = board[i][j];
			}
		}
		blank = new Coordinates(previousBlank.getX(), previousBlank.getY(), newBlank.getActionIdentifier());
		swap(newBlank.getX(), newBlank.getY());
	}
	
	public boolean isSolvable() {
		int counter = 0;
		for(int i = 0; i < 9; i++) {
			if ( i+1 < 9 && board[i/3][i%3] != 0 && board[(i+1)/3][(i+1)%3] != 0 &&  board[i/3][i%3] < board[(i+1)/3][(i+1)%3]) {
				counter++;
			}
		}
		return (counter % 2 == 0);
	}
	
	public List<Coordinates> getAdjacent() {
		ArrayList<Coordinates> returnList = new ArrayList<Coordinates>();
		int x = blank.getX();
		int y = blank.getY();
		
		if (x+1 < board.length) {
			returnList.add(new Coordinates(x+1, y, "Down"));
		}
		
		if (x-1 >= 0) {
			returnList.add(new Coordinates(x-1, y, "Up"));
		}
		
		if (y+1 < board[x].length) {
			returnList.add(new Coordinates(x, y+1, "Right"));
		}
		
		if (y-1 >= 0) {
			returnList.add(new Coordinates(x, y-1, "Left"));
		}
		return returnList;
	}
	
	public Hashtable<String, Board> getPossibleStates() {
		Hashtable<String, Board> returnTable = new Hashtable<String,Board>();
		List<Coordinates> list = getAdjacent();
		for(Coordinates coords : list) {
			returnTable.put(coords.getActionIdentifier(), new Board(board, blank, coords));
		}
		return returnTable;
	}
	
	protected Board processMove(Coordinates coords) {
		return new Board(board, blank, coords);
	}
	
	private void swap(int x2, int y2) {
		int x = blank.getX();
		int y = blank.getY();
		
		int holder = board[x][y];
		board[x][y] = board[x2][y2];
		board[x2][y2] = holder;
		blank.setX(x2);
		blank.setY(y2);
	}
	
	public boolean isGoal() {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if (board[i][j] != (i*3) + j) {
					return false;
				}
			}
		}
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				sb.append(board[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public boolean equals(Board otherBoard) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if (board[i][j] != otherBoard.board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int h1() {
		int counter = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if (board[i][j] != (i*3)+j) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	public int h2() {
		int sum = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				sum+= Math.abs( ((i*3)+j) - board[i][j] ); 
			}
		}
		return sum;
	}
}


