import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnitTest {
	public static void main(String[] args) throws IOException {
		System.out.println("Start testing.");

		// searchKeywords: "影評", "adventure", "柯南", "林美秀", "資料結構", "郁方", "賽車", "Oscar", "tom cruise", "21世紀", "2021", "印度"
		String[] inputs = { "" };

		KeywordList lst = new KeywordList();
		// filter options: romance, fantasy, action, comedy, thriller (default: "")
		lst.filter("horror");
		
		for (int i = 0; i < inputs.length; i++) {
			String input = inputs[i];
			GoogleQuery google = new GoogleQuery();
			HashMap<String, String> query = google.query(input);
			int query_before = query.size();
			
			System.out.printf("\nCalculating score...\n");
			CalcScore calc = new CalcScore(query, lst);
			HashMap<Integer, ArrayList<String>> score = calc.calcScore();
			List<Integer> order = calc.sort(score);

			for (Integer integer : order) {
				System.out.printf("%s = %d\n", score.get(integer), integer);
			}
			System.out.printf("\nQuery_before size: %d\n", query_before);
			System.out.println("Query_after size: " + query.size());
			System.out.println("filter: horror");
			System.out.println();
		}
		System.out.println("End of testing.");
	}
}
