import java.util.Comparator;

/**
 * StringComp.java November 21, 2019
 * 
 * @author wilcda01 String comparator override for graph
 */
public class StringComp implements Comparator<String> {
	public StringComp() { // not sure if needed?

	}

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
}
