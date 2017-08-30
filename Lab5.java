import java.io.BufferedReader;
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
	private BSTNode<T> left;
	private BSTNode<T> right;
	
	public BSTNode(T value){
		this.data=value;
	}
	
	public T getData() {
        return data;
    }

    public void setData(T value) {
        this.data = value;
    }

    public BSTNode<T> getLeft() {
        return left;
    }

    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    public BSTNode<T> getRight() {
        return right;
    }

    public void setRight(BSTNode<T> right) {
        this.right = right;
    }
    
    
    //CORRECT THIS and make FOR EVERY OBJECT
    
    //@Override
    public boolean equals(BSTNode<T> other) {
    	if(this==null && other==null)
    		return true;
    	if(this.data==other.data && this.left.equals(other.left) && this.right.equals(other.right))
    	{
    		return true;
    	}
    	else
    		return false;
    }
    
	
}

class BST <T extends Comparable<T>> {
	private BSTNode<T> RootNode;
	
	public BST(T rootval) {
		BSTNode<T> RootNode=new BSTNode<T>(rootval);
	}
	
	public void insert(BSTNode<T> root, T val) {
		if(root==null)
		{
			root=new BSTNode<T>(val);
		}
		else if(root.getData().compareTo(val) <= 0)
		{
			insert(root.getLeft(),val);
		}
		else
		{
			insert(root.getRight(),val);
		}
	}
	
	public int findCorrespondingStudent (BSTNode<T> curNode, int pos) {
		if(curNode==null)
		{
			return pos;
		}
		else
		{
			findCorrespondingStudent(curNode.getLeft(),pos);
			pos=pos+1;
			if(curNode.equals(RootNode))
			{
				return pos;
			}
			findCorrespondingStudent(curNode.getRight(),pos);
			return pos;
		}
		
	}
	
	public BSTNode<T> getRoot() {
		return this.RootNode;
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
		for(int i=1; i<KidsNo; i++)
		{
			H.put(i, new ArrayList<Object>() );
		}
		
		for(int i=1; i<=TreeNo; i++)
		{
			//build tree and assign to a kid
			String type=Reader.reader.readLine();
			//BST bst;
			int n=Reader.nextInt(); //number of nodes in a tree
			//an object variable to store the answer of each tree
			Object ans;
			//an integer variable to store the position of the root in inorder traversal
			int childNo=0;
			if(type.equals("Integer"))
			{
				int root=Reader.nextInt();
				ans=root;
				BST<Integer> bst=new BST<Integer>(root);
				for(int ctr=0; ctr<n; ctr++)
				{
					int val=Reader.nextInt();
					bst.insert(bst.getRoot(), val);
					ans=(int) ans + val;
				}
				childNo=bst.findCorrespondingStudent(bst.getRoot(), 0);
			}
			else if(type.equals("Float"))
			{
				float root=//read float from file
				BST<Float> bst=new BST<Float>(root);
				ans= root;
				for(int ctr=0; ctr<n; ctr++)
				{
					float val=//read the float from file
					bst.insert(bst.getRoot(), val);
					ans=(float) ans + val;
				}
				childNo=bst.findCorrespondingStudent(bst.getRoot(), 0);
			}
			else
			{
				String s=Reader.reader.readLine();
				String[] A=s.split(" ");
				BST<String> bst=new BST<String> (A[0]);
				ans=A[0];
				for(int ctr=1; ctr<n; ctr++)
				{
					bst.insert(bst.getRoot(), A[1]);
					ans=ans+A[i];
				}
				childNo=bst.findCorrespondingStudent(bst.getRoot(), 0);
			}
			
			//insert the tree's answer in the Arraylist for that particular child
			ArrayList<Object> o=H.get(childNo);
			
			o.add(ans); //Will this add to the arraylist on hashmap too????
			
		}
		
		int chocoKids=0; //to get the number of kids getting a chocolate
		
		//now for final output display
		for(int k=1; k<KidsNo; k++)
		{
			ArrayList<Object> o=H.get(k);
			if(o.size()==0)
			{
				chocoKids++;
			}
			else
			{
				System.out.print(k+" ");
				for(int i=0; i<o.size(); i++)
				{
					System.out.print(o.get(i)+" ");
				}
				System.out.println();
			}
		}
		System.out.println(chocoKids);
		
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
    
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}