package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Md2Html {
    private static StringBuilder parseUntilEndOfTag(String tag, Reader reader) throws IOException {
        StringBuilder resultString = new StringBuilder();
        String lastChar = "";
        Map<String, String> markdownToHtmlOpenTag = Map.of("*", "<em>", "_", "<em>", "**",
                "<strong>", "__", "<strong>", "--", "<s>", "`", "<code>", "\n\n", "\n<p>");
        Map<String, String> markdownToHtmlCloseTag = Map.of("*", "</em>", "_", "</em>", "**",
                "</strong>", "__", "</strong>", "--", "</s>", "`", "</code>", "\n\n", "</p>\n\n");
        int ch;
        while ((ch = reader.read()) != -1) {
            String currentChar = Character.toString(ch);
            if (currentChar.equals("#")
                    && (resultString.isEmpty() || resultString.charAt(resultString.length() - 1) == '\n')) {
                int headerLevel = 1;
                while ((ch = reader.read()) != -1 && (currentChar = Character.toString(ch)).equals("#")) {
                    headerLevel++;
                }
                if (currentChar.equals(" ")) {
                    if (resultString.isEmpty()) {
                        resultString.append("<h").append(headerLevel).append(">");
                    } else {
                        resultString.replace(resultString.length() - 1, resultString.length(), "<h" + headerLevel + ">");
                    }
                    resultString.append(parseUntilEndOfTag("\n\n", reader));
                    lastChar = "";
                    continue;
                } else {
                    resultString.append("#".repeat(headerLevel - 1));
                }
            }
            if ((currentChar + lastChar).equals(tag)) {
                return resultString.append(markdownToHtmlCloseTag.get(tag));
            } else if ((lastChar + currentChar).equals("\n\n") && tag != null) {
                return resultString.append(markdownToHtmlCloseTag.get("\n\n"));
            } else if (currentChar.equals(tag)) {
                if (!lastChar.equals("\\")) {
                    resultString.append(lastChar);
                    return resultString.append(markdownToHtmlCloseTag.get(tag));
                } else {
                    lastChar = currentChar;
                }
            } else if (markdownToHtmlOpenTag.containsKey(currentChar + lastChar)) {
                resultString.append(markdownToHtmlOpenTag.get(currentChar + lastChar));
                resultString.append(parseUntilEndOfTag(currentChar + lastChar, reader));
                lastChar = "";
            } else if (markdownToHtmlOpenTag.containsKey(currentChar)) {
                if (!lastChar.equals("\\")) {
                    resultString.append(lastChar);
                    StringBuilder temp = parseUntilEndOfTag(currentChar, reader);
                    if (temp.substring(temp.length() - 5).equals(markdownToHtmlCloseTag.get(currentChar))) {
                        resultString.append(markdownToHtmlOpenTag.get(currentChar));
                    } else {
                        resultString.append(currentChar);
                    }
                    resultString.append(temp);
                    lastChar = "";
                } else {
                    lastChar = currentChar;
                }
            } else if (resultString.length() > 2 && resultString.substring(resultString.length() - 2, resultString.length()).equals("\n\n")) {
                StringBuilder temp = parseUntilEndOfTag("\n\n", reader);
                if (!temp.isEmpty() && temp.substring(temp.length() - 6).equals(markdownToHtmlCloseTag.get("\n\n"))) {
                    resultString.replace(resultString.length() - 2, resultString.length(), markdownToHtmlOpenTag.get("\n\n"));
                }
                if (!currentChar.equals("\n")) {
                    resultString.append(currentChar);
                }
                resultString.append(temp);
            } else {
                resultString.append(lastChar);
                lastChar = currentChar;
            }
        }
        return resultString.append(lastChar);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough arguments");
            return;
        }
        StringBuilder resultString = new StringBuilder();
        Map<String, String> markdownToHtmlOpenTag = Map.of("*", "<em>", "_", "<em>", "**",
                "<strong>", "__", "<strong>", "--", "<s>", "`", "<code>");
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
            resultString.append(parseUntilEndOfTag(null, reader));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error in writer occurs: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            writer.write(resultString.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsporting encoding in writer: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error in writer occurs: " + e.getMessage());
        }
    }

}
