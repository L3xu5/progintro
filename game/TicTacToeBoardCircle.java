package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TicTacToeBoardCircle extends AbstractTicTacToeBoard {
    private int amountOfNotACells;

    public TicTacToeBoardCircle(int r, int k) {
        super(r * 2, r * 2, k);
        float rCord = (float) r - 0.5f;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if ((rCord - i) * (rCord - i) + (rCord - j) * (rCord - j) > r * r) {
                    cells[i][j] = Cell.NOTaCELL;
                    amountOfNotACells++;
                } else {
                    cells[i][j] = Cell.E;
                }
            }
        }
    }
    private static int askR(final PrintStream out, final Scanner in) {
        out.print("Enter R: ");
        int r;
        while (true) {
            try {
                r = in.nextInt();
                if (r <= 0) {
                    System.out.println("R is less than 1. Can not create field. Try another values.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input " + in.nextLine() + "! Try another values.");
            }
        }
        return r;
    }

    private static int askK(final PrintStream out, final Scanner in) {
        out.print("Enter K: ");
        int k;
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
    public TicTacToeBoardCircle(final PrintStream out, final Scanner in) {
        this(askR(out, in), askK(out, in));
    }

    public TicTacToeBoardCircle() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Board getClone() {
        return new TicTacToeBoardCircle(m / 2, k);
    }
    @Override
    protected boolean isDraw() {
        return amountOfTurns == m * m - amountOfNotACells;
    }
}
