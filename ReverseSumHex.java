
import static java.util.Arrays.copyOf;

public class ReverseSumHex {
    public static int[] expandIfNeeded(int[] array, int triedIndex) {
        if (triedIndex <  array.length) {
            return array;
        }
        return copyOf(array, array.length * 2);
    }

    public static void printSums(int[] array, int endIndex) {
        int sum = 0;
        for (int i = 0; i < endIndex; i++) {
            sum += array[i];
            System.out.print(Integer.toHexString(sum));
            System.out.print(" ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] ints = new int[1];
        Scanner linesScanner = new Scanner(System.in);
        while (linesScanner.hasNextLine()) {
            Scanner intsScanner = new Scanner(linesScanner.nextLine());
            {
                int elementIndex = 0;
                while (intsScanner.hasNext()) {
                    ints = expandIfNeeded(ints, elementIndex);
                    ints[elementIndex] += Integer.parseUnsignedInt(intsScanner.next(), 16);
                    elementIndex++;
                }
                printSums(ints, elementIndex);
            }
        }
    }
}
