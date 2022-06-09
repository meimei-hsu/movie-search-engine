
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WebPage {
	/**
	 * To store and evaluate a web page.
	 */
	private String url;
	private WordCounter counter;
	private String name;
	private double score; // page score for sorting
	private String html;

	public WebPage(String url, String name, WebContent webContent) throws IOException {
		this.url = url;
		this.name = name;
		html = webContent.fetchContent(url);
		System.out.println("content:"+html);
	}

	public void setScore(ArrayList<Keyword> keywords) throws IOException {
		score = 0;
		counter = new WordCounter(html);
		for (Keyword k : keywords) {
			score += k.getWeight() * counter.countKeyword(k.getName());
			System.out.println("word"+k.getName()+"count:"+counter.countKeyword(k.getName()));
		}
		System.out.println(name+score);
	}

	public String getWebUrl() {
		return url;
	}

	public String getWebName() {
		return name;
	}
	
	public WordCounter getCounter() {
		return counter;
	}

	public double getWebScore() {
		return score;
	}
}
