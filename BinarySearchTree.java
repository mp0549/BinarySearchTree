public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    
    /**
     * Fields:
     * protected root node of type BinaryTreeNode<T>
     */
    protected BinaryTreeNode<T> root;

    /**
     * Class constructor
     * initializes root to 0 to make empty tree
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Inserts a new data value into the sorted collection.
     * @param data the new value being inserted
     * @throws NullPointerException if data argument is null, we do not allow
     * null values to be stored within a SortedCollection
     * @return
     */
    @Override
    public void insert(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Data value null!!!!!!!!!");
        }
        if (this.root == null) {
            this.root = new BinaryTreeNode<T>(data);
        }
        else {
            insertHelper(new BinaryTreeNode<T>(data) , this.root);
        }
        
    }
    /**
     * Private helper for insert method
     * @param newNode
     * @param subRoot
     */
    protected void insertHelper(BinaryTreeNode<T> newNode, BinaryTreeNode<T> subRoot) {
        //base case
        if (subRoot == null) return;

        // duplicate

        if (newNode.getData().compareTo(subRoot.getData()) == 0) {
            if (subRoot.childLeft() == null) {
                subRoot.setChildLeft(newNode);
                newNode.setParent(subRoot);
                return;
            }
            newNode.setChildLeft(subRoot.childLeft());
            subRoot.childLeft().setParent(newNode);
            subRoot.setChildLeft(newNode);
            newNode.setParent(subRoot);
        }

        if (newNode.getData().compareTo(subRoot.getData()) < 0) {
            if (subRoot.childLeft() == null) {
                subRoot.setChildLeft(newNode);
                newNode.setParent(subRoot);
                return;
            } 
            
            else {
                insertHelper(newNode, subRoot.childLeft());
            }
        } 
        else if (newNode.getData().compareTo(subRoot.getData()) > 0) {
            if (subRoot.childRight() == null) {
                subRoot.setChildRight(newNode);
                newNode.setParent(subRoot);
                return;
            } 
            
        else {
            insertHelper(newNode, subRoot.childRight());
        }
        }
    }

    /**
     * Check whether data is stored in the tree.
     * @param data the value to check for in the collection
     * @return true if the collection contains data one or more times, 
     * and false otherwise
     */
    @Override
    public boolean contains(Comparable<T> data) {
        return containsHelper(data, this.root);
    }
    /**
     * Private helper for contains method
     * @param data
     * @param subRoot
     * @return
     */
    private boolean containsHelper(Comparable<T> data, BinaryTreeNode<T> subRoot) {
        if (subRoot == null) return false;
        if (subRoot.getData().compareTo((T) data) == 0) return true;
        if (data.compareTo(subRoot.getData()) < 0) {
            return containsHelper(data, subRoot.childLeft());
        } else {
            return containsHelper(data, subRoot.childRight());
        }
    }
    /**
     * Counts the number of values in the collection, with each duplicate value
     * being counted separately within the value returned.
     * @return the number of values in the collection, including duplicates
     */
    @Override
    public int size() {
        return sizeHelper(this.root);
    }
    /**
     * Private helper for size method
     * @param node
     * @return
     */
    private int sizeHelper(BinaryTreeNode<T> node) {
        if (node == null) return 0;
        return (1 + sizeHelper(node.childLeft()) + sizeHelper(node.childRight()));
    }
    /**
     * Checks if the collection is empty.
     * @return true if the collection contains 0 values, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.root == null;
    }
    /**
     * Removes all values and duplicates from the collection.
     */
    @Override
    public void clear() {
        this.root = null;
    }


    
    
   //-------------------------------------------------Testing------------------------------------------------------------------    
  

    /**
     * Test1 tests the functionality of the constructor, isEmpty(), and size() methods of BinarySearchTree
     * @return true if the constructor, isEmpty(), and size() work correctly
     */
   public boolean test1() {
    BinarySearchTree<String> bst =  new BinarySearchTree<>();
    if (bst.root != null) return false;
    if (!bst.isEmpty()) return false;
    if (bst.size() != 0) return false;
    return true;
   }

   /**
    * Test2 tests if insert() of BinarySearchTree throws an error when a null value is attempted
    * @return true if insert() works correctly
    */
   public boolean test2() {
    BinarySearchTree<Integer> bst =  new BinarySearchTree<>();
    
    //attempting to insert a null value to the BST
    try {
        bst.insert(null);
        return false;
    } catch (NullPointerException n) {
        return true;
    }
   }

   /**
    * Test3 tests the functionality of BinarySearchTree's insert()
    * @return true if insert() works correctly
    */
    public boolean test3() {
        BinarySearchTree<Integer> bst =  new BinarySearchTree<>();

        bst.insert(4); //insert new root
        if (!(bst.root.getData() == 4)) return false; // ensuring the root is the correct node
        if (bst.size() != 1) return false; // checking if the size changed to 1
        
        
        bst.insert(2);
        bst.insert(6); // inserting a left child and a right child
        if (!(bst.root.childLeft().getData() == 2)) return false;
        if (!(bst.root.childRight().getData() == 6)) return false; // checking if the respective children are in correct place

        
        bst.insert(1); // inserting a grandchild
        if (!(bst.root.childLeft().childLeft().getData() == 1)) return false; // checking if it's in the right place

        bst.insert(7);
        bst.insert(5); // inserting 2 more grand children to the right subtree
        if (!(bst.root.childRight().childLeft().getData() == 5)) return false;
        if (!(bst.root.childRight().childRight().getData() == 7)) return false; // checking if they're in the right places
        
        
        return true;
    }

    /**
     * Test4 tests if insert() can handle duplicate values
     * @return true if insert() works
     */
    public boolean test4() {
        //setting up bst for testing
        BinarySearchTree<Integer> bst =  new BinarySearchTree<>();
        bst.insert(2);
        bst.insert(1);
        bst.insert(3);

        //adding a duplicate
        bst.insert(3);
        if (!(bst.root.childRight().getData() == 3)) return false;// checkign if original 3 still there
        if (!(bst.root.childRight().childLeft().getData() == 3)) return false; // checking if the duplicate is added to the left


        //adding a duplicate with a child to the left--- should be placed to the left of 2 and 1 is moved down
        bst.insert(2);
        if (!(bst.root.getData() == 2)) return false; // checking if the original 2 is still there
        if (!(bst.root.childLeft().getData() == 2)) return false; // checking if the new duplicate is added to the left
        if (!(bst.root.childLeft().childLeft().getData() == 1)) return false; //checking if the original 2's child is now the duplicate 2's child

    
        return true;
    }

    /**
     * Test5 tests if contains() and size() return the correct values 
     *  also tests if clear() correctly clears the Tree
     * @return true if contians(), size(), and clear() work
     */
    public boolean test5() {
        BinarySearchTree<Integer> bst =  new BinarySearchTree<>();
        bst.insert(2);
        bst.insert(1);
        bst.insert(3);

        if (!bst.contains(1) && !bst.contains(2) && !bst.contains(3) && bst.contains(0)) return false; //testing contains


        if (bst.size() != 3) return false;

        bst.clear();
        if (bst.size() != 0 || bst.root != null) return false;

        return true;
    }
  
  public static void main(String[] args) {
    BinarySearchTree<Integer> faceOfTheOperation = new BinarySearchTree<>();
    
    boolean check1 = faceOfTheOperation.test1();
    boolean check2 = faceOfTheOperation.test2();
    boolean check3 = faceOfTheOperation.test3();
    boolean check4 = faceOfTheOperation.test4();
    boolean check5 = faceOfTheOperation.test5();

    System.out.println("Check1 " + (check1 ? "Passed" : "FAILED"));
    System.out.println("Check2 " + (check2 ? "Passed" : "FAILED"));
    System.out.println("Check3 " + (check3 ? "Passed" : "FAILED"));
    System.out.println("Check4 " + (check4 ? "Passed" : "FAILED"));
    System.out.println("Check5 " + (check5 ? "Passed" : "FAILED"));
  }

  
}