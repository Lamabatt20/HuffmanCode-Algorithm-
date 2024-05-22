package Application;

public class BinaryTree {
	char ch;
	String huffCode;
	BinaryTree left;
	BinaryTree right;
	
	public BinaryTree() {
		ch = '\0';
		huffCode = "";
		left = right = null;

	}
	
	public BinaryTree(char ch, String huffCode) {
		super();
		this.ch = ch;
		this.huffCode = huffCode;
		
	}

	public static BinaryTree insert(BinaryTree tree, String st, int ind, char ch) {
		if (ind < st.length()) {

			if (st.charAt(ind) == '0') {
				if (tree.left == null)
					tree.left = new BinaryTree();
				tree.left = insert(tree.left, st, ind + 1, ch);
			} else {
				if (tree.right == null)
					tree.right = new BinaryTree();
				tree.right = insert(tree.right, st, ind + 1, ch);
			}
			return tree;
		} else {
			tree.ch = ch;
			return tree;
		}

	}
	

	public char getCh() {
		return ch;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}

	public String getHuffCode() {
		return huffCode;
	}

	public void setHuffCode(String huffCode) {
		this.huffCode = huffCode;
	}

	public BinaryTree getLeft() {
		return left;
	}

	public void setLeft(BinaryTree left) {
		this.left = left;
	}

	public BinaryTree getRight() {
		return right;
	}

	public void setRight(BinaryTree right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "BinaryTree [ch=" + ch + ", huffCode=" + huffCode + ", left=" + left + ", right=" + right + "]";
	}
	
	

}
