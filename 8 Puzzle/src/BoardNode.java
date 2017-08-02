
public class BoardNode {
	private Board state;
	private String action;
	private int pathCost;
	private int depth;
	private BoardNode parent;
	
	public BoardNode(Board initialState) { //For initial root node.
		state = initialState;
		action = null;
		pathCost = 0;
		depth = 0;
		parent = null;
	}
	
	private BoardNode(Board state, String action, int pathCost, int depth, BoardNode parent) { //For expansion
		this.state = state;
		this.action = action;
		this.pathCost = parent.pathCost + pathCost;
		depth = parent.depth + depth;
		this.parent = parent;
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Board getState() {
		return state;
	}

	public String getAction() {
		return action;
	}

	public int getPathCost() {
		return pathCost;
	}

	public BoardNode getParent() {
		return parent;
	}
	
	protected BoardNode childNode(String action) {
		BoardNode newChildNode = new BoardNode(state.getPossibleStates().get(action), action, pathCost+1, depth+1, this);
		return newChildNode;
	}
	
	public boolean isGoal() {
		return state.isGoal();
	}
	
	public String toString() {
		return state.toString();
	}
	
	public boolean equals(BoardNode otherNode) {
		return state.equals(otherNode.state);
	}
}
	
