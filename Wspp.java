import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.LinkedHashMap;

public class Wspp {
    private static void writeMapToFile(Map<String, IntList> map, BufferedWriter writer) throws IOException {
        for (Map.Entry<String, IntList> entry : map.entrySet()) {
            writer.write(entry.getKey());
            IntList includes = entry.getValue();
            for (int include = 0; include < includes.length(); include++) {
                writer.write(" ");
                writer.write(Integer.toString(includes.get(include)));
            }
            writer.write(System.lineSeparator());
        }
    }

    public static void main(String[] args) {
        Map<String, IntList> map = new LinkedHashMap<>();
        try {
            Scanner scanner = new Scanner(new FileInputStream(args[0]), "UTF-8");
            try {
                scanner.setSeparator(ch -> !(Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\''));
                for (int indexOfWord = 1; scanner.hasNext(); indexOfWord++) {
                    String s = scanner.next().toLowerCase();
                    IntList currentIntList = map.getOrDefault(s, new IntList());
                    currentIntList.add(0, currentIntList.get(0) + 1);
                    currentIntList.add(indexOfWord);
                    map.put(s, currentIntList);
                }
            } finally {
                scanner.close();
            }
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                writeMapToFile(map, writer);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Two arguments needed");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsporting encoding: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error occurs: " + e.getMessage());
        }
    }
}
