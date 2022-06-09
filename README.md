# [Data Structure] Final Project

### Motivation
Google's results are not just about movies → Create a prioritizing system
We want to build a search engine just for movie lovers or people who wants to search for information related to movies.

### Function
* gives results about "movie" no matter what kind of words you key in
* sorts in different categories and provides you the most related content
* supports four languages - ZH, EN, JP, KR

### System design logic
1. Keyword setting
    Words that means "movie" have the highest weight, 
    words that are about movie have the lowest weight, 
    words that are related to "movie" are in between.
2. Class building
* `KeywordList` creates and stores various preset keywords for page revaluation
* `Keyword` contains two attributes: name, weight
* `GoogleQuery` connects google and parse down HTML contents for page revaluation.
* `CalcScore` call `KeywordList` and `GoogleQuery` to calculate each result webpage's score and reorder the pages by its score.
* `Search` is the search engine starting page in HTML for you to enter your searching keyword
* `ResultPage` presents all the result page in a reorder sequence
* `Main` is the main class and connects all required classes

### Encountered Problems
Mostly IO Exceptions problems.

I. Server response 400 bad request
	add the code segment below to format the target URL
	
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
		
II. Server response 403, 404 bad request
	add the code segment below to handle possible errors

		if (conn.getResponseCode() == 403 || conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
			retVal = url;
			System.out.printf("Error %d: %s\n", conn.getResponseCode(), retVal);
		}
		
III. Java.IO.Exceptions Premature EOF
	replace the buffer reader by the code segment below to prevent bad format of HTML in the target webpage
	
		InputStream in = conn.getInputStream();
		retVal = new String(in.readAllBytes(), StandardCharsets.UTF_8); 
			
IV. Server redirected too many times (20)
	add the segment below before the connection opens to avoid redirecting
	
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();

### Test Plans
We have complete the test plans below and fixed some problems listed above.

√ **Walk-through: **
	fixed some typing errors during this process

√ **Desk checking: **
	be more clear about our design logic during this process

√ **Unit testing: **
	test the methods in `GoogleQuery` and encountered some IO Exception problems

√ **Integration testing: **
	run the whole project on the server and no problems are discovered

√ **Performance test: **
	examine the response time for the proper amount of searching results → the best quality is to run 30 results in one go

√ **Alpha test: **
	use various search keywords to simulate the usage scenarios
