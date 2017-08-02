import java.util.Comparator;

public class BoardNodeComparator implements Comparator<BoardNode> {

	@Override
    public int compare(BoardNode x, BoardNode y)
    {
        if (x.getPathCost() < y.getPathCost())
        {
            return -1;
        }
        if (x.getPathCost() > y.getPathCost())
        {
            return 1;
        }
        return 0;
    }
	
}
