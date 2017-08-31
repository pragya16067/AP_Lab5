//NAME : PRAGYA PRAKASH
//Roll No. : 2016067

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

class BSTNode<T> {
	
	private T data;
	protected BSTNode<T> left;
	protected BSTNode<T> right;
	
	public BSTNode(T value){
		this.data=value;
	}
	
	public T getData() {
        return data;
    }

    public void setData(T value) {
        this.data = value;
    }

    
    //To check if 2 nodes are equal or not
    public boolean equals(BSTNode<T> node,BSTNode<T> other) {
    	//System.out.println(this.getData());
    	if(node==null && other==null)
    		return true;
    	
    	if(node.data==other.data && this.equals(node.left,other.left) && this.equals(node.right,other.right))
    	{
    		return true;
    	}
    	else
    		return false;
    }
    
	
}

class BST <T extends Comparable<T>> {
	protected BSTNode<T> RootNode;
	
	public BST(T rootval) {
		 RootNode=new BSTNode<T>(rootval);
	}
	
	public BSTNode<T> insert(BSTNode<T> root, T val) {
		if(root==null)
		{
			root=new BSTNode<T>(val);
		}
		else if(val.compareTo(root.getData()) <= 0)
		{
		     root.left=insert(root.left,val);
		     
		}
		else
		{
			root.right=insert(root.right,val);
		}
		return root;
		
	}
	
	public void PrintInorder(BSTNode<T> curr) {
		if(curr==null)
		{
			return;
		}
		else
		{
			PrintInorder(curr.left);
			System.out.print(curr.getData()+" ");
			PrintInorder(curr.right);
		}
	}
	
	public int findCorrespondingStudent (BSTNode<T> curNode, int pos) {
		if(curNode==null)
		{
			return pos;
		}
		else
		{
			pos=findCorrespondingStudent(curNode.left,pos);
			if(curNode.equals(RootNode,curNode))
			{
				return pos;
			}
			else
			{
				pos=pos+1;
			}
			
			pos=findCorrespondingStudent(curNode.right,pos);
			return pos;
		}
		
	}
		
	
}

public class Lab5 {

	public static void main(String[] args) throws IOException {
		Reader.init(System.in);
		int TreeNo = Reader.nextInt();
		int KidsNo = Reader.nextInt(); //This is the number of kids as well as the number of nodes in each tree
		
		BSTFilesBuilder builder=new BSTFilesBuilder();
		//Now input files will be created containing information about the trees named 1.txt,2.txt and so on till TreeNo.txt
		builder.createBSTFiles(KidsNo, TreeNo);
		
		HashMap <Integer, ArrayList<Object>> H=new HashMap <Integer, ArrayList<Object>> ();
		//create all keys as roll no of students
		for(int i=1; i<=KidsNo; i++)
		{
			ArrayList<Object> a=new ArrayList<Object> ();
			H.put(i, a);
		}
		
		for(int i=1; i<=TreeNo; i++)
		{
			//Taking input from files
			ReaderFromFile.init("./src/" + i + ".txt");
			//build tree and assign to a kid
			String type=ReaderFromFile.reader.readLine();
			//BST bst;
			int n=ReaderFromFile.nextInt(); //number of nodes in a tree
			//an object variable to store the answer of each tree
			Object ans;
			//an integer variable to store the position of the root in inorder traversal
			int childNo=0;
			if(type.equals("Integer"))
			{
				int root=ReaderFromFile.nextInt();
				ans=root;
				BST<Integer> bst=new BST<Integer>(root);
				for(int ctr=1; ctr<n; ctr++)
				{
					int val=ReaderFromFile.nextInt();
					bst.insert(bst.RootNode, val);
					ans=(int) ans + val;
				}
				//bst.PrintInorder(bst.RootNode);
				//System.out.println();
				childNo=bst.findCorrespondingStudent(bst.RootNode, 1);
			}
			else if(type.equals("Float"))
			{
				float root=ReaderFromFile.nextFloat();
				BST<Float> bst=new BST<Float>(root);
				ans= root;
				for(int ctr=1; ctr<n; ctr++)
				{
					float val=ReaderFromFile.nextFloat();         //read the float from file
					bst.insert(bst.RootNode, val);
					ans=(float) ans + val;
				}
				//bst.PrintInorder(bst.RootNode);
				childNo=bst.findCorrespondingStudent(bst.RootNode, 1);
			}
			else
			{
				String root=ReaderFromFile.next();
				
				BST<String> bst=new BST<String> (root);
				ans=root;
				for(int ctr=1; ctr<n; ctr++)
				{
					String s=ReaderFromFile.next();
					bst.insert(bst.RootNode, s);
					ans=ans+s;
				}
				//bst.PrintInorder(bst.RootNode);
				childNo=bst.findCorrespondingStudent(bst.RootNode, 1);
			}
			
			//insert the tree's answer in the Arraylist for that particular child
			//System.out.println(childNo);
			
			ArrayList<Object> o = H.get(childNo);
			o.add(ans); 
			
			
		}
		
		int chocoKids=0; //to get the number of kids getting a chocolate
		
		//now for final output display
		
		PrintWriter OPwrite = new PrintWriter("./src/Output.txt", "UTF-8");
		for(int k=1; k<=KidsNo; k++)
		{
			ArrayList<Object> o=H.get(k);
			if(o.size()==0)
			{
				chocoKids++;
			}
			else
			{
				OPwrite.print(k+" ");
				for(int i=0; i<o.size(); i++)
				{
					OPwrite.print(o.get(i)+" ");
				}
				OPwrite.println();
			}
		}
		OPwrite.println(chocoKids);
		OPwrite.close();
		
	}
	
}

class BSTFilesBuilder {

	public void createBSTFiles(int numStudents, int numTrees) {
		Random rand = new Random();
		for (int i = 1; i <= numTrees; i++) {
		    try {
				PrintWriter w = new PrintWriter("./src/" + i + ".txt", "UTF-8");
				int type = rand.nextInt(3) + 1;
				if(type == 1) {
					w.println("Integer");
					w.println(numStudents);
					for (int j = 1; j <= numStudents; j++) {
						w.print(rand.nextInt(1000));
						w.print(" ");
					}
				}
				else if(type == 2) {
					w.println("Float");
					w.println(numStudents);
					for (int j = 1; j <= numStudents; j++) {
						w.print(rand.nextFloat()*1000);
						w.print(" ");
					}
				}
				else {
					w.println("String");
					w.println(numStudents);
					String alphabet = "abcdefghijklmnopqrstuvwxyz";
					for (int j = 1; j <= numStudents; j++) {
						int len = rand.nextInt(10)+1;
						for (int k = 0; k < len; k++)
							w.print(alphabet.charAt(rand.nextInt(alphabet.length())));
						w.print(" ");
					}
				}
				w.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


/** Class for buffered reading int and double values */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    
    static float nextFloat() throws IOException {
        return Float.parseFloat( next() );
    }
    
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}

/** Class for buffered reading int and double values */
class ReaderFromFile {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream 
     * @throws FileNotFoundException */
    static void init(String input) throws FileNotFoundException {
        reader = new BufferedReader(
                     new FileReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    
    static float nextFloat() throws IOException {
        return Float.parseFloat( next() );
    }
    
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}