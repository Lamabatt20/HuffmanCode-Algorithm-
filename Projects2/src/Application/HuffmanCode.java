package Application;

public class HuffmanCode implements Comparable<HuffmanCode> {
	char Char;
	int Frequency;
	String huffmanCode;
	int Length;
	int ASCII;
	
	


	public HuffmanCode(char character) {
		super();
		this.Char = character;
	}

	public HuffmanCode(char c, int frequency, String huffCode, int length,int ascii) {
		super();
		Char = c;
		Frequency = frequency;
		huffmanCode = huffCode;
		Length = length;
		ASCII=ascii;
		
	}
	public HuffmanCode(char character, int frequency) {
		super();
		this.Char = character;
		this.Frequency = frequency;
	}

	public HuffmanCode(char charValue, String string) {
		this.Char=charValue;
		this.huffmanCode=string;
	}

	public char getChar() {
		return Char;
	}



	public void setChar(char Char) {
		this.Char = Char;
	}



	public int getFrequency() {
		return Frequency;
	}



	public void setFrequency(int frequency) {
		this.Frequency = frequency;
	}



	


	public int getLength() {
		return Length;
	}



	public void setLength(int Length) {
		this.Length = Length;
	}
	


	@Override
	public int compareTo(HuffmanCode o) {
		
		return this.huffmanCode.compareTo(o.huffmanCode);
	}
	@Override
	public String toString() {
		return "HuffmanCode [Char=" + Char + ", Frequency=" + Frequency + ", HuffmanCode=" + huffmanCode + ", Length="
				+ Length+", ASCII="
						+ ASCII ;
	}
	public String getHuffmanCode() {
		return huffmanCode;
	}
	public void setHuffmanCode(String huffmanCode) {
		this.huffmanCode = huffmanCode;
	}

	public int getASCII() {
		return ASCII;
	}
	public void setASCII(int  ASCII) {
		this.ASCII = ASCII;
	}
	
	
	
	

}
