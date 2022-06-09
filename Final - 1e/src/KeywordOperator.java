
import java.util.ArrayList;
import java.util.LinkedList;

public class KeywordOperator {
	/**
	 * To store and operate Keywords.
	 */
	protected ArrayList<Keyword> lst;

	public KeywordOperator() {
		this.lst =  new ArrayList<Keyword>();
	}
	
	public ArrayList<Keyword> getKeywordList(){
		return lst;
	}

	public void add(Keyword keyword) {
		for (int i = 0; i < lst.size(); i++) {
			Keyword k = lst.get(i);
			// compare "weight" and add keyword in descend order
			if (keyword.getWeight() >= k.getWeight()) {
				lst.add(i,keyword);
				return;
			}
		}
		lst.add(keyword);
	}

	public void outputIndex(int i) {
		if (i > lst.size()) {
			System.out.println("InvalidOperation");
			return;
		}
		Keyword k = lst.get(i);
		System.out.println(k);
	}

	public void outputHas(String pattern) {
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++) {
			Keyword k = lst.get(i);
			if (k.getName().contains(pattern)) {
				results.add(k);
			}
		}
		if (results.isEmpty()) {
			System.out.println("NotFound");
		} else {
			printKeywordList(results);
		}
	}

	public void outputName(String pattern) {

		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++) {
			Keyword k = lst.get(i);
			if (k.getName().equals(pattern)) {
				results.add(k);
			}
		}
		if (results.isEmpty()) {
			System.out.println("NotFound");
		} else {
			printKeywordList(results);
		}
	}

	public void outputFirstN(int n) {
		if (n > lst.size()) {
			System.out.println("InvalidOperation");
			return;
		}
		LinkedList<Keyword> found = new LinkedList<>();

		for (int i = 0; i < n; i++) {
			Keyword k = lst.get(i);
			found.add(k);
		}

		printKeywordList(found);
	}

	public void deleteIndex(int i) {
		if (i >= lst.size()) {
			return;
		}

		lst.remove(i);
	}

	public void deleteHas(String pattern) {
		LinkedList<Keyword> found = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++) {
			Keyword k = lst.get(i);
			if (k.getName().contains(pattern)) {
				found.add(k);
			}
		}

		if (!found.isEmpty()) {
			lst.removeAll(found);
		}
	}

	public void deleteName(String name) {
		LinkedList<Keyword> found = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++) {
			Keyword k = lst.get(i);
			if (k.getName().equals(name)) {
				found.add(k);
			}
		}

		if (!found.isEmpty()) {
			lst.removeAll(found);
		}
	}

	public void deleteFirstN(int n) {
		LinkedList<Keyword> found = new LinkedList<>();
		if (n > lst.size()) {
			System.out.println("InvalidOperation");
			return;
		}

		for (int i = 0; i < n; i++) {
			Keyword k = lst.get(i);
			found.add(k);
		}

		if (!found.isEmpty()) {
			lst.removeAll(found);
		}
	}

	public void deleteAll() {
		lst = new ArrayList<Keyword>();
	}

	private void printKeywordList(LinkedList<Keyword> kLst) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < kLst.size(); i++) {
			Keyword k = kLst.get(i);
			if (i > 0)
				sb.append(" ");
			sb.append(k.toString());
		}
		System.out.println(sb.toString());
	}
}
