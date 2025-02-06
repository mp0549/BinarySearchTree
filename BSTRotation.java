public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree_Placeholder<T> {
    
     /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException: when either reference is
     * null, or otherwise will throw an IllegalArgumentException.
     *
     * @param child is the node being rotated from child to parent position 
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent
     *     nodes are not initially (pre-rotation) related that way
     */
    protected void rotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent)
        throws NullPointerException, IllegalArgumentException {

            // if either passed argument is null, throw a NullPointerException
            if (child == null || parent == null) {
                throw new NullPointerException("Can't use null values!");
            }

            // right rotation if child is a left child
            if (parent.childLeft() == child) {
                //System.out.println("Right Rotation");
                rightRotate(child , parent);
            }

            // left rotation if child is a right child
            else if (parent.childRight() == child) {
                //System.out.println("Left Rotation");
                leftRotate(child,parent);
            }


            // if the child isn't a left or right child,
            // the parent-child relationship is incorrect
            // so throw an IllegalArgumentException
            else {
                throw new IllegalArgumentException("Illegal Argument!");
            }

            
        
    }

    /**
     * private helper to rotate the tree to the right
     */
    private void rightRotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {
        
        // making child the new parent
        child.setParent(parent.parent());
        if (parent.parent() != null) {
            if (parent.parent().childLeft() == parent) {
                parent.parent().setChildLeft(child);
            } else {
                parent.parent().setChildRight(child);
            }
        }
        else {
            this.root = child;
        }
        parent.setParent(child);

        // moving the children over
        if (child.childRight() != null) {
            parent.setChildLeft(child.childRight());
            child.childRight().setParent(parent);
        } else {
            parent.setChildLeft(null);
        }

        child.setChildRight(parent);
        

    }


    private void leftRotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {
        
        // making child the new parent
        child.setParent(parent.parent());
        if (parent.parent() != null) {
            if (parent.parent().childLeft() == parent) {
                parent.parent().setChildLeft(child);
            } else {
                parent.parent().setChildRight(child);
            }
        }
        else {
            this.root = child;
        }

        parent.setParent(child);

        // moving the children over
        if (child.childLeft() != null) {
            parent.setChildRight(child.childLeft());
            child.childLeft().setParent(parent);
        } else {
            parent.setChildRight(null);
        }

        child.setChildLeft(parent);
        
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * the first tester method checks if the rotate() method throws the correct exceptions
     * @return
     */
    public boolean test1() {
        // bstRotation object to do the actual testing
        BSTRotation<Integer> bst = new BSTRotation<>();
        //constructing tree
        bst.root = new BinaryTreeNode<Integer>(10);
        bst.root.setChildLeft(new BinaryTreeNode<Integer>(5));
        bst.root.setChildRight(new BinaryTreeNode<Integer>(14));
        bst.root.childLeft().setChildLeft(new BinaryTreeNode<Integer>(2));
        
        // NullPointerException
        try {
            bst.rotate(bst.root.childLeft() , null);
            return false;
        } catch (NullPointerException e) {
        
        }

        try {
            bst.rotate(null , bst.root.childRight());
            return false;
        } catch (NullPointerException e) {

        }

        // IllegalArgumentException
        try {
            bst.rotate(bst.root.childLeft() , bst.root.childRight());
            return false;
        } catch (IllegalArgumentException e) {

        }        
        try {
            bst.rotate(bst.root , bst.root);
            return false;
        } catch (IllegalArgumentException e) {

        }

        try {
            bst.rotate(bst.root , bst.root.childLeft());
            return false;
        } catch (IllegalArgumentException e) {

        }                
        
        return true;
    }


    /**
     * Tests rotation() method for rotations not involving the rut node
     * @return True if rotate() works correctly
     */
    public boolean test2() {
        // new BSTRotation object for testing
        BSTRotation<Integer> bst = new BSTRotation<>();

        // constructing the tree
        bst.root = new BinaryTreeNode<Integer>(10);
        bst.root.setChildLeft(new BinaryTreeNode<Integer>(5));
        bst.root.childLeft().setParent(bst.root);
        bst.root.setChildRight(new BinaryTreeNode<Integer>(14));
        bst.root.childRight().setParent(bst.root);;
        bst.root.childLeft().setChildLeft(new BinaryTreeNode<Integer>(2));
        bst.root.childLeft().childLeft().setParent(bst.root.childLeft());
        bst.root.childLeft().setChildRight(new BinaryTreeNode<Integer>(7));
        bst.root.childLeft().childRight().setParent(bst.root.childLeft());

        /*
         * RIGHT ROTATION
         * 
         * before:
         *              10
         *      5               14
         *  2       7       
         * 
         * expected after:
         *                  10
         *      2                   14
         *          5
         *              7
         *  
         */

        bst.rotate(bst.root.childLeft().childLeft() , bst.root.childLeft());

        // checking if all the nodes went to the right spots and if the parent and child assignments got updated
        if (bst.root.getData() != 10) return false;
        if (bst.root.childLeft().getData() != 2 || bst.root.childLeft().parent().getData() != 10) return false;
        if (bst.root.childLeft().childLeft() != null || bst.root.childLeft().childRight().getData() != 5) return false;
        if (bst.root.childLeft().childRight().getData() != 5 || bst.root.childLeft().childRight().parent().getData() != 2) return false;
        if (bst.root.childLeft().childRight().childLeft() != null || bst.root.childLeft().childRight().childRight().getData() != 7) return false;
        if (bst.root.childLeft().childRight().childRight().childLeft()  != null || bst.root.childLeft().childRight().childRight().childRight() != null) return false;
        
        /*
         * LEFT ROTATION
         * 
         *  before:
         *                  10
         *      2                   14
         *          5
         *              7
         *  expected after:
         *              10
         *      5               14
         *  2       7        
         */

        bst.rotate(bst.root.childLeft().childRight() , bst.root.childLeft());
        
        // checking if all the nodes went to the right spots and if the parent and child assignments got updated
        if (bst.root.getData() != 10) return false;        
        if (bst.root.childLeft().getData() != 5 || bst.root.childLeft().parent().getData() != 10) return false;
        if (bst.root.childLeft().childLeft().getData() != 2 || bst.root.childLeft().childRight().getData() != 7) return false;
        if (bst.root.childLeft().childRight().getData() != 7 || bst.root.childLeft().childRight().parent().getData() != 5) return false;
        if (bst.root.childLeft().childRight().childLeft() != null || bst.root.childLeft().childRight().childRight() != null) return false;
        if (bst.root.childLeft().childLeft().childLeft() != null || bst.root.childLeft().childLeft().childRight() != null) return false;

        return true;
    }

    /**
     * Tests rotation() for rotations involving the rut node
     * @return True if rotate() works correctly
     */
    public boolean test3() {
        // new BSTRotation object for testing
        BSTRotation<Integer> bst = new BSTRotation<>();

        // constructing the tree
        bst.root = new BinaryTreeNode<Integer>(10);
        bst.root.setChildLeft(new BinaryTreeNode<Integer>(5));
        bst.root.childLeft().setParent(bst.root);
        bst.root.setChildRight(new BinaryTreeNode<Integer>(14));
        bst.root.childRight().setParent(bst.root);;
        bst.root.childLeft().setChildLeft(new BinaryTreeNode<Integer>(2));
        bst.root.childLeft().childLeft().setParent(bst.root.childLeft());
        bst.root.childLeft().setChildRight(new BinaryTreeNode<Integer>(7));
        bst.root.childLeft().childRight().setParent(bst.root.childLeft());

        /*
         * RIGHT ROTATION
         * 
         *  before:
         *              10
         *      5               14
         *  2       7       
         * 
         * expected after:
         *              5
         *      2               10
         *                  7       14    
         *  
         */

        bst.rotate(bst.root.childLeft() , bst.root);

        if (bst.root.getData() != 5) return false;
        if (bst.root.childLeft().getData() != 2) return false;
        if (bst.root.childRight().getData() != 10) return false;
        if (bst.root.childRight().childLeft().getData() != 7) return false;
        if (bst.root.childRight().childRight().getData() != 14) return false;


        /**
         * LEFT ROTATION
         * 
         * before:
         *              5
         *      2               10
         *                  7       14
         * 
         * expected after:
         *                  10
         *          5               14
         *      2       7
         */

        
        bst.rotate(bst.root.childRight() , bst.root);
        
        // checking if all the nodes went to the right spots
        if (bst.root.getData() != 10) return false;
        if (bst.root.childRight().getData() != 14) return false;
        if (bst.root.childLeft().getData() != 5) return false;
        if (bst.root.childLeft().childLeft().getData() != 2) return false;
        if (bst.root.childLeft().childRight().getData() != 7) return false;


        return true;
        
    }

    public static void main(String[] args) {
        BSTRotation<Integer> faceOfTheOperations= new BSTRotation<>();
        System.out.println("Test 1 " + (faceOfTheOperations.test1() ? "passes" : "fails"));
        System.out.println("Test 2 " + (faceOfTheOperations.test2() ? "passes" : "fails"));
        System.out.println("Test 3 " + (faceOfTheOperations.test3() ? "passes" : "fails"));
    }

    
}
