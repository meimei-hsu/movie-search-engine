
import java.io.IOException;

public class WordCounter {
	/**
	 * To count how many times a Keyword appears.
	 */
	private String content;
	
	public WordCounter(String content) {
		this.content = content;
	}

	public int countKeyword(String keyword) throws IOException {
		// To do a case-insensitive search, we turn the whole content and keyword into
		// upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int retVal = 0;
		int fromIdx = 0;
		int found = -1;

		while ((found = content.indexOf(keyword, fromIdx)) != -1) {
			retVal++;
			fromIdx = found + keyword.length();
		}

		return retVal;
	}
}
