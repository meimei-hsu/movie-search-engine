
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CalcScore {
	/**
	 * To count how many times a Keyword appears.
	 */
	private HashMap<String, String> query;
	private GoogleQuery google;
	private ArrayList<Keyword> keywords;

	public CalcScore(HashMap<String, String> query, KeywordList lst) {
		this.query = query;
		google = new GoogleQuery();

		keywords = lst.getKeywordList();
	}

	private int countKeyword(String content, String keyword) throws IOException {
		// To do a case-insensitive search, turn both string into upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int count = 0;
		int fromIdx = 0;
		int found = -1;

		while ((found = content.indexOf(keyword, fromIdx)) != -1) {
			count++;
			fromIdx = found + keyword.length();
		}

		return count;
	}

	public HashMap<Integer, ArrayList<String>> calcScore() throws IOException {
		HashMap<Integer, ArrayList<String>> retVal = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> error = new ArrayList<String>();

		// get every result link and calculate its score
		for (String title : query.keySet()) {

			// fetch the HTML content of each page
			String content = google.fetchContent(query.get(title));
			// deal with Exceptions
			if (content.equals(query.get(title))) {
				error.add(title);
				System.out.printf("Error: Content of [%s] \n", title);
				continue;
			}

			int score = 0;
			for (Keyword k : keywords) {
				int count = countKeyword(content, k.getName());
				score += k.getWeight() * count;

				if (count != 0) {
					System.out.printf("%s * %d | title: %s\n", k.getName(), count, title);
				}
			}

			ArrayList<String> t = new ArrayList<String>();
			// store the title with its score in [@retVal]
			if (retVal.containsKey(Integer.valueOf(score))) {
				t = retVal.get(Integer.valueOf(score));
			}
			t.add(title);
			retVal.put(score, t);
			System.out.printf("%d = %s\n\n", score, title);
		}

		// delete error link from query
		for (String s : error) {
			query.remove(s);
		}

		return retVal;
	}

	public List<Integer> sort(HashMap<Integer, ArrayList<String>> score) throws IOException {
		List<Integer> retVal = new ArrayList<Integer>();

		// sort {[@score] by a descend order
		for (Integer sc : score.keySet()) {
			retVal.add(sc);
		}
		Collections.sort(retVal, Collections.reverseOrder());
		return retVal;
	}

}
