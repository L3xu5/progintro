package game;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TicTacToeBoard extends AbstractTicTacToeBoard {

    public TicTacToeBoard(int m, int n, int k) {
        super(m, n, k);
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
    }

    public TicTacToeBoard(final PrintStream out, final Scanner in) {
        this(askM(out, in), askN(out, in), askK(out, in));
    }
    public TicTacToeBoard() {
        this(System.out, new Scanner(System.in));
    }

    private static int askM(final PrintStream out, final Scanner in) {
        int m;
        out.print("Enter M: ");
        while (true) {
            try {
                m = in.nextInt();
                if (m <= 0) {
                    System.out.println("M is less than 1. Can not create field. Try another values.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input " + in.nextLine() + "! Try another values.");
            }
        }
        return m;
    }

    private static int askN(final PrintStream out, final Scanner in) {
        int n;
        out.print("Enter N: ");
        while (true) {
            try {
                n = in.nextInt();
                if (n <= 0) {
                    System.out.println("N is less than 1. Can not create field. Try another values.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input " + in.nextLine() + "! Try another values.");
            }
        }
        return n;
    }

    private static int askK(final PrintStream out, final Scanner in) {
        int k;
        out.print("Enter K: ");
        while (true) {
            try {
                k = in.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input " + in.nextLine() + "! Try another values.");
            }
        }
        return k;
    }

    @Override
    public Board getClone() {
        return new TicTacToeBoard(m, n, k);
    }
}
