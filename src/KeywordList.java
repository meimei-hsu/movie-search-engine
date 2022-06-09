import java.util.ArrayList;

public class KeywordList {
	/**
	 * To store provided keywords.
	 */

	private ArrayList<Keyword> lst;

	public KeywordList() {
		lst = new ArrayList<Keyword>();
	}

	public String filter(String input) {
		// to rebuild the keyword list by user's option
		String s = "電影";
		lst.clear();
		switch (input) {
		case "romance":
			romance();
			s = "愛情/浪漫";
			break;
		case "fantasy":
			fantasy();
			s = "奇幻/科幻";
			break;
		case "action":
			action();
			s = "動作/冒險";
			break;
		case "comedy":
			comedy();
			s = "喜劇";
			break;
		case "thriller":
			s = "懸疑/驚悚";
			thriller();
			break;
		default:
			movie();
			break;
		}
		return s;
	}

	public void movie() {
		lst.add(new Keyword("電影", 3));
		lst.add(new Keyword("えいが", 3));
		lst.add(new Keyword("映画", 3));
		lst.add(new Keyword("영화", 3));
		lst.add(new Keyword("影展", 2));
		lst.add(new Keyword("票房", 2));
		lst.add(new Keyword("爛番茄", 2));
		lst.add(new Keyword("大螢幕", 2));
		lst.add(new Keyword("好萊塢", 1));
		lst.add(new Keyword("奧斯卡", 1));
		lst.add(new Keyword("金馬獎", 1));
		lst.add(new Keyword("鉅座", 1));
		lst.add(new Keyword("演", 1));
		lst.add(new Keyword("客串", 1));
		lst.add(new Keyword("劇情", 1));
		lst.add(new Keyword("故事", 1));

		lst.add(new Keyword("movie", 3));
		lst.add(new Keyword("cinema", 2));
		lst.add(new Keyword("theatre", 2));
		lst.add(new Keyword("the big screen", 2));
		lst.add(new Keyword("film", 2));
		lst.add(new Keyword("box office", 2));
		lst.add(new Keyword("IMDB", 2));
		lst.add(new Keyword("rotten tomatoes", 2));
		lst.add(new Keyword("trailer", 2));
		lst.add(new Keyword("Hollywood", 1));
		lst.add(new Keyword("Oscars", 1));
		lst.add(new Keyword("blockbuster", 1));
		lst.add(new Keyword("soundtrack", 1));
		lst.add(new Keyword("director", 1));
		lst.add(new Keyword("stars", 1));
		lst.add(new Keyword("cameo", 1));
		lst.add(new Keyword("drama", 1));
		lst.add(new Keyword("plot", 1));
		lst.add(new Keyword("acting", 1));
	}

	public void romance() {
		lst.add(new Keyword("浪漫", 15));
		lst.add(new Keyword("ロマンチック", 15));
		lst.add(new Keyword("로맨틱", 15));
		lst.add(new Keyword("愛", 15));
		lst.add(new Keyword("멜로", 15));
		lst.add(new Keyword("로맨스", 15));
		lst.add(new Keyword("恋愛", 15));
		lst.add(new Keyword("れんあい", 15));
		lst.add(new Keyword("好きです", 10));
		lst.add(new Keyword("情", 10));
		lst.add(new Keyword("喜歡", 10));
		lst.add(new Keyword("戀人", 1));
		lst.add(new Keyword("兩性", 1));
		lst.add(new Keyword("婚", 1));
		lst.add(new Keyword("嫁", 1));
		lst.add(new Keyword("娶", 1));
		lst.add(new Keyword("吻", 1));
		lst.add(new Keyword("慾望", 1));

		lst.add(new Keyword("romance", 15));
		lst.add(new Keyword("romantic", 15));
		lst.add(new Keyword("affection", 10));
		lst.add(new Keyword("love", 10));
		lst.add(new Keyword("sexual", 1));
		lst.add(new Keyword("lover", 1));
		lst.add(new Keyword("couple", 1));
		lst.add(new Keyword("relationship", 1));
		lst.add(new Keyword("marry", 1));
		lst.add(new Keyword("mariage", 1));
		lst.add(new Keyword("forever", 1));
		lst.add(new Keyword("desire", 1));
	}

	public void fantasy() {
		lst.add(new Keyword("奇幻", 15));
		lst.add(new Keyword("科幻", 15));
		lst.add(new Keyword("SF", 15));
		lst.add(new Keyword("科幻", 15));
		lst.add(new Keyword("星際", 1));
		lst.add(new Keyword("太空", 1));
		lst.add(new Keyword("外星", 1));
		lst.add(new Keyword("宇宙", 1));
		lst.add(new Keyword("地球", 1));
		lst.add(new Keyword("時間旅行", 1));
		lst.add(new Keyword("機器", 1));
		lst.add(new Keyword("妖", 1));
		lst.add(new Keyword("怪物", 1));
		lst.add(new Keyword("魔", 1));
		lst.add(new Keyword("法師", 1));
		lst.add(new Keyword("巫", 1));
		lst.add(new Keyword("童話", 1));
		lst.add(new Keyword("神", 1));
		lst.add(new Keyword("鬼", 1));
		lst.add(new Keyword("龍", 1));

		lst.add(new Keyword("fantasy", 15));
		lst.add(new Keyword("sci-fi", 15));
		lst.add(new Keyword("science fiction", 15));
		lst.add(new Keyword("alien", 1));
		lst.add(new Keyword("space travel", 1));
		lst.add(new Keyword("time travel", 1));
		lst.add(new Keyword("robot", 1));
		lst.add(new Keyword("monster", 1));
		lst.add(new Keyword("magic", 1));
		lst.add(new Keyword("power", 1));
		lst.add(new Keyword("sorcerer", 1));
		lst.add(new Keyword("imagination", 1));
		lst.add(new Keyword("fairy", 1));
		lst.add(new Keyword("tale", 1));
		lst.add(new Keyword("myth", 1));
		lst.add(new Keyword("god", 1));
	}

	public void action() {
		lst.add(new Keyword("動作", 15));
		lst.add(new Keyword("アクション", 15));
		lst.add(new Keyword("액션", 15));
		lst.add(new Keyword("冒險", 15));
		lst.add(new Keyword("アドベンチャー", 15));
		lst.add(new Keyword("모험", 15));
		lst.add(new Keyword("戰爭", 1));
		lst.add(new Keyword("諜", 1));
		lst.add(new Keyword("武", 1));
		lst.add(new Keyword("特效", 1));
		lst.add(new Keyword("特技", 1));
		lst.add(new Keyword("賽車", 1));
		lst.add(new Keyword("飛車", 1));

		lst.add(new Keyword("action", 15));
		lst.add(new Keyword("adventure", 15));
		lst.add(new Keyword("war", 1));
		lst.add(new Keyword("military", 1));
		lst.add(new Keyword("spy", 1));
		lst.add(new Keyword("martial art", 1));
		lst.add(new Keyword("blast effect", 1));
		lst.add(new Keyword("special effect", 1));
	}

	public void comedy() {
		lst.add(new Keyword("喜劇", 15));
		lst.add(new Keyword("きげき", 15));
		lst.add(new Keyword("コメディ", 15));
		lst.add(new Keyword("코미디", 15));
		lst.add(new Keyword("笑", 1));
		lst.add(new Keyword("幽默", 1));

		lst.add(new Keyword("comedy", 15));
		lst.add(new Keyword("sitcom", 5));
		lst.add(new Keyword("romcom", 5));
		lst.add(new Keyword("funny", 1));
		lst.add(new Keyword("hilarious", 1));
		lst.add(new Keyword("humor", 1));
	}

	public void thriller() {
		lst.add(new Keyword("懸疑", 15));
		lst.add(new Keyword("서스펜스", 15));
		lst.add(new Keyword("サスペンス", 15));
		lst.add(new Keyword("驚悚", 15));
		lst.add(new Keyword("스릴러", 15));
		lst.add(new Keyword("スリラー", 15));
		lst.add(new Keyword("恐怖", 15));
		lst.add(new Keyword("호러", 15));
		lst.add(new Keyword("공포", 15));
		lst.add(new Keyword("ホラー", 15));
		lst.add(new Keyword("燒腦", 1));
		lst.add(new Keyword("推理", 1));
		lst.add(new Keyword("謀殺", 1));
		lst.add(new Keyword("犯罪", 1));
		lst.add(new Keyword("殺人", 1));
		lst.add(new Keyword("靈異", 1));
		lst.add(new Keyword("駭人", 1));
		lst.add(new Keyword("鬼片", 1));
		lst.add(new Keyword("顫慄", 1));
		lst.add(new Keyword("嚇", 1));
		lst.add(new Keyword("毛骨悚然", 1));

		lst.add(new Keyword("thriller", 15));
		lst.add(new Keyword("suspense", 15));
		lst.add(new Keyword("horror", 15));
		lst.add(new Keyword("murder", 1));
		lst.add(new Keyword("detective", 1));
		lst.add(new Keyword("crime", 1));
		lst.add(new Keyword("supernatural", 1));
		lst.add(new Keyword("terrify", 1));
		lst.add(new Keyword("horrify", 1));
	}

	public ArrayList<Keyword> getKeywordList() {
		return lst;
	}

	public void add(Keyword k) {
		lst.add(k);
	}

	public void remove(Keyword k) {
		lst.remove(lst.indexOf(k));
	}
}
