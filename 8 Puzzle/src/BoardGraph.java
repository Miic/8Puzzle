import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

public class BoardGraph {
	
	private BoardNode root;
	
	/*
	 * Note: The hardest possible 8 Puzzle problem takes up to 31 moves to solve. Therefore, we can limit the depth to 31
	 */
	
	public BoardGraph(Board board) {
		root = new BoardNode(board);
	}
	
	public List<BoardNode> aStar(Comparator<BoardNode> comparator) {
		PriorityQueue<BoardNode> frontier;
		Hashtable<BoardNode, Boolean> explored;
		frontier = new PriorityQueue<BoardNode>(10, comparator);
		frontier.add(root);
		explored = new Hashtable<BoardNode, Boolean>();
		
		BoardNode node = frontier.peek();
		if (!node.isSolvable()) {
			return null;
		}
		
		while (!frontier.isEmpty()) {
			node = frontier.poll();
			if (node.isGoal()) {
				List<BoardNode> returnList = new ArrayList<BoardNode>();
				while(node.getParent() != null) {
					returnList.add(0, node);
					node = node.getParent();
				}
				returnList.add(0, node);
				return returnList;
			}
			explored.put(node, true);
			
			
			List<Coordinates> actions = node.getState().getAdjacent();
			BoardNode actionNode;
			
			for (Coordinates coords : actions) {
				actionNode = node.childNode(coords);
				if (!frontier.contains(actionNode) && !explored.containsKey(actionNode)) {
					frontier.add(actionNode);
				} else if (frontier.contains(actionNode)) {
					frontier.add(actionNode);
					frontier.remove(actionNode);
				}
			}	
		}
		return null;
	}
}
