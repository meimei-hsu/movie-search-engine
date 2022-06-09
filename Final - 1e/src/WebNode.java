
import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	/**
	 * To define a web tree child.
	 */
	private WebNode parent;
	private ArrayList<WebNode> children;
	private WebPage webPage;
	private double nodeScore;

	public WebNode(WebPage webPage) {
		this.parent = null;
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}

	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		// this method should be called in post-order mode

		// compute webPage score
		webPage.setScore(keywords);
		nodeScore = webPage.getWebScore();

		// nodeScore += all children¡¦s nodeScore
		for (WebNode child : children) {
			nodeScore += child.nodeScore;
		}
	}

	public void addChild(WebNode child) {
		// add the WebNode to its children list
		this.children.add(child);
		child.parent = this;
	}

	public boolean isTheLastChild() {
		if (this.parent == null)
			return true;

		ArrayList<WebNode> siblings = this.parent.children;
		return this.equals(siblings.get(siblings.size() - 1));
	}

	public int getDepth() {
		int retVal = 1;
		WebNode currNode = this;
		while (currNode.parent != null) {
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
	
	public WebNode getParent() {
		return parent;
	}
	
	public ArrayList<WebNode> getChildren() {
		return children;
	}
	
	public WebPage getWebPage() {
		return webPage;
	}
	
	public double getNodeScore() {
		return nodeScore;
	}
}
