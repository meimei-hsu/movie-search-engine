
public class KeywordList extends KeywordOperator {
	/**
	 * To store provided keywords.
	 */
	public KeywordList() {
		super();

		lst.add(new Keyword("電影", 5));
		lst.add(new Keyword("movie", 5));
		lst.add(new Keyword("好萊塢", 2));
		lst.add(new Keyword("Hollywood", 2));
		lst.add(new Keyword("影城", 3));
		lst.add(new Keyword("影展", 3));
		lst.add(new Keyword("奧斯卡", 3));
		lst.add(new Keyword("Oscars", 3));
		lst.add(new Keyword("金馬獎", 3));
		lst.add(new Keyword("金獅獎", 3));
		lst.add(new Keyword("二輪戲院", 2));

	}
}
