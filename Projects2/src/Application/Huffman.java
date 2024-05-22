package Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.scene.control.TextField;

public class Huffman {
	public static String filename;
	public static HuffmanCode[] huffmancode;
	public static int huffmanSize = 0;
	public static int compSize;
	public static int fileSize;
//--------display header info 
	public static String header(String path, int fileSize, TextField t) throws IOException {
		StringBuilder headerInfo = new StringBuilder();

		String fileName = new File(path).getName();
		headerInfo.append("Original file name: ").append(fileName).append("\n");//file name for original file

		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);//get extension for original file 
		headerInfo.append("Extension: ").append(fileExtension).append('\n');//display extension
		headerInfo.append("Extension Size: ").append(fileExtension.length()).append('\n');//display the extension size

		headerInfo.append("Original file size: ").append(fileSize).append(" bytes\n");//file size for original file

		headerInfo.append("Huffman code" + "\n");//Display Huffman code 
		int sum = 0;
		int Length = 0;
		for (int i = 0; i < huffmanSize; i++) {//get huffman code for each character 
			headerInfo.append(huffmancode[i].getHuffmanCode());
			Length += huffmancode[i].getLength();
			sum += huffmancode[i].Frequency;

		}
		headerInfo.append("\nTotal code length:\n" + Length);// total length for huffman code 
		headerInfo.append("\nHuffman code size:\n"+huffmanSize);//size for huffman code 
		headerInfo.append("\nCompression file size \n" +compSize).append(" bytes\n");//size for compress file 
		Double compersionpercent = ( 1-Double.parseDouble(Integer.toString(compSize)) / Double.parseDouble(Integer.toString(fileSize)))
				* 100;//compression percent  for original file
		t.appendText(Double.toString(compersionpercent) + " %");
		return headerInfo.toString();
	}

//compression file 
//------------------------------------------------------------------------------------------------------------------
	public static void compression(File file) throws Exception {
		// Read File
		String path = file.getPath();
		FileInputStream scanner = new FileInputStream(path);//help to open the original file 
		fileSize = scanner.available();
		byte[] buffer = new byte[512];//buffer help to read original file of size 512 
		int[] frequency = new int[256];//create an array to store the  frequency for each character of size 256
		int size = scanner.read(buffer, 0, 512);
//calculate frequency for each character 
		int index = -1;
		do {
			for (int i = 0; i < size; i++) {
				index = buffer[i];
				if (index < 0) {
					index += 256;
				}
				if (frequency[index] == 0) {
					huffmanSize++;//increment huffman code size 
				}
				frequency[index]++;
			}
			size = scanner.read(buffer, 0, 512);
		} while (size > 0);

		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = 0;
		}
//---------------Write huffman tree and get Huffman code for each character 
		int numberCH = 0;
		int index1 = 0;
		huffmancode = new HuffmanCode[huffmanSize];
		for (int i = 0; i < 256; i++)
			if (frequency[i] != 0) {
				huffmancode[index1++] = new HuffmanCode((char) i, frequency[i]);

				numberCH += frequency[i];
				frequency[i] = 0;

			}

		if (huffmanSize == 1) {
			huffmancode[0].setHuffmanCode("1");
			huffmancode[0].setLength(1);

		} else {
			Node[] node = new Node[huffmanSize];
			Heap heap = new Heap(huffmanSize + 22);//create new heap with huffman code size 
			for (int i = 0; i < huffmanSize; i++) {
				node[i] = new Node(huffmancode[i].getFrequency(), huffmancode[i].getChar());

				heap.insert(node[i]);
			}

			for (int i = 1; i <= node.length - 1; i++) {
				Node z = new Node();//create new node 
				//remove two min node 
				Node x = heap.remove();
				Node y = heap.remove();
				z.frequency = x.frequency + y.frequency;//sum of the x frequency and y frequency 
				z.setLeft(x);//set x to left 
				z.setRight(y);//set y to right 
				heap.insert(z);
			}
			getHuffmanCodes(heap.get()[1], "");

		}

		String[] out = new String[256];

		for (int i = 0; i < huffmanSize; i++) {
			out[(int) huffmancode[i].getChar()] = new String(huffmancode[i].getHuffmanCode());
		}
//--------------Write Header in compress file
		String compressedFileName = newFilename(file);// create new file name and change extension file to huff
		File compressedFile = new File(compressedFileName);
//write file path and size(number charater) for compress file	
		FileOutputStream output = new FileOutputStream(compressedFile);
		byte[] outbuffer = new byte[512];// outbuffer help to Store data compress of size 512 byte
		index1 = 0;

		for (int i = 0; i < path.length(); i++) {
			outbuffer[index1++] = (byte) path.charAt(i);
		}
		outbuffer[index1++] = '\n';

		String numberchar = String.valueOf(numberCH);
		for (int i = 0; i < numberchar.length(); i++) {
			outbuffer[index1++] = (byte) numberchar.charAt(i);
		}
		outbuffer[index1++] = '\n';
//write huffman size for compress file 
		for (int i = 0; i < String.valueOf(huffmanSize).length(); i++) {
			outbuffer[index1++] = (byte) String.valueOf(huffmanSize).charAt(i);
		}
		outbuffer[index1++] = '\n';

		output.write(outbuffer, 0, index1);
		index1 = 0;
		for (int i = 0; i < outbuffer.length; i++) {
			outbuffer[i] = 0;
		}
		for (int i = 0; i < huffmanSize; i++) {
			if (index1 == 512) {
				output.write(outbuffer);
				index1 = 0;
			}
//write Huffman codes in compress file 		
			outbuffer[index1++] = (byte) huffmancode[i].getChar();//generate the  huffman code for each character in the compress file
			if (index1 == 512) {
				output.write(outbuffer);
				index1 = 0;
			}
			outbuffer[index1++] = (byte) huffmancode[i].getLength();//write the length of the huffman code 
			String res = "";
			Long x;
			if (huffmancode[i].getHuffmanCode().length() <= 15) {
				x = Long.parseLong(huffmancode[i].getHuffmanCode());//huffman code convert to a long integer 

			} else {
				for (int z = 0; z < huffmancode[i].getHuffmanCode().length() / 2; z++) {
					res += huffmancode[i].getHuffmanCode().charAt(z) + "";
				}
				x = Long.parseLong(res);
				res = "";
				for (int z = (huffmancode[i].getHuffmanCode().length() + 1) / 2; z < huffmancode[i].getHuffmanCode()
						.length(); z++) {
					res += huffmancode[i].getHuffmanCode().charAt(z) + "";
				}
				x += Long.parseLong(res);
			}
			byte[] code = new byte[50];
			int l = 0;
			if (x == 0) {
				outbuffer[index1++] = 0;
				if (index1 == 512) {
					output.write(outbuffer);
					index1 = 0;
				}

				outbuffer[index1++] = 0;
				if (index1 == 512) {
					output.write(outbuffer);
					index1 = 0;
				}
			} else {
				while (x != 0) {
					if (index1 == 512) {
						output.write(outbuffer);
						index1 = 0;
					}
					code[l++] = (byte) (x % 256);
					x /= 256;
				}
				outbuffer[index1++] = (byte) l;
				if (index1 == 512) {
					output.write(outbuffer);
					index1 = 0;
				}
				for (int j = 0; j < l; j++) {
					outbuffer[index1++] = code[j];
					if (index1 == 512) {
						output.write(outbuffer);
						index1 = 0;
					}
				}
			}

			if (index1 == 512) {
				output.write(outbuffer);
				index1 = 0;
			}
			outbuffer[index1++] = '\n';
		}

		output.write(outbuffer, 0, index1);
//write compress data in compress file 
		for (int i = 0; i < outbuffer.length; i++)
			outbuffer[i] = 0;

		scanner.close();
		scanner = new FileInputStream(path);
		index1 = 0;
		size = scanner.read(buffer, 0, 512);
		do {
			for (int i = 0; i < size; i++) {
				index = buffer[i];
				if (index < 0)
					index += 256;
				for (int j = 0; j < out[index].length(); j++) {
					char ch = out[index].charAt(j);
					if (ch == '1')
						outbuffer[index1 / 8] = (byte) (outbuffer[index1 / 8] | 1 << 7 - index1 % 8);
					index1++;
					if (index1 / 8 == 512) {
						output.write(outbuffer);
						for (int k = 0; k < outbuffer.length; k++)
							outbuffer[k] = 0;
						index1 = 0;
					}
				}
			}
			size = scanner.read(buffer, 0, 512);
		} while (size > 0);
		scanner.close();
		output.write(outbuffer, 0, (index1 / 8) + 1);
		output.close();
		scanner = new FileInputStream(compressedFile);

		compSize = scanner.available();
		scanner.close();

	}

//get Huffman codes for character 
	public static void getHuffmanCodes(Node t, String st) {
		if (t.getLeft() == t.getRight() && t.getRight() == null)
			for (int i = 0; i < huffmanSize; i++) {
				if (huffmancode[i].getChar() == t.getCh()) {
					huffmancode[i].setHuffmanCode(st);
					huffmancode[i].setLength(st.length());
				}
			}
		else {
			getHuffmanCodes(t.getLeft(), st + '0');
			getHuffmanCodes(t.getRight(), st + '1');
		}
	}

//create new file name  
	public static String newFilename(File file) {
		String filename = file.getAbsolutePath();
		String extension = "";

		int index = filename.lastIndexOf('.');
		if (index > 0) {
			extension = filename.substring(index);
			filename = filename.substring(0, index);
		}

		String compressedFileName = filename + ".huff";
		return compressedFileName;
	}
//decompression file 
//------------------------------------------------------------------------------------------------------------------
	public static void deCompression(File infile) {
	    try {
	        int size = 0;
	        String fileName = null;
	        fileName = infile.getPath();
	        int tracker = 0;
	        int bufferTracker = 0;
	        boolean flag = true;
	        String originalFileName = "";
	        File file = new File(fileName);
	        FileInputStream scan = new FileInputStream(file);
	        byte[] buffer = new byte[512];//Store data read in a buffer of size 512
	        size = scan.read(buffer, 0, 512);//read data in compress file 
	        char[] tmp = new char[200];
	        while (flag) {
	            if (buffer[tracker] != '\n')
	                tmp[tracker++] = (char) buffer[bufferTracker++];
	            else
	                flag = false;
	        }
	        bufferTracker++;
	        originalFileName = String.valueOf(tmp, 0, tracker);
	        long nbChar = 0;
	        tracker = 0;
	        flag = true;
	        while (flag) {
	            if (bufferTracker == 512) {
	                size = scan.read(buffer, 0, 512);
	                bufferTracker = 0;
	            }
	            if (buffer[bufferTracker] != '\n')
	                tmp[tracker++] = (char) buffer[bufferTracker++];
	            else
	                flag = false;
	        }
	        nbChar = Integer.parseInt(String.valueOf(tmp, 0, tracker));
	        bufferTracker++;
	        int loopSize = 0;
	        tracker = 0;
	        flag = true;
	        while (flag) {
	            if (bufferTracker == 512) {
	                size = scan.read(buffer, 0, 512);
	                bufferTracker = 0;
	            }
	            if (buffer[bufferTracker] != '\n')
	                tmp[tracker++] = (char) buffer[bufferTracker++];
	            else
	                flag = false;
	        }
	        loopSize = Integer.parseInt(String.valueOf(tmp, 0, tracker));
	        bufferTracker++;
//--------------Read huffman code in compress file 	        
	        huffmancode = new HuffmanCode[loopSize];//store the huffman code in array huffman code 
	        huffmanSize = loopSize;
	        for (int i = 0; i < loopSize; i++) {
	            huffmancode[i] = new HuffmanCode((char) Byte.toUnsignedInt(buffer[bufferTracker++]));
	            if (bufferTracker == 512) {
	                size = scan.read(buffer, 0, 512);
	                bufferTracker = 0;
	            }
	            huffmancode[i].setLength(buffer[bufferTracker++]);
	            if (bufferTracker == 512) {
	                size = scan.read(buffer, 0, 512);
	                bufferTracker = 0;
	            }
	            int l = buffer[bufferTracker++];
	            if (bufferTracker == 512) {
	                size = scan.read(buffer, 0, 512);
	                bufferTracker = 0;
	            }
	            if (l == 0)
	                bufferTracker++;
	            if (bufferTracker == 512) {
	                size = scan.read(buffer, 0, 512);
	                bufferTracker = 0;
	            }
	            long x = 0;
	            for (int j = 0; j < l; j++) {
	                x += Byte.toUnsignedLong(buffer[bufferTracker++]) * (1 << 8 * j);
	                if (bufferTracker == 512) {
	                    size = scan.read(buffer, 0, 512);
	                    bufferTracker = 0;
	                }
	            }
	            huffmancode[i].setHuffmanCode(String.valueOf(x));
	            if (huffmancode[i].getHuffmanCode().length() != huffmancode[i].getLength()) {
	                String s = "";
	                for (int j = 0; j < huffmancode[i].getLength() - huffmancode[i].getHuffmanCode().length(); j++)
	                    s += "0";
	                s += huffmancode[i].getHuffmanCode();
	                huffmancode[i].setHuffmanCode(s);
	            }
	            bufferTracker++;
	            if (bufferTracker == 512) {
	                size = scan.read(buffer, 0, 512);
	                bufferTracker = 0;
	            }
	        }
//------------------Build huffman tree   
	        BinaryTree tree = new BinaryTree();//build huffman tree using the binary tree 
	        for (int i = 0; i < huffmanSize; i++) {//insert huffman code into huffman tree 
	            tree = BinaryTree.insert(tree, huffmancode[i].getHuffmanCode(), 0, huffmancode[i].getChar());
	        }
	        for (int i = 0; i < tmp.length; i++)
	            tmp[i] = '\0';
	        if (bufferTracker == 512) {
	            size = scan.read(buffer, 0, 512);
	            bufferTracker = 0;
	        }
	        int index = bufferTracker;
	        bufferTracker = 0;
	        byte[] outputBuffer = new byte[512];
	        tracker = 0;
	        String name = "";
	        File n = new File(name);
	        if (n.exists())
	            n.delete();
	        FileOutputStream output = new FileOutputStream(originalFileName);
	        BinaryTree root = tree;
	        long count = 0;
	        flag = false;
	        do {
	        	//traversing Huffman tree to decompress data
	            while (tree.getLeft() != null || tree.getRight() != null) {
	                if ((buffer[index] & (1 << 7 - bufferTracker)) == 0)
	                    tree = tree.getLeft();
	                else
	                    tree = tree.getRight();
	                bufferTracker++;
	                if (bufferTracker == 8) {
	                    bufferTracker = 0;
	                    index++;
	                    if (index == 512) {
	                        size = scan.read(buffer, 0, 512);
	                        index = 0;
	                        if (size == -1)
	                            flag = true;
	                    }
	                }
	            }
	            if (flag)
	                break;
	         // Write decompressed character to output buffer
	            outputBuffer[tracker++] = (byte) tree.getCh();
	            if (tracker == 512) {// if it is the size full Write output buffer to file  
	                output.write(outputBuffer);
	                tracker = 0;
	            }
	            count++;
	            tree = root;
	            if (count == nbChar)
	                break;
	        } while (size != -1);
	        output.write(outputBuffer, 0, tracker);
	        output.close();
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}
    
}
