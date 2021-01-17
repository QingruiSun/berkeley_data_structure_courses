import java.io.Serializable;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

public class BinaryTrie implements Serializable {

    private Queue<Node> queue = new PriorityQueue<>();
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        for (Character c : frequencyTable.keySet()) {
            Node node = new Node(c, frequencyTable.get(c), null, null);
            queue.add(node);
        }
        while (queue.size() > 1) {
            Node firstNode = queue.poll();
            Node secondNode = queue.poll();
            int sumFreq = firstNode.freq + secondNode.freq;
            Node newNode = new Node('\0', sumFreq, firstNode, secondNode);
            queue.add(newNode);
        }
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node root = queue.peek();
        Node ptr = root;
        int index = 0;
        BitSequence bitSequence = new BitSequence();
        while (ptr.c == '\0') {
            int bit = querySequence.bitAt(index++);
            if (bit == 0) {
                ptr = ptr.left;
                bitSequence = bitSequence.appended(0);
            } else {
                ptr = ptr.right;
                bitSequence = bitSequence.appended(1);
            }
        }
        return new Match(bitSequence, ptr.c);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> map = new HashMap<>();
        Node root = queue.peek();
        BitSequence bitSequence = new BitSequence();
        buildLookupTableHelp(map, bitSequence, root);
        return map;
    }

    private void buildLookupTableHelp(Map<Character, BitSequence> map,
                                      BitSequence bitSequence, Node root) {
        if (root == null) {
            throw new IllegalArgumentException("root should be not null!");
        }
        if (root.left != null) {
            BitSequence newLeftBitSequence = bitSequence.appended(0);
            buildLookupTableHelp(map, newLeftBitSequence, root.left);
        }
        if (root.right != null) {
            BitSequence newRightBitSequence = bitSequence.appended(1);
            buildLookupTableHelp(map, newRightBitSequence, root.right);
        }
        if (root.c != '\0') {
            map.put(root.c, bitSequence);
        }
    }

    private class Node implements Comparable<Node>, Serializable {
        private Integer freq;
        private Node left;
        private Node right;
        private Character c;
        Node(Character c, Integer freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
}
