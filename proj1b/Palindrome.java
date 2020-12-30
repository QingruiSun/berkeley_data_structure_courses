public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        }
        Deque<Character> deque = new LinkedListDeque<>();
        int wordLength = word.length();
        for (int i = 0; i < wordLength; ++i) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        int wordLength = word.length();
        if ((wordLength == 0) || (wordLength == 1)) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        int remainNum = wordLength;
        while (remainNum > 1) {
            Character leftChar = deque.removeFirst();
            Character rightChar = deque.removeLast();
            if (leftChar != rightChar) {
                return false;
            }
            remainNum = remainNum - 2;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc)  {
        if (word == null) {
            return false;
        }
        int wordLength = word.length();
        if (wordLength <= 1) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        int remainNum = wordLength;
        while (remainNum > 1) {
            char leftChar = deque.removeFirst();
            char rightChar = deque.removeLast();
            if (!cc.equalChars(leftChar, rightChar)) {
                return false;
            }
            remainNum = remainNum - 2;
        }
        return true;
    }
}
