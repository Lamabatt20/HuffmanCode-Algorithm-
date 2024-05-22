package Application;

public class Node implements Comparable<Node>{
	int  frequency;
	char ch;
	Node left;
	Node right;
	
	public Node() {
		super();
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public char getCh() {
		return ch;
	}
	public void setCh(char ch) {
		this.ch = ch;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	@Override
	public String toString() {
		return "Node [frequency=" + frequency + ", ch=" + ch + ", left=" + left + ", right=" + right + "]";
	}
	public int  compareTo(Node o) {
		return this.frequency-o.frequency;
	}
	public Node(int frequency, char ch) {
		super();
		this.frequency = frequency;
		this.ch = ch;
	}
	
	

}
