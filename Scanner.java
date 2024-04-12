import java.io.Reader;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.IOException;
import static java.util.Objects.isNull;
import java.util.InputMismatchException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Map;

public class Scanner {
    private final Reader reader;
    private final char[] buffer = new char[8092];
    private final StringBuilder token = new StringBuilder();
    private StringBuilder lineToken = null;
    private Integer hexOrLiteralDecToken = null;
    private Integer intToken = null;
    private Long longToken;
    private int indexOfBuffer = 0;
    private int indexInLine = 0;
    private int amountOfReaded = 0;
    private final Map<Object, Object> literalIntMap  = mapOf('a', 0, 'b', 1, 'c', 2, 'd', 3, 'e', 4, 'f', 5, 'g', 6, 'h', 7, 'i', 8, 'j', 9);
    private Predicate<Character> isSeparator = Character::isWhitespace;

    private static Map<Object, Object> mapOf(Object... includes) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < includes.length - 1; i += 2) {
            map.put(includes[i], includes[i + 1]);
        }
        return map;
    }

    public Scanner(File file) throws FileNotFoundException {
        this.reader = new InputStreamReader(new FileInputStream(file));        
    }
    
    public Scanner(File file, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
        this.reader = new InputStreamReader(new FileInputStream(file), encoding);        
    }

    public Scanner(InputStream in) {
        this.reader = new InputStreamReader(in);
    }
    
    public Scanner(InputStream in, String encoding) throws UnsupportedEncodingException {
        this.reader = new InputStreamReader(in, encoding);
    }

    public Scanner(String string) {
        this.reader = new StringReader(string);
    }

    public  void setSeparator(Predicate<Character> isSeparator) {
        this.isSeparator = isSeparator;
    }

    public boolean hasNext() throws IllegalStateException {
        if (!token.isEmpty()) {
            return true;
        }
        while (amountOfReaded != -1) {
            for (; indexOfBuffer < amountOfReaded; indexOfBuffer++) {
                if (isSeparator.test(buffer[indexOfBuffer])) {
                    if (token.isEmpty()) {
                        continue;
                    }
                    return true;
                } else {
                    token.append(buffer[indexOfBuffer]);
                }
            }
            indexOfBuffer = 0;
            try {
                amountOfReaded = reader.read(buffer);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return !token.isEmpty();
    }

    boolean hasNextInLine() throws IllegalStateException {
        if (isNull(lineToken)) {
            return false;
        }
        if (!token.isEmpty()) {
            return true;
        }
        for (; indexInLine < lineToken.length(); indexInLine++) {
            if (isSeparator.test(lineToken.charAt(indexInLine))) {
                if (token.isEmpty()) {
                    continue;
                }
                return true;
            } else {
                token.append(lineToken.charAt(indexInLine));
            }
        }
        return !token.isEmpty();
    }

    boolean hasNextInt() throws NumberFormatException {
        if (!isNull(intToken)) {
            return true;
        } else if (hasNext()) {
            try {
                intToken = Integer.parseInt(token.toString());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    boolean hasNextHexOrLiteralDecInLine() throws NumberFormatException {
        if (!isNull(hexOrLiteralDecToken)) {
            return true;
        }
        if (hasNextInLine()) {
            String iterToken = token.toString().toLowerCase();
            if (iterToken.startsWith("0x")) {
                try {
                    hexOrLiteralDecToken = Integer.parseUnsignedInt(iterToken.replace("0x", ""), 16);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                try {
                    StringBuilder buildedDec = new StringBuilder();
                    for (int i = 0; i < iterToken.length(); i++) {
                        if (literalIntMap.containsKey(iterToken.charAt(i))) {
                            buildedDec.append(literalIntMap.get(iterToken.charAt(i)));
                        } else {
                            buildedDec.append(iterToken.charAt(i));
                        }
                    }
                    hexOrLiteralDecToken = Integer.parseInt(buildedDec.toString());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }
    
    boolean hasNextInt(int radix) throws NumberFormatException {
        if (!isNull(intToken)) {
            return true;
        } else if (hasNext()) {
            try {
                intToken = Integer.parseInt(token.toString(), radix);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    boolean hasNextLong() throws NumberFormatException {
        if (!isNull(longToken)) {
            return true;
        } else if (hasNext()) {
            try {
                longToken = Long.parseLong(token.toString());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
    
    boolean hasNextLong(int radix) throws NumberFormatException {
        if (!isNull(longToken)) {
            return true;
        } else if (hasNext()) {
            try {
                longToken = Long.parseLong(token.toString(), radix);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private int isLineSeparator(int indexOfBuffer) {
        int indexOfSeparator = indexOfBuffer;
        for (; indexOfSeparator < Math.min(indexOfBuffer + System.lineSeparator().length(), amountOfReaded); indexOfSeparator++) {
            if (buffer[indexOfSeparator] != System.lineSeparator().charAt(indexOfSeparator - indexOfBuffer)) {
                return -1;
            }
        }
        return indexOfSeparator;
    }

    boolean hasNextLine() throws IllegalStateException {
        if (!isNull(lineToken)) {
            return true;
        }
        lineToken = new StringBuilder(token);
        while (amountOfReaded != -1) {
            for (; indexOfBuffer < amountOfReaded; indexOfBuffer++) {
                int indexOfSeparatorEnds = isLineSeparator(indexOfBuffer);
                if (indexOfSeparatorEnds != -1) {
                    indexOfBuffer = indexOfSeparatorEnds;
                    return true;
                } else {
                    lineToken.append(buffer[indexOfBuffer]);
                }
            }
            indexOfBuffer = 0;
            try {
                amountOfReaded = reader.read(buffer);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return !lineToken.isEmpty();
    }
    
    String next() throws InputMismatchException {
        if (hasNext()) {
            String returnValue = token.toString();
            token.setLength(0);
            return returnValue;
        }
        throw new InputMismatchException();
    }

    String nextInLine() throws InputMismatchException {
        if (hasNext()) {
            String returnValue = token.toString();
            token.setLength(0);
            return returnValue;
        }
        throw new InputMismatchException();
    }

    int nextHexOrLiteralDecInLine() throws InputMismatchException {
        if (hasNextHexOrLiteralDecInLine()) {
            int returnValue = hexOrLiteralDecToken;
            hexOrLiteralDecToken = null;
            token.setLength(0);
            return returnValue;
        }
        throw new InputMismatchException();
    }

    int nextInt() throws InputMismatchException {
        if (hasNextInt()) {
            int returnValue = intToken;
            intToken = null;
            token.setLength(0);
            return returnValue;
        }
        throw new InputMismatchException();
    }

    int nextInt(int radix) throws InputMismatchException {
        if (hasNextInt(radix)) {
            int returnValue = intToken;
            intToken = null;
            token.setLength(0);
            return returnValue;
        }
        throw new InputMismatchException();
    }
    
    long nextLong() {
        if (hasNextLong()) {
            long returnValue = longToken;
            longToken = null;
            token.setLength(0);
            return returnValue;
        }
        throw new InputMismatchException();
    }

    long nextLong(int radix) throws InputMismatchException {
        if (hasNextLong(radix)) {
            long returnValue = longToken;
            longToken = null;
            token.setLength(0);
            return returnValue;
        }
        throw new InputMismatchException();
    }
    
    String nextLine() throws NoSuchElementException {
        if (hasNextLine()) {
            String returnValue = lineToken.toString();
            lineToken = null;
            token.setLength(0);
            indexInLine = 0;
            return returnValue;
        }
        throw new NoSuchElementException();
    }

    void close() throws IOException {
        reader.close();
    }
}