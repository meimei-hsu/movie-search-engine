
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WebTree {
	/**
	 * To define a website and it's subpages.
	 */
	private WebNode root;
	private WebContent content;

	public WebTree()  {
		content = new WebContent();
	}

	public void search(String searchKeyword) throws IOException {
		// To setup default website to browse, and build up the tree with it's sublinks.
		root = new WebNode(
				new WebPage("http://www.google.com/search?q=" + searchKeyword + "+movie"+ "&oe=utf8&num=1", "Google", content));

		HashMap<String, String> sublinks = new HashMap<String, String>();
		sublinks = content.google(root.getWebPage().getWebUrl());

		for (String s : sublinks.keySet()) {
			root.addChild(new WebNode(new WebPage(sublinks.get(s), s, content)));
		}
	}

	public void setRoot(WebNode root) {
		this.root = root;
	}

	public WebNode getTree() {
		return root;
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
		// compute the score of children nodes in post-order
		for (WebNode child : startNode.getChildren()) {
			setPostOrderScore(child, keywords);
		}

		startNode.setNodeScore(keywords);
		System.out.println(startNode.getWebPage().getWebName() + startNode.getWebPage().getWebScore());
		System.out.println(startNode.getNodeScore());
	}

	public void eularPrintTree() {
		eularPrintTree(root);
	}

	private void eularPrintTree(WebNode startNode) {
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));
		// print "("
		System.out.print("(");
		// print "name","score"
		System.out.print(startNode.getWebPage().getWebName() + "," + startNode.getNodeScore());
 
		// print child in pre-order
		for (WebNode child : startNode.getChildren()) {
			eularPrintTree(child);
		}

		// print ")"
		System.out.print(")");

		// if is the last child, return to the next line's previous position
		if (startNode.isTheLastChild())
			System.out.print("\n" + repeat("\t", nodeDepth - 2));
	}

	private String repeat(String str, int repeat) {
		String retVal = "";
		for (int i = 0; i < repeat; i++) {
			retVal += str;
		}
		return retVal;
	}
	
	public int size() {
		if(root==null) {
			return 0;
		} else {
			return (root.getChildren().size() + 1);
		}
	}
}
