import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class WsppSortedFirst {
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
        if (args.length < 2) {
            System.out.println("Not enough arguments");
            return;
        }
        Map<String, IntList> map = new TreeMap<>();
        try {
            Scanner scanner = new Scanner(new FileInputStream(args[0]), "UTF-8");
            try {
                scanner.setSeparator(ch -> !(Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\''));
                int indexOfWord = 1;
                Set<String> used = new HashSet<>();
                while (scanner.hasNextLine()) {
                    for (; scanner.hasNextInLine(); indexOfWord++) {
                        String s = scanner.nextInLine().toLowerCase();
                        IntList currentIntList = map.getOrDefault(s, new IntList());
                        currentIntList.add(0, currentIntList.get(0) + 1);
                        if (used.add(s)) {
                            currentIntList.add(indexOfWord);
                        }
                        map.put(s, currentIntList);
                    }
                    used.clear();
                    scanner.nextLine();
                }
            } finally {
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsporting encoding in reader: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error in writer occurs: " + e.getMessage());
        }
        try {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                writeMapToFile(map, writer);
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsporting encoding in writer: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error in writer occurs: " + e.getMessage());
        }
    }
}
