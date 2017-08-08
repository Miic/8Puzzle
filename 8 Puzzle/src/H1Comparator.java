
import java.util.Comparator;

public class H1Comparator implements Comparator<BoardNode> {

	@Override
    public int compare(BoardNode x, BoardNode y)
    {
        if (x.getPathCost() + x.h1() < y.getPathCost() + y.h1())
        {
            return -1;
        }
        if (x.getPathCost() + y.h1() > y.getPathCost() + y.h1())
        {
            return 1;
        }
        return 0;
    }
	
}
