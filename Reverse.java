//import java.util.Scanner;
public class Reverse {
    public static int[][] expandIfNeeded(int[][] array, int triedIndex) {
        if (triedIndex <  array.length) {
            return array;
        }
        int[][] resultArray = new int[array.length * 2][0];
        System.arraycopy(array, 0, resultArray, 0, array.length);
        return resultArray;
    }

    public static int[] expandIfNeeded(int[] array, int triedIndex) {
        if (triedIndex <  array.length - 1) {
            return array;
        }
        int[] resultArray = new int[array.length * 2 + 2];
        System.arraycopy(array, 0, resultArray, 0, array.length);
        return resultArray;
    }

    public static void main(String[] args) {
        int[][] ints = new int[1][0];
        Scanner linesScanner = new Scanner(System.in);
        int lineIndex = 0;
        while (linesScanner.hasNextLine()) {
            ints = expandIfNeeded(ints, lineIndex);
            Scanner intsScanner = new Scanner(linesScanner.nextLine());
            {
                int elementIndex = 0;
                while (intsScanner.hasNextInt()) {
                    ints[lineIndex] = expandIfNeeded(ints[lineIndex], elementIndex);
                    ints[lineIndex][elementIndex] = intsScanner.nextInt();
                    elementIndex++;
                }
                if (ints[lineIndex].length != 0) {
                    ints[lineIndex][ints[lineIndex].length - 1] = elementIndex - 1;
                }
                lineIndex++;
            }
        }
        while (lineIndex > 0) {
            lineIndex--;
            if (ints[lineIndex].length != 0) {
                for (int elementIndex = ints[lineIndex][ints[lineIndex].length - 1]; elementIndex >= 0; elementIndex--) {
                    System.out.print(ints[lineIndex][elementIndex]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
