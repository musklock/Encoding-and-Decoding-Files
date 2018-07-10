/**
 * Muskaan Mendiratta
 * Class ID 94
 * I did not collaborate with anyone on this assignment
 */

//packages imported
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.PriorityQueue;

//Node class
class Node implements Comparable<Node>{

	int data;
	char c;
	Node left;
	Node right;

	//constructor
	public Node(int item, char c) {
		this.c=c;
		this.data=item;
		left=right=null;
	}
	//constructor
	public Node(int item) {
		this.data=item;
		left=right=null;
	}
	//get character
	public char getCharValue() {
		return c;
	}
	//get integer
	public int getIntegerValue() {
		return data;
	}

	@Override
	public int compareTo(Node o) {
		return this.data-o.data;
	}

}
public class HuffmanSubmit implements Huffman {

	HashMap<Character, Integer> frequency;		//stores character and its corresponding frequency
	HashMap <Character, String> mapForCodes;	//stores huffman codes for each character
	HashMap<String,Character> mapForCodesSwapped;	//same as mapForCodes, except the key, value pairs are swapped
	int count=0;

	public HuffmanSubmit() {
		//initializing
		frequency=new HashMap<>();
		mapForCodes=new HashMap<>();
		mapForCodesSwapped=new HashMap<>();
	}

	/**
	 * method for encoding the file. Takes the input file, gets frequency of each character
	 * in the input file. Stores the frequency of the characters in a hashmap called 
	 * frequency. Calls the function makePriorityQueue() on the keys in the frequency key set,
	 * and codeTheTree(Node root). Finally, it writes the huffman codes to the output file
	 * by converting the strings into booleans.  
	 */
	public void encode(String inputFile, String outputFile, String freqFile){
		BinaryIn in;
		BinaryOut out;

		out=new BinaryOut(outputFile);
		in=new BinaryIn(inputFile);
		while (!in.isEmpty()) {
			char c=in.readChar();

			frequency.put(c, 0);
		}
		in=new BinaryIn(inputFile);

		while (!in.isEmpty()) {
			char c=in.readChar();
			count+=1;
			//putting chars and their frequencies to the frequency hashmap
			frequency.put(c, frequency.get(c)+1);

		}	

		//writing the frequency in the output file 
		try {
			PrintWriter printWriter=new PrintWriter(freqFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedWriter outputWriter=null;
		try {
			outputWriter =new BufferedWriter (new FileWriter(freqFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (char key:frequency.keySet()) {
			try {
				String s=Integer.toBinaryString(key);
				while (s.length()<8) {
					s="0"+s;
				}
				outputWriter.write(s+":"+frequency.get(key));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		try {
			outputWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outputWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Node root=makePriorityQueue();
		codeTheTree(root,"");

		in=new BinaryIn(inputFile);	
		out.write(count);
		while (!in.isEmpty()) {
			char c=in.readChar();
			//get the binary code
			String binary=mapForCodes.get(c);
			//get each 0 or 1 in the binary code
			char[] bin=binary.toCharArray();			

			for (char ch:bin) {
				if (ch=='0') {
					out.write(false);
				}else if (ch=='1') {
					out.write(true);
				}
			}

		}
		out.flush();
	}


	public void codeTheTree(Node root, String string) {
		if (root.left==null && root.right==null) {
			mapForCodes.put(root.getCharValue(), string);
			mapForCodesSwapped.put(string, root.getCharValue());
			return;
		}
		codeTheTree(root.left, string+"0");
		codeTheTree(root.right, string+"1");
	}

	public Node makePriorityQueue() {
		PriorityQueue<Node> p=new PriorityQueue<>();
		for (char key: this.frequency.keySet()) {
			Node node=new Node(frequency.get(key),key);
			p.offer(node);
		}
		while (p.size()>1) {
			Node node1=p.poll();
			Node node2=p.poll();

			Node newNode=new Node(node1.getIntegerValue()+node2.getIntegerValue());
			newNode.left=node1;
			newNode.right=node2;
			p.offer(newNode);
		}
		return p.poll();
	}


	/**
	 * takes the encoded file as input along with the frequency file, makes a binary tree
	 * and assigns huffman codes again, and puts them in a map. Decodes the encoded file
	 * by checking the values and getting the decoded characters from the map.  
	 */
	public void decode(String inputFile, String outputFile, String freqFile){

		BinaryIn in=new BinaryIn(inputFile);
		BinaryOut out=new BinaryOut(outputFile);
		HashMap<Character,Integer> map=new HashMap<>();
		//read the freq file using buffered reader

		BufferedReader br = null;
		FileReader fr = null;

		try {


			fr = new FileReader(freqFile);
			br = new BufferedReader(fr);
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				
				String[] array=sCurrentLine.split(":");
				map.put((char)Integer.parseInt(array[0],2), Integer.parseInt(array[1]));
				
			}
			frequency=map;
			mapForCodes=new HashMap<>();
			codeTheTree(makePriorityQueue(),"");

			in=new BinaryIn(inputFile);
			String str="";
			boolean a = true;
			int newCount=0;
			int size=in.readInt();

			while(newCount<size) {


				while (!this.mapForCodesSwapped.containsKey(str)) {
					a = in.readBoolean();
					if(a==true) {
						str= str+"1";
					
					}else if(a==false) {
						str=str+"0";
					}

				}

				out.write(mapForCodesSwapped.get(str));
				str="";
				newCount+=1;
			}
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public static void main(String[] args) {
		Huffman  huffman = new HuffmanSubmit();

		huffman.encode("ur.jpg", "ur.enc", "freq.txt");

		huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");

	}

}
