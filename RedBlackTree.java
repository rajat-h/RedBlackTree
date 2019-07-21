import java.util.Stack;

public class RedBlackTree
{
    public static void main(String[] args)
    {            
       RBTree rbt = new RBTree();  
       rbt.insert(10);
       rbt.insert(5);
       rbt.insert(12);
       rbt.insert(7);
       rbt.insert(9);
       rbt.insert(25);
       rbt.insert(24);
       rbt.insert(11);
       rbt.insert(4);
       System.out.println("Red Black Tree:");
       rbt.displayTree();              
    }
}
/* Class Node */
class Node
{    
    Node left, right;
    int iData;
    int color;

    /* Constructor */
    public Node(int key)
    {
        this( key, null, null );
    } 
    /* Constructor */
    public Node(int key, Node lt, Node rt)
    {
        left = lt;
        right = rt;
        iData = key;
        color = 1;
    }    
}

/* Class RBTree */
class RBTree
{
	private Node current;
    private Node parent;
    private Node grand;
    private Node great;
    private Node root;    
    private static Node x;
    static 
    {
        x = new Node(0);
        x.left = x;
        x.right = x;
    }
    //set color values
    static final int BLACK = 1;    
    static final int RED   = 0;

    /* Constructor */
    public RBTree()
    {
        root = new Node(0);
        root.left = x;
        root.right = x;
    }
    
    /* Function to insert data */
    public void insert(int data )
    {
        current = parent = grand = root;
        x.iData = data;
        while (current.iData != data)
        {            
            great = grand; 
            grand = parent; 
            parent = current;
            if(data < current.iData)
            	current =  current.left;
            else
            	current =  current.right;           
            if (current.left.color == RED && current.right.color == RED)
            	adjustAfterInsertion( data );
        }
        if (current != x)
            return;
        current = new Node(data, x, x);
        if (data < parent.iData)
            parent.left = current;
        else
            parent.right = current;        
        adjustAfterInsertion( data );
    }
    private void adjustAfterInsertion(int data)
    {
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if (parent.color == RED)   
        {
            grand.color = RED;
            if (data < grand.iData != data < parent.iData) {
            	if(data < grand.iData) {
                	if(data < grand.left.iData)
                		parent = rotateLeft(grand.left); 
            		else
            			parent = rotateRight(grand.left);
                }
                else {
                	if(data < grand.right.iData)
                		parent = rotateLeft(grand.right);
                	else
                		parent = rotateRight(grand.right);
                } 
            }
            if(data < great.iData) {
            	if(data < great.left.iData)
            		great.left = rotateLeft(great.left); 
        		else
        			great.left = rotateRight(great.left);
            	current = great.left;
            }
            else {
            	if(data < great.right.iData)
            		great.right = rotateLeft(great.right);
            	else
            		great.right = rotateRight(great.right);
            	current = great.right;
            }
            current.color = BLACK;
        }
        root.right.color = BLACK; 
    }      

    /* Rotate node left*/
    private Node rotateLeft(Node n)
    {
        Node m = n.left;
        n.left = m.right;
        m.right = n;
        return m;
    }
    /* Rotate node right*/
    private Node rotateRight(Node n)
    {
        Node m = n.right;
        n.right = m.left;
        m.left = n;
        return m;
    }

    public void displayTree() {
        Stack<Node> globalStack = new Stack<Node>();
        globalStack.push(root.right);
        int nBlanks = 60;
        boolean isRowEmpty = false;
        while (isRowEmpty == false) {
          Stack<Node> localStack = new Stack<Node>();
          isRowEmpty = true;

          for (int j = 0; j < nBlanks; j++) 
        	  System.out.print(' ');

          while (globalStack.isEmpty() == false) {
            Node temp = (Node) globalStack.pop();
            if (temp != x) {
            	char c = 'B';
                if (temp.color == 0)
                    c = 'R';
              System.out.print(temp.iData+""+c);
              localStack.push(temp.left);
              localStack.push(temp.right);

              if (temp.left != x || temp.right != x) isRowEmpty = false;
            } else {
              System.out.print("--");
              localStack.push(null);
              localStack.push(null);
            }
            for (int j = 0; j < nBlanks * 2 - 2; j++) System.out.print(' ');
          } // end while globalStack not empty
          System.out.println();
          nBlanks /= 2;
          while (localStack.isEmpty() == false) globalStack.push(localStack.pop());
        } // end while isRowEmpty is false
      } // end displayTree()
}