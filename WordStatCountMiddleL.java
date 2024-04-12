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
import java.util.List;
import java.util.ArrayList;

public class WordStatCountMiddleL {
    private static void sortAndWriteMapToFile(Map<String, Integer> map, BufferedWriter writer) throws IOException {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        for (Map.Entry<String, Integer> entry : entries) {
            writer.write(entry.getKey());
            writer.write(" ");
            writer.write(entry.getValue().toString());
            writer.write(System.lineSeparator());
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            Scanner scanner = new Scanner(new FileInputStream(args[0]), "UTF-8");
            try {
                scanner.setSeparator(ch -> !(Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\''));
                while (scanner.hasNext()) {
                    String currentString = scanner.next().toLowerCase();
                     if (currentString.length() > 4) {
                        String sub = currentString.substring(2, currentString.length() - 2);
                        map.put(sub, map.getOrDefault(sub, 0) + 1);
                    }
                }
            } finally {
                scanner.close();
            }
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                sortAndWriteMapToFile(map, writer);
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
