
import java.util.Comparator;

public class H2Comparator implements Comparator<BoardNode> {

	@Override
    public int compare(BoardNode x, BoardNode y)
    {
        if (x.getPathCost() + x.h2() < y.getPathCost() + y.h2())
        {
            return -1;
        }
        if (x.getPathCost() + x.h2() > y.getPathCost() + y.h2())
        {
            return 1;
        }
        return 0;
    }
	
}
