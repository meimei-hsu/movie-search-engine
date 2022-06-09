import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class Main {
	public static void main(String[] args) throws IOException {
		WebTree searchResult = new WebTree();
		KeywordList wordList = new KeywordList();
				
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type something and search:");
		String searchKeyword = scanner.nextLine();
		
		searchResult.search(searchKeyword);
		searchResult.setPostOrderScore(wordList.getKeywordList());
		searchResult.eularPrintTree();
		scanner.close();

	}

	static {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}

}