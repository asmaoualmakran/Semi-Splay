package semisplay;

import java.util.*;

/**
 * @Author Asma Oualmakran
 * Implementation of a Splay tree using the semi-splay algorithm.
 * At the moment it only supports Binary Search Tree operations, the semi-splay algorithm is not implemented at the moment.
 */
public class SemiSplayTree<E extends  Comparable<E>> implements SearchTree<E>   {

    int initSize = 100;
    int rootIdx = 1;
    int rootDepth = 1;
    ArrayList<Node<E>> tree = new ArrayList<>();
    int splayDepth;

    /**
     * @return It returns the data structure where the tree is kept, this is used for testing
     */
    public ArrayList<Node<E>> getTree() {
        return this.tree;
    }

    /**
     * It clears the tree from all it's elements
     */
    public void deleteTree() {
        tree.clear();
        tree = new ArrayList<Node<E>>(initSize);

    }


    /**
     * @param index Index in the array where you want to place the node.
     * @param N The node to be placed in the tree.
     *          Add a node on a certain index in the data structure containing the tree.
     *          It fills the location between the last reached index and the new index with null values O(n) operation with generally small n.
     */
    public void addToTree(int index, Node<E> N) {

        int size = tree.size();

        if (size < index) {
            for (int i = 0; i < index - size; i++) {
                tree.add(null);
            }
        }
        tree.set(index - 1, N);

    }

    /**
     * @param idx The index of the node who's left child's index needs to be calculated.
     * @return The left child's index.
     */
    private int leftChildIdx(int idx) {
        return 2 * idx;
    }

    /**
     * @param idx The index of the node who's right child's index needs to be calculated.
     * @return The right child's index.
     */
    private int rightChildIdx(int idx) {
        return (2 * idx) + 1;
    }


    /**
     * @param root  The root of the subtree where the node wil be added.
     * @param child The node that will be attached to the root of the subtree.
     * @return void It makes the root the parent of child, and child the left or right child of root.
     * Depending if child is smaller of larger than root (BST rules used).
     */
    private void setChild(Node<E> root, Node<E> child) {


        if (root.smaller(child)) {   // root is smaller than the child

            root.setRightChild(child);  // the child will be added as the right child of root
            child.setParent(root);
            child.setIndex(rightChildIdx(root.getIndex()));
            child.setNodeDepth(root.getNodeDepth() + 1);
            addToTree(child.getIndex(), child);


        }
        if (root.larger(child)) { // root is larger than the child

            root.setLeftChild(child);  // the child will be added as the left child of the root
            child.setParent(root);
            child.setIndex(leftChildIdx(root.getIndex()));
            child.setNodeDepth(root.getNodeDepth() + 1);
            tree.ensureCapacity(child.getIndex());
            addToTree(child.getIndex(), child);
        }
    }

    /**
     * @param node The node that needs to be searched.
     * @return The path to the to be searched node in reversed order (stack).
     * If the tree does not contain the node, the path that is used to reach it is still returned.
     */
    private Stack<Node<E>> find(Node<E> node) {
        Stack<Node<E>> path = new Stack<>();
        Node<E> root = tree.get(rootIdx - 1);

        while (root.hasChild()) {
            if (root.equal(node)) {
                path.push(root);
                return path;

            } else if (root.smaller(node)) {   // the root of subtree is smaller than the searched node

                if (null == root.getRightChild()) {  // the root has no right child, the tree does not contain the node
                    path.push(root);
                    return path;
                } else {
                    path.push(root);
                    root = root.getRightChild();

                }

            } else {                             // the root is larger than the searched node
                if (null == root.getLeftChild()) {
                    path.push(root);
                    return path;
                } else {
                    path.push(root);
                    root = root.getLeftChild();
                }
            }
        }
        path.push(root);
        return path;

    }

    /**
     * @param value The value that needs to be added in the tree.
     * @return boolean It returns true when the value is added.
     * False is returned when the tree already contains the value, value is not added in that case.
     * The path to reach the added node is constructed while traveling the tree. This is contained in
     * the stack 'path', the path is saved in reverse order.
     */
    @Override
    public boolean add(E value) {

        Node<E> node = new Node<E>(value);


        if (tree.isEmpty()) {

            node.setIndex(rootIdx);
            node.setNodeDepth(rootDepth);
            addToTree(rootIdx, node);
            return true;

        } else {
            Stack<Node<E>> path = find(node);
            Node<E> root = path.peek();


            if (root.equal(node)) {  // the tree already contains the to be added value
                return false;
            } else {

                if (root.smaller(node) || root.larger(node)) {  // the added node is smaller or larger than the node

                    setChild(root, node);                    // add node to the tree
                    return true;

                } else {
                    return false;
                }
            }
        }
    }

    /**
     * @param Value The value of the node that needs to be found.
     * @return boolean It returns true when the node with value is found otherwise it returns false.
     */
    @Override
    public boolean contains(E Value) {

        Node<E> node = new Node<>(Value);
        Stack<Node<E>> path = find(node);

        return path.peek().equal(node);

    }

    /**
     * @param Value The value of the node that needs to be removed.
     * @return boolean It returns true when the node with the given value is found otherwise it returns false.
     * When the node has two child nodes, the BST procedure of deleting a node is started.
     * The most left leaf node of the right subtree is searched and set as the replacement for the deleted node.
     */
    @Override
    public boolean remove(E Value) {

        Node<E> node = new Node<E>(Value);
        Stack<Node<E>> path = find(node);
        if (node.equal(path.peek())) {

            node = path.peek();

            if (node.hasChild()) {

                Node<E> parent = node.parent;  // the Parent of the to be deleted node

                Node<E> leftChild = node.getLeftChild();
                Node<E> rightChild = node.getRightChild();
                if (leftChild != null && rightChild != null) {
                    Node<E> newRoot = findMinRight(node);   // search for the replacement node (most left node of the right subtree

                    setChild(newRoot, leftChild);
                    setChild(newRoot, rightChild);

                } else if (leftChild == null) {


                    setChild(parent, rightChild);
                    rightChild.setParent(parent);
                } else {

                    setChild(parent, leftChild);
                    leftChild.setParent(parent);
                }

                return true;

            } else {                             // the to be deleted node is a leaf, we can just delete

                Node<E> parent = node.getParent();
                if (node.getIndex() % 2 == 0) {  // index of the node is even --> you're a left child

                    parent.setLeftChild(null);
                    tree.remove(node);

                    return true;

                } else {                         // index of the node is odd --> you're a right child

                    parent.setRightChild(null);
                    tree.remove(node);

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param node The root of the subtree who's smallest left leaf of it's right subtree needs to be found.
     * @return It returns the node of the smallest left leaf of the right subtree.
     */
    private Node<E> findMinRight(Node<E> node) {
        Node<E> rightSubRoot = node.getRightChild();

        while (rightSubRoot.hasChild()) {
            if (null == rightSubRoot.getLeftChild()) {

                return rightSubRoot;
            } else {
                rightSubRoot = node.getLeftChild();
            }
        }
        return rightSubRoot;
    }


    /**
     * @return Iterator is not used in the implementation. It's properties are contained in the Node class.
     */
    @Override
    public Iterator<E> iterator() {
        return null;
    }


    /**
     * @return The number of nodes contained by the tree.
     * Iterates over the tree using the Node class' methods
     */
    @Override
    public int size() {

        if (tree.isEmpty()) {
            return 0;
        } else {
            int size = 0;
            Queue<Node> q = new LinkedList<>();
            Node<E> root = tree.get(rootIdx - 1);
            q.add(root);

            while (!q.isEmpty()) {

                size++;
                Node<E> curr = q.poll();
                if (curr.getLeftChild() != null) {
                    q.add(curr.getLeftChild());
                }
                if (curr.getRightChild() != null) {
                    q.add(curr.getRightChild());
                }

            }

            return size;
        }
    }

    /**
     * @return The maximum depth of the tree.
     * Iterates over the tree using the Node class' methods to determine the depth
     * Each node contains it's own depth, it's a matter of iterating over all the nodes to determine de depth
     */
    @Override
    public int depth() {
        if (tree.isEmpty()) {
            return 0;
        } else {

            Queue<Node> q = new LinkedList<>();
            Node<E> root = tree.get(rootIdx - 1);
            q.add(root);
            Integer depth = 0;

            while (!q.isEmpty()) {

                Node<E> curr = q.poll();

                if (curr.leftChild != null) {
                    q.add(curr.getLeftChild());
                }
                if (curr.rightChild != null) {
                    q.add(curr.getRightChild());
                }
                if (curr.getNodeDepth() > depth) {
                    depth = curr.getNodeDepth();
                }

            }

            return depth;
        }
    }

    /**
     * @param k The splay depth of the tree
     *          Constructor for the splay tree.
     */
    public SemiSplayTree(int k) {
        this.splayDepth = k;
        for (int i = 0; i > initSize; i++) {
            this.tree.add(null);
        }
    }


}
