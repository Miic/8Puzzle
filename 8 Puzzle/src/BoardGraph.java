import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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
	
	public List<BoardNode> aStarH1() {
		PriorityQueue<BoardNode> frontier;
		Hashtable<BoardNode, Boolean> explored;
		frontier = new PriorityQueue<BoardNode>(10, new H1Comparator());
		frontier.add(root);
		explored = new Hashtable<BoardNode, Boolean>();
		
		BoardNode node = frontier.peek();
		
		if (!node.isSolvable()) {
			return null;
		}
		
		while (true) {
			if (frontier.isEmpty()) {
				return null;
			}
			
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
			
			if (node.getDepth() <= 31) { // All 8 Puzzle problems can be solved in 31 moves or less.
			
				List<Coordinates> actions = node.getState().getAdjacent();
				BoardNode actionNode;
			
				for (Coordinates coords : actions) {
					actionNode = node.childNode(coords);
					if (!frontier.contains(actionNode) && !explored.containsKey(actionNode)) {
						frontier.add(actionNode);
					} else if (frontier.contains(actionNode)) {
						Iterator<BoardNode> it = frontier.iterator();
						BoardNode itNode;
						while (it.hasNext()) {
							itNode = it.next();
							if (itNode.equals(actionNode) && itNode.getPathCost() + itNode.h1() > actionNode.getPathCost() + actionNode.h1()) {
								frontier.remove(actionNode);
								frontier.add(actionNode);
								break;
							}
						}
					}
				}
			}
			
		}
	}
	
	public List<BoardNode> aStarH2() {
		PriorityQueue<BoardNode> frontier;
		Hashtable<BoardNode, Boolean> explored;
		frontier = new PriorityQueue<BoardNode>(10, new H2Comparator());
		frontier.add(root);
		explored = new Hashtable<BoardNode, Boolean>();
		
		BoardNode node = frontier.peek();
		
		if (!node.isSolvable()) {
			return null;
		}
		
		while (true) {
			if (frontier.isEmpty()) {
				return null;
			}
			System.out.println(frontier.peek() + " " + frontier.peek().getPathCost() + " " + frontier.peek().h2());
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
			
			if (node.getDepth() <= 31) {
			
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
			
		}
	}
}
