package semisplay;

/**
 * @Author Asma Oualmakran
 */
public class Node<E extends Comparable<E>> implements Tnode<semisplay.Node<E>> {

    E value = null;
    Node<E> parent = null;
    Node<E> leftChild = null;
    Node<E> rightChild = null;
    Integer index = null;
    Integer nodeDepth = null;

    /**
     * @return boolean Determine if the node is the root of the tree.
     */
    @Override
    public boolean isRoot() {
        return (!hasParent() && index == 0);
    }

    /**
     * @return Returns a Node as left child if there is one present, otherwise null is returned.
     */
    @Override
    public Node<E> getLeftChild() {
        return this.leftChild;
    }

    /**
     * @return Returns a Node as right child if there is one present, otherwise null is returned.
     */
    @Override
    public Node<E> getRightChild() {
        return this.rightChild;
    }

    /**
     * @return Returns a Node as parent if there is one present, otherwise null is returned.
     */
    @Override
    public Node<E> getParent() {
        return this.parent;
    }

    /**
     * @return Returns true when the node has a parent.
     */
    @Override
    public boolean hasParent() {
        return (parent != null);
    }

    /**
     * @return Retruns true when the node has at least one child node.
     */
    @Override
    public boolean hasChild() {
        return (leftChild != null || rightChild != null);
    }

    /**
     * @param n Given a node, the node will be set as the parent.
     */
    @Override
    public void setParent(Node<E> n) {
        this.parent = n;
    }

    /**
     * @param n Given a node, the node will be set as the left child.
     */
    @Override
    public void setLeftChild(Node<E> n) {
        this.leftChild = n;
    }

    /**
     * @param n Given a node, the node will be set as the right child.
     */
    @Override
    public void setRightChild(Node<E> n) {
        this.rightChild = n;
    }


    /**
     * @return Returns the logical tree index of the node.
     * The index is always in relation to the index of its parent.
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param idx The index that the node needs to have.
     *            This is the logical index of the node in the tree.
     */
    public void setIndex(int idx) {
        this.index = idx;
    }

    /**
     * @param val The value that the node has
     *            At the moment only Integer values are accepted.
     */
    public Node(E val) {
        this.value = val;
    }

    /**
     * @return The value of the node is returned if it has one, otherwise null is returned.
     */
    public E getValue() {
        return this.value;
    }

    /**
     * @return The depth of the node in the tree is returned. It's value is always in relation to the depth of its parent.
     */
    public Integer getNodeDepth() {
        return this.nodeDepth;
    }

    /**
     * @param depth Changing the depth of the node.
     */
    public void setNodeDepth(Integer depth) {
        this.nodeDepth = depth;
    }

    /**
     * @param n The node used in the comparison.
     * @return If the value of this node is smaller than the value of the compared node it returns true. Otherwise false is returned.
     */
    public boolean smaller(Node<E> n) {  // value current node is smaller than the node that it's compared to node < n
        if (compareTo(n) < 0) {
            return true;
        }
        return false;
    }

    /**
     * @param n The node used in the comparison.
     * @return If the value of this node is larger than the value of the compared node it returns true. Otherwise false is returned.
     */
    public boolean larger(Node<E> n) {  // value current node is larger than the node it's compared to
        if (compareTo(n) > 0) {
            return true;
        }

        return false;
    }

    /**
     * @param n The node used in the comparison.
     * @return If the value of this node is equal than the value of the compared node it returns true. Otherwise false is returned.
     */
    public boolean equal(Node<E> n) {
        if (compareTo(n) == 0) {
            return true;
        }
        return false;
    }

    /**
     * @param o The node used in the comparison.
     * @return -1 if it's smaller,  0 if it's equal, 1 if it's equal.
     * override of the method compareTo from the Comparable interface, conform to the description in the javaDocs.
     */
    @Override
    public int compareTo(Node<E> o) {

        E valX = this.value;
        E valY = o.getValue();

        return valX.compareTo(valY);
    }


}
