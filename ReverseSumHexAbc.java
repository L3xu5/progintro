import static java.util.Arrays.copyOf;
import java.util.HashMap;
import java.util.Map;

public class ReverseSumHexAbc {
    public static int[] expandIfNeeded(int[] array, int triedIndex) {
        if (triedIndex <  array.length) {
            return array;
        }
        return copyOf(array, array.length * 2);
    }

    public static void printSums(int[] array, int endIndex, Map<Object, Object> map) {
        int sum = 0;
        for (int i = 0; i < endIndex; i++) {
            sum += array[i];
            String stringSum = Integer.toString(sum);
            StringBuilder result = new StringBuilder();
            for (int j = 0; j < stringSum.length(); j++) {
                result.append(map.get(stringSum.charAt(j)));
            }
            System.out.print(result.toString());
            System.out.print(" ");
        }
        System.out.println();
    }

    private static Map<Object, Object> mapOf(Object... includes) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < includes.length - 1; i += 2) {
            map.put(includes[i], includes[i + 1]);
        }
        return map;
    }

    public static void main(String[] args) {
        int[] ints = new int[1];
        int elementIndex;
        Map<Object, Object> literalIntMap  = mapOf('-', '-', '0', 'a', '1', 'b', '2', 'c', '3', 'd', '4', 'e', '5', 'f', '6', 'g', '7', 'h', '8', 'i', '9', 'j');
        Scanner scanner= new Scanner(System.in);
        while (scanner.hasNextLine()) {
            elementIndex = 0;
            while (scanner.hasNextHexOrLiteralDecInLine()) {
                ints = expandIfNeeded(ints, elementIndex);
                ints[elementIndex] += scanner.nextHexOrLiteralDecInLine();;
                elementIndex++;
            }
            printSums(ints, elementIndex, literalIntMap);
            scanner.nextLine();
        }
    }
}
