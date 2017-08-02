import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class BoardGraph {

	private PriorityQueue<BoardNode> frontier;
	private Hashtable<BoardNode, Boolean> explored;
	private BoardNode root;
	private int size;
	
	public BoardGraph(Board board) {
		root = new BoardNode(board);
		frontier = new PriorityQueue<BoardNode>(10, new BoardNodeComparator());
		frontier.add(root);
		explored = new Hashtable<BoardNode, Boolean>();
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public List<BoardNode> uniformCostSearch() {
		BoardNode node;
		
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
						if (itNode.equals(actionNode) && itNode.getPathCost() > actionNode.getPathCost()) {
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
