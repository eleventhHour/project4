/**
 * BSTCounter implements the DataCounter interface using a binary search tree to
 * store the data items and counts.
 *
 * @param <E> The type of the data elements. Note that we have strengthened the
 *            constraints on E such that E is now a Comparable.
 */
public class BinarySearchTree<E extends Comparable<? super E>> implements
        DataCounter<E> {

    /**
     * The root of the binary search tree. root is null if and only if the tree
     * is empty.
     */
    protected BSTNode overallRoot;

    /**
     * Number of nodes in the binary search tree.
     */
    protected int size;

    /**
     * Inner (non-static) class to represent a node in the tree. Each node
     * includes a String and an integer count. The class is protected so that it
     * may be accessed by subclasses of BSTCounter.
     */
    protected class BSTNode {
        /**
         * The left child of this node.
         */
        public BSTNode left;

        /**
         * The right child of this node.
         */
        public BSTNode right;

        /**
         * The data element stored at this node.
         */
        public E data;

        /**
         * The count for this data element.
         */
        public int count;

        /**
         * Create a new data node. Also takes care of incrementing the tree
         * size.
         *
         * @param data data element to be stored at this node.
         */
        public BSTNode(E data) {
            this.data = data;
            count = 1;
            left = right = null;
            //size++; Move tree size increment down in insertion function
        }
    }

    /**
     * Create an empty binary search tree.
     */
    public BinarySearchTree() {
        overallRoot = null;
        size = 0;
    }

    /** {@inheritDoc} */
    public void incCount(E data) {
        if (overallRoot == null) {
            overallRoot = new BSTNode(data);
            size++;
        } else {
            // traverse the tree
            BSTNode currentNode = overallRoot;
            while (true) {

                // compare the data to be inserted with the data at the current
                // node
                int cmp = data.compareTo(currentNode.data);

                if (cmp == 0) {
                    // current node is a match
                    currentNode.count++;
                    return;
                } else if (cmp < 0) {
                    // new data goes to the left of the current node
                    if (currentNode.left == null) {
                        currentNode.left = new BSTNode(data);
                        size++;
                        return;
                    }
                    currentNode = currentNode.left;
                } else {
                    // new data goes to the right of the current node
                    if (currentNode.right == null) {
                        currentNode.right = new BSTNode(data);
                        size++;
                        return;
                    }
                    currentNode = currentNode.right;
                }
            }
        }
    }

    /** {@inheritDoc} */
    public int getSize() {
        return size;
    }

    /** {@inheritDoc} */
    public DataCount<E>[] getCounts() {
    	@SuppressWarnings("unchecked")
        DataCount<E>[] counts = new DataCount[size];
        if (overallRoot != null)
            traverse(overallRoot, counts, 0);
        return counts;
    }

    /**
     * Do an inorder traversal of the tree, filling in an array of DataCount
     * objects with the count of each element. Doing an inorder traversal
     * guarantees that the result will be sorted by element. We fill in some
     * contiguous block of array elements, starting at index, and return the
     * next available index in the array.
     *
     * @param counts The array to populate.
     */
    protected int traverse(BSTNode root, DataCount<E>[] counts, int idx) {
        if(root != null) {
            idx = traverse(root.left, counts, idx);
            counts[idx] = new DataCount<E>(root.data, root.count);
            idx = traverse(root.right, counts, idx + 1);
        }
        return idx;
    }
    
    public BSTNode findMax(BSTNode root){
		if (root == null){
			throw new NullPointerException();
		}
		
		while (root.right != null){
			root = root.right;
		}
		return root;
	}
	
	public BSTNode findMin(BSTNode root){
		if (root == null){
			throw new NullPointerException();
		}
		
		while (root != null){
			root = root.left;
		}
		return root;
	}
	
	public int getHeight(BSTNode root){
		if (root == null){
			return -1;
		}
		return (Math.max(getHeight(root.left), getHeight(root.right)) + 1);
	}

    /**
     * Dump the contents of the tree to a String (provided for debugging and
     * unit testing purposes).
     *
     * @return a textual representation of the tree.
     */
    protected String dump() {
        if (overallRoot != null)
            return dump(overallRoot);
        return "<empty tree>";
    }

    /**
     * Dump the contents of the subtree rooted at this node to a String
     * (provided for debugging purposes).
     *
     * @return a textual representation of the subtree rooted at this node.
     */
    protected String dump(BSTNode root) {
        if(root == null)
            return ".";

        String out = "([" + root.data + "," + root.count + "] ";
        out += dump(root.left);
        out += " ";
        out += dump(root.right);
        out += ")";

        return out;
    }
    
    
}
