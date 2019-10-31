package semisplay;

public interface Tnode <N> extends Comparable<N> {

    boolean hasParent();
    boolean hasChild ();
    boolean isRoot();
    N getLeftChild ();
    N getRightChild ();
    N getParent ();
    void setParent (N n);

    void setLeftChild (N n);
    void setRightChild (N n);

}
