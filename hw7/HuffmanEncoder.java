import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        int length = inputSymbols.length;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < length; ++i) {
            if (!map.containsKey(inputSymbols[i])) {
                map.put(inputSymbols[i], 1);
            } else {
                map.put(inputSymbols[i], map.get(inputSymbols[i]) + 1);
            }
        }
        return map;
    }

    private static char[] readInputSymbols(String inputFileName) {
        List<Character> charList = new LinkedList<>();
        try {
            File file = new File(inputFileName);
            FileReader fileReader = new FileReader(file);
            int intC;
            while ((intC =  fileReader.read()) != -1) {
                char c = (char) intC;
                charList.add(c);
            }
        } catch (IOException e) {
            System.out.println("Exception when read the file!");
            e.printStackTrace();
        }
        char[] charArray = new char[charList.size()];
        for (int i = 0; i < charList.size(); ++i) {
            charArray[i] = charList.get(i);
        }
        return charArray;
    }

    private static void buildPersistentFile(String writeFileName,
                                            BinaryTrie binaryTrie, char[] inputSymbols) {
        ObjectWriter ow = new ObjectWriter(writeFileName);
        ow.writeObject(binaryTrie);
        ow.writeObject(inputSymbols.length);
        Map<Character, BitSequence> lookuptab = binaryTrie.buildLookupTable();
        List<BitSequence> bitSequenceList = new ArrayList<>();
        for (int i = 0; i < inputSymbols.length; ++i) {
            bitSequenceList.add(lookuptab.get(inputSymbols[i]));
        }
        BitSequence bitSequence = BitSequence.assemble(bitSequenceList);
        ow.writeObject(bitSequence);
    }



    public static void main(String[] args) {
        String inputFileName = args[0];
        char[] inputSymbols = readInputSymbols(inputFileName);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie binaryTrie = new BinaryTrie(frequencyTable);
        String writeFileName = inputFileName + ".huf";
        buildPersistentFile(writeFileName, binaryTrie, inputSymbols);
    }
}
