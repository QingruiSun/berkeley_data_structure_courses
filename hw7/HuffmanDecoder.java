import java.io.FileWriter;
import java.io.IOException;

public class HuffmanDecoder {

    public static void decoder(String inputFileName, String writeFileName) {
        ObjectReader or = new ObjectReader(inputFileName);
        Object o = or.readObject();
        BinaryTrie binaryTrie = (BinaryTrie) o;
        o = or.readObject();
        Integer symbolNum = (Integer) o;
        o = or.readObject();
        BitSequence bitSequence = (BitSequence) o;
        try {
            FileWriter fileWriter = new FileWriter(writeFileName);
            while (bitSequence.length() != 0) {
                Match match = binaryTrie.longestPrefixMatch(bitSequence);
                fileWriter.write(match.getSymbol());
                int catLength = match.getSequence().length();
                int prevLength = bitSequence.length();
                int remainLength = prevLength - catLength;
                bitSequence = bitSequence.lastNBits(remainLength);
            }
        } catch (IOException e) {
            System.out.println("Exception in the write!");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String inputFileName = args[0];
        String writeFileName = args[1];
        decoder(inputFileName, writeFileName);
    }
}
