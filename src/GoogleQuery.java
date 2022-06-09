
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

public class GoogleQuery {
	/**
	 * To crawl down web content and identify specific info.
	 */
	public GoogleQuery() {
	}

	public String fetchContent(String url) throws IOException {
		String retVal = "";

		try {
			URL u = new URL(url);

			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0) Chrome/96.0.4664.110 Safari/15.2");
			CookieHandler.setDefault(new CookieManager());

			if (conn.getResponseCode() == 403 || conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
				retVal = url;
				System.out.printf("Error %d: %s\n", conn.getResponseCode(), retVal);
			} else {
				InputStream in = conn.getInputStream();
				retVal = new String(in.readAllBytes(), StandardCharsets.UTF_8); // will fail with large amounts of data
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			retVal = url;
		} catch (IOException e) {
			e.printStackTrace();
			retVal = url;
		}

		return retVal;
	}

	public String fetchGoogle(String url) throws IOException {
		String retVal = "";

		try {
			URL u = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) u.openConnection();

			conn.setRequestProperty("User-agent", "Chrome/96.0.4664.110");

			InputStream in = conn.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in, "utf-8");

			BufferedReader bufReader = new BufferedReader(inReader);

			String line = null;
			while ((line = bufReader.readLine()) != null) {
				retVal += line;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public HashMap<String, String> query(String searchKeyword) throws IOException {
		// format searchKeyword
		String[] splitStr = searchKeyword.split(" ");
		if (splitStr.length > 1) {
			String comb = "";
			for (int i = 0; i < splitStr.length; i++) {
				comb += splitStr[i] + "+";
			}
			searchKeyword = comb;
		}
		searchKeyword.trim();

		// to get results that are related to movie
		String lang = "";
		for (int i = 0; i < searchKeyword.length();) {
			int codepoint = searchKeyword.codePointAt(i);
			i += Character.charCount(codepoint);
			if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN) {
				lang = "han";
			} else if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HIRAGANA
					|| Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.KATAKANA) {
				lang = "jp";
			} else if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HANGUL) {
				lang = "kr";
			}
		}
		if (lang.equals("han")) {
			if (!searchKeyword.contains("電影")) {
				searchKeyword += "+電影";
			}
		} else if (lang.equals("kr")) {
			if (!searchKeyword.contains("영화")) {
				searchKeyword += "+영화";
			}
		} else if (lang.equals("jp")) {
			if (!searchKeyword.contains("えいが") || !searchKeyword.contains("映画")) {
				searchKeyword += "+映画";
			}
		} else {
			if (!searchKeyword.contains("movie")) {
				searchKeyword += "+movie";
			}
		}

		// search google
		String url = "http://www.google.com/search?q=" + searchKeyword + "&oe=utf8&num=25";
		System.out.println("url: " + url);

		String content = fetchGoogle(url);
		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");

		for (Element li : lis) {
			try {
				// parse down URL link
				String citeUrl = li.select("a").attr("href"); // System.out.println("origin: " + citeUrl);
				if (citeUrl.startsWith("/url?q=")) {
					citeUrl = citeUrl.replace("/url?q=", "");
				}
				String[] splittedString = citeUrl.split("&sa=");
				if (splittedString.length > 1) {
					citeUrl = splittedString[0];
				}

				// url decoding from UTF-8
				citeUrl = java.net.URLDecoder.decode(citeUrl, StandardCharsets.UTF_8);
				citeUrl.replaceAll(" ", "%20");

				// parse down title
				String title = li.select("a").select(".vvjwJb").text();
				if (title.equals("")) {
					continue;
				}

				System.out.println(title + ", " + citeUrl);
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

}
