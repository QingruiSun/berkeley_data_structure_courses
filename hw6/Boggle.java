import java.io.File;
import java.util.*;

import edu.princeton.cs.algs4.In;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "trivial_words.txt";

    // Root of the trie.
    private static Node root;



    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0) {
            throw new IllegalArgumentException("K should be greater than 0!");
        }
        File boardFile = new File(boardFilePath);
        if (!boardFile.exists()) {
            throw new IllegalArgumentException("Board file doesn't exist!");
        }
        In in = new In(boardFile);
        int rows = 0;
        List<String> boardStrings = new ArrayList<>();
        while (in.hasNextLine()) {
            rows++;
            boardStrings.add(in.readLine());
        }
        int cols = boardStrings.get(0).length();
        for (int i = 1; i < rows; ++i) {
            if (boardStrings.get(i).length() != cols) {
                throw new IllegalArgumentException("String length is inconsistent," +
                        " board should be a board!");
            }
        }
        char[][] boadChars = new char[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                boadChars[i][j] = boardStrings.get(i).charAt(j);
            }
        }
        if (root == null) {
            buildTrie();
        }
        Queue<stringContainers> resultQueue = new PriorityQueue<>();
        boolean[][] visited = new boolean[rows][cols];
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}};
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                String searchString = "";
                search(boadChars, visited, directions, i, j, rows, cols, k,
                        resultQueue, root, searchString);
            }
        }
        List<String> resultList = new LinkedList<>();
        while (!resultQueue.isEmpty()) {
            String oneString = resultQueue.poll().stringContent;
            resultList.add(0, oneString);
        }
        return resultList;
    }

    /** Use DFS to find a valid word in the board, we utilize the trie to prune
     * unnecessary bunch. */
    private static void search(char[][] boardChars, boolean[][] visited, int[][] directions,
                               int x, int y, int rows, int cols, int k,
                               Queue<stringContainers> resultQueue,
                               Node ptr, String searchString) {
        ptr = ptr.children.get(boardChars[x][y]);
        if (ptr == null) {
            return;
        }
        visited[x][y] = true;
        searchString = searchString + ptr.c;
        if ((ptr.isEndOfWord) && (!ptr.hasFound)) {
            ptr.hasFound = true;
            resultQueue.add(new stringContainers(searchString));
            if (resultQueue.size() > k) {
                resultQueue.poll();
            }
        }
        for (int i = 0; i < 8; ++i) {
            int nextX = x + directions[i][0];
            int nextY = y + directions[i][1];
            if ((nextX >= 0) && (nextX < rows) && (nextY >= 0) && (nextY < cols)) {
                if (!visited[nextX][nextY]) {
                    search(boardChars, visited, directions, nextX, nextY, rows, cols, k,
                            resultQueue, ptr, searchString);
                }
            }
        }
        visited[x][y] = false;
    }

    /** Build a trie structure. */
    private static void buildTrie() {
        File file = new File(dictPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Dict file not exits!");
        }
        root = new Node();
        In in = new In(file);
        while (in.hasNextLine()) {
            String word = in.readLine();
            addWordToTrie(root, word);
        }
    }


    /** Add a word to the trie. */
    private static void addWordToTrie(Node root, String word) {
        int wordLength = word.length();
        Node ptr = root;
        for (int i = 0; i < wordLength; ++i) {
            char letter = word.charAt(i);
            if (!ptr.children.containsKey(letter)) {
                ptr.children.put(letter, new Node(letter));
            }
            ptr = ptr.children.get(letter);
        }
        ptr.isEndOfWord = true;
    }


    /** Node represents a node in the trie */
    private static class Node {
        boolean isEndOfWord;
        char c;
        // Because exists many ways to form a word in aboard, I use it to assure
        // the word in result is unique.
        boolean hasFound;
        Map<Character, Node> children;
        Node(Character c) {
            this.c = c;
            children = new HashMap<>();
            hasFound = false;
        }
        Node() {
            children = new HashMap<>();
            hasFound = false;
        }
    }


    /** Use to compare String, we need find the longer Sting. When the length is equal,
     * we need chose String with low alphabetical order. */
    private static class stringContainers implements Comparable<stringContainers> {
        String stringContent;
        stringContainers(String stringContent) {
            this.stringContent = stringContent;
        }

        @Override
        public int compareTo(stringContainers o) {
            if (stringContent.length() < o.stringContent.length()) {
                return -1;
            } else if (stringContent.length() > o.stringContent.length()) {
                return 1;
            } else {
                return -stringContent.compareTo(o.stringContent);
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "exampleBoard2.txt";
        List<String> resultStrings = solve(7, filePath);
        System.out.println(resultStrings);
    }
}
