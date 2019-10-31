package test;

import semisplay.Node;
import semisplay.SemiSplayTree;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Test {

    SemiSplayTree<Integer> splayTree = new SemiSplayTree<Integer>(3);
    ArrayList<Node<Integer>> tree = splayTree.getTree();
    int rootIdx = 1;

    @org.junit.Test
    public void add() {

        splayTree.deleteTree();

        splayTree.add(5);       // initialise the tree
        splayTree.add(3);
        splayTree.add(8);

        ArrayList<Node<Integer>> tree = splayTree.getTree();

        Node<Integer> root = tree.get((rootIdx - 1));      // setting variables for easy testing
        Node<Integer> left = tree.get((rootIdx*2) - 1);
        Node<Integer> right = tree.get(rootIdx *2);

        // test to check if the values are placed in the data structure
        System.out.print(tree);

        assertTrue(root.getValue() == 5);
        assertTrue(left.getValue() == 3);
        assertTrue(right.getValue() == 8);

        assertTrue(root.getValue() == 5 && root.getLeftChild().getValue() == 3 && root.getRightChild().getValue() == 8);        // test to check if nodes are correctly placed in the array
        assertTrue(left.getParent() == root && right.getParent() == root);          // test to check if the parent is placed in both nodes
        assertTrue(root.getLeftChild() == left && root.getRightChild() == right);   // test if the pointers to the children are placed correctly

        assertFalse(splayTree.add(5));      // adding a value already contained must return false
        assertTrue(splayTree.add(20));      // adding a value new to the tree must return true


    }

    @org.junit.Test
    public void contains() {

        splayTree.deleteTree();

        splayTree.add(10);
        splayTree.add(30);
        splayTree.add(5);
        splayTree.add(4);
        splayTree.add(3);

        assertTrue(splayTree.contains(10) && splayTree.contains(3) && splayTree.contains(30));
        assertFalse(splayTree.contains(20) && splayTree.contains(6));

    }

    @org.junit.Test
    public void removeLeafs() {

        splayTree.deleteTree();

        splayTree.add(10);
        splayTree.add(30);
        splayTree.add(5);
        splayTree.add(4);
        splayTree.add(3);


        //                 10
        //            5          30
        //       4
        //    3


        splayTree.remove(3);                            // test to check if leafs are removed in a correct manner

        assertTrue(splayTree.getTree().get(3).getLeftChild() == null);

        splayTree.remove(4);

        assertTrue(splayTree.getTree().get(2).getLeftChild() == null);

        splayTree.remove(30);

        assertTrue(splayTree.getTree().get(rootIdx).getRightChild() == null);

        assertFalse(splayTree.contains(3) && splayTree.contains(4) && splayTree.contains(30));
        assertTrue(splayTree.contains(10) && splayTree.contains(5));

        splayTree.deleteTree(); // test if the root is deleted and replaced by the correct node
    }
    @org.junit.Test
    public void removeNonLeafs() {
        splayTree.add(10);
        splayTree.add(30);
        splayTree.add(5);
        splayTree.add(4);
        splayTree.add(3);

        splayTree.remove(5);
        splayTree.remove(10);

        assertTrue(splayTree.getTree().get(rootIdx).getValue() == 30);
        assertTrue(splayTree.getTree().get(rootIdx).getRightChild() == null);
        assertTrue(splayTree.getTree().get(rootIdx).getLeftChild().getValue() == 4);

    }


    @org.junit.Test
    public void size() {

        splayTree.deleteTree();

        assert (splayTree.size() == 0);

        splayTree.add(10);
        splayTree.add(30);
        splayTree.add(5);
        splayTree.add(4);
        splayTree.add(3);

        assert (splayTree.size() == 5);

        splayTree.remove(3);
        splayTree.remove(4);
        splayTree.remove(30);

        assert (splayTree.size() == 2);

    }

    @org.junit.Test
    public void depth() {

        splayTree.deleteTree();

        splayTree.add(10);
        assert (splayTree.depth() == 1);
        splayTree.add(30);
        assert (splayTree.depth() == 2);
        splayTree.add(5);
        assert (splayTree.depth() == 2);
        splayTree.add(4);
        assert (splayTree.depth() == 3);
        splayTree.add(3);

        assert (splayTree.depth() == 4);

        splayTree.remove(3);
        splayTree.remove(4);


        assert (splayTree.depth() == 2);

        splayTree.remove(5);
        splayTree.remove(30);

        assert (splayTree.depth() == 1);
    }
}