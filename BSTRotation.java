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
                rightRotate(child , parent);
            }

            // left rotation if child is a right child
            else if (parent.childRight() == child) {
                leftRotate(child,parent);
            }


            // if the child isn't a left or right child,
            // the parent-child relationship is incorrect
            // so throw an IllegalArgumentException
            else {
                throw new IllegalArgumentException("Illegal Argument!");
            }

            
        
    }

    private void rightRotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {
        
        child.setParent(parent.parent());
        if (parent.parent() != null) {
            if (parent.parent().childLeft() == parent) {
                parent.parent().setChildLeft(child);
            } else {
                parent.parent().setChildRight(child);
            }
        }
        parent.setParent(child);

        if (child.childRight() != null) {
            parent.setChildLeft(child.childRight());
            child.childRight().setParent(parent);
        } else {
            parent.setChildLeft(null);
        }

        child.setChildRight(parent);
        

    }


    private void leftRotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {

        child.setParent(parent.parent());
        if (parent.parent() != null) {
            if (parent.parent().childLeft() == parent) {
                parent.parent().setChildLeft(child);
            } else {
                parent.parent().setChildRight(child);
            }
        }
        
        parent.setParent(child);

        if (parent.parent() != null) {
            if (parent.parent().childLeft() == parent) {
                parent.parent().setChildLeft(child);
            } else {
                parent.parent().setChildRight(child);
            }
        }
        

        child.setChildLeft(parent);
        
    }


    public boolean test1() {
        BSTRotation<Integer> bstRotation = new BSTRotation<>();

        BinarySearchTree_Placeholder<Integer> bst = new BinarySearchTree_Placeholder<>();
        bst.root = new BinaryTreeNode<Integer>(10);
        bst.root.setChildLeft(new BinaryTreeNode<Integer>(5));
        bst.root.setChildRight(new BinaryTreeNode<Integer>(14));
        bst.root.childLeft().setChildLeft(new BinaryTreeNode<Integer>(2));
        
        try {
            bstRotation.rotate(bst.root.childLeft() , null);
            return false;
        } catch (NullPointerException e) {
        
        }

        try {
            bstRotation.rotate(null , bst.root.childRight());
            return false;
        } catch (NullPointerException e) {

        }

        try {
            bstRotation.rotate(bst.root.childLeft() , bst.root.childRight());
            return false;
        } catch (NullPointerException e) {

        }
        
        
        return true;
    }

    public boolean test2() {
        
        
        return true;
    }

    public boolean test3() {
        
        
        return true;
    }

    public static void main(String[] args) {
        BSTRotation<Integer> faceOfTheOperations= new BSTRotation<>();
        System.out.println("Test 1 " + (faceOfTheOperations.test1() ? "passes" : "fails"));
        System.out.println("Test 2 " + (faceOfTheOperations.test2() ? "passes" : "fails"));
        System.out.println("Test 3 " + (faceOfTheOperations.test3() ? "passes" : "fails"));
    }

    
}
