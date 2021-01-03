package lab9;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        /* Parent of this Node. */
        private Node parent;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        }
        if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        Node addNode = new Node(key, value);
        if (p == null) {
            return addNode;
        }
        Node ptr = p;
        Node prePtr = null;
        while (ptr != null) {
            prePtr = ptr;
            if (key.compareTo(ptr.key) < 0) {
                ptr = ptr.left;
            } else {
                ptr = ptr.right;
            }
        }
        if (key.compareTo(prePtr.key) < 0) {
            prePtr.left = addNode;
        } else {
            prePtr.right = addNode;
        }
        addNode.parent = prePtr;
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        Node addNode = putHelper(key, value, root);
        if (root == null) {
            root = addNode;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return sizeHelper(root);
    }

    private int sizeHelper(Node p) {
        if (p == null) {
            return 0;
        }
        return 1 + sizeHelper(p.left) + sizeHelper(p.right);
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> sets = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node ptr = queue.remove();
            sets.add(ptr.key);
            if (ptr.left != null) {
                sets.add(ptr.left.key);
            }
            if (ptr.right != null) {
                sets.add(ptr.right.key);
            }
        }
        return sets;
    }


    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node removeNode = getNode(key);
        if (removeNode == null) {
            return null;
        }
        V result = removeNode.value;
        if (removeNode.left == null) {
            transplant(removeNode, removeNode.right);
        } else if (removeNode.right == null) {
            transplant(removeNode, removeNode.left);
        } else {
            Node nextMinimumNode = findMinimum(removeNode.right);
            if (nextMinimumNode.parent != removeNode) {
                transplant(nextMinimumNode, nextMinimumNode.right);
                nextMinimumNode.right = removeNode.right;
                nextMinimumNode.right.parent = removeNode.parent;
            }
            transplant(removeNode, nextMinimumNode);
            nextMinimumNode.left = removeNode.left;
            nextMinimumNode.left.parent = nextMinimumNode;
        }
        return result;
    }

    /**
     * Find the node with the minimus key in the subtree rooted at the p.
     */
    private Node findMinimum(Node p) {
        Node ptr = p;
        while (ptr.left != null) {
            ptr = ptr.left;
        }
        return ptr;
    }

    private Node getNode(K key) {
        Node ptr = root;
        while ((ptr != null) && (ptr.key != key)) {
            if (key.compareTo(ptr.key) < 0) {
                ptr = ptr.left;
            } else {
                ptr = ptr.right;
            }
        }
        if (ptr == null) {
            return null;
        }
        return ptr;
    }



    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node ptr = root;
        while ((ptr != null) && (ptr.key.compareTo(key) != 0)) {
            if (key.compareTo(ptr.key) < 0) {
                ptr = ptr.left;
            } else {
                ptr = ptr.right;
            }
        }
        if (ptr == null) {
            return null;
        }
        if (ptr.value == value) {
            remove(key);
            return value;
        } else {
            return null;
        }
    }

    /**
     * Replaces the subtree rooted at node v with the subtree rooted
     * at node v, node u's parent becomes node v's parent.
     */
    private void transplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    @Override
    public Iterator<K> iterator() {
        Iterator<K> it = new BSTMapIterator();
        return it;
    }

    private class BSTMapIterator implements Iterator<K> {

        Set<K> sets = keySet();
        Iterator<K> it = sets.iterator();
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public K next() {
            K result = it.next();
            return result;
        }
    }
}
