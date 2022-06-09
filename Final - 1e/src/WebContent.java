
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

public class WebContent {
	/**
	 * To crawl down web content and identify specific info.
	 */
	private String content;
	

	public WebContent() {
	}

	public String fetchContent(String url) throws IOException {
		String retVal = "";

		try {
			URL u = new URL(url);
			
			
			URLConnection conn = u.openConnection();
	
			conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
	
			InputStream in = conn.getInputStream();
	
			InputStreamReader inReader = new InputStreamReader(in, "utf-8");
	
			BufferedReader bufReader = new BufferedReader(inReader);
			String line = null;
	
			while ((line = bufReader.readLine()) != null) {
				retVal += line;
	
			}
		
		}catch (MalformedURLException e){
			e.printStackTrace();
		}
		
		return retVal;
	}

	public HashMap<String, String> google(String url) throws IOException {

		content = fetchContent(url);
		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);

		Elements lis = doc.select("div");

		lis = lis.select(".kCrYT");

		for (Element li : lis) {
			try {
				String citeUrl = li.select("a").attr("href");
				String title = li.select("a").select(".vvjwJb").text();
				if (title.equals("")) {
					continue;
				}
				System.out.println(title + "," + citeUrl);
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			} 
		}
		return retVal;
	}

}
