package game;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public abstract class AbstractTicTacToeBoard implements  Board{
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.NOTaCELL, ' '
    );
    protected final int m;
    protected final int n;
    protected final int k;
    protected final Cell[][] cells;
    protected Cell turn;
    protected final Position positionTicTacToe;
    protected int amountOfTurns = 0;

    protected List<ImmutablePair> lefts = List.of(new ImmutablePair(0, -1), new ImmutablePair(-1, 0), new ImmutablePair(-1, -1), new ImmutablePair(1, -1));
    protected List<ImmutablePair> rights = List.of(new ImmutablePair(0, 1), new ImmutablePair(1, 0), new ImmutablePair(1, 1), new ImmutablePair(-1, 1));

    protected Map<ImmutablePair, Integer> turns = new HashMap<>();

    private class PositionTicTacToe implements Position {
        @Override
        public boolean isValid(final Move move) {
            return 0 <= move.getRow() && move.getRow() < m
                    && 0 <= move.getColumn() && move.getColumn() < n
                    && cells[move.getRow()][move.getColumn()] == Cell.E
                    && turn == AbstractTicTacToeBoard.this.getCell();
        }
        @Override
        public String toString() {
            return AbstractTicTacToeBoard.this.toString();
        }

        @Override
        public int getM() {
            return m;
        }

        @Override
        public int getN() {
            return n;
        }
    }

    public AbstractTicTacToeBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[m][n];
        turn = Cell.X;
        for (int i = 0; i < lefts.size(); i++) {
            turns.put(lefts.get(i), 0);
            turns.put(rights.get(i), 0);
        }
        positionTicTacToe = new PositionTicTacToe();
    }

    @Override
    public Position getPosition() {
        return positionTicTacToe;
    }

    @Override
    public abstract Board getClone();

    protected static class ImmutablePair {
        private final int first;
        private final int second;

        public ImmutablePair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }

    protected boolean isWin() {
        for (int i = 0; i < lefts.size(); i++) {
            if (turns.get(lefts.get(i)) + turns.get(rights.get(i)) == k - 1) {
                return true;
            }
        }
        return false;
    }

    protected boolean isExtra() {
        for (int i = 0; i < lefts.size(); i++) {
            int turnsOnLeft = turns.get(lefts.get(i));
            int turnsOnRight = turns.get(rights.get(i));
            if (turnsOnLeft < 4 && turnsOnRight < 4 && turnsOnLeft + turnsOnRight >= 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!positionTicTacToe.isValid(move)) {
            return Result.LOSE;
        }
        turns.replaceAll((k, v) -> 0);
        int currentMoveRow = move.getRow();
        int currentMoveColumn = move.getColumn();
        cells[currentMoveRow][currentMoveColumn] = move.getValue();
        int step = 1;
        while (step <= k) {
            if (isWin()) {
                return Result.WIN;
            }
            for (ImmutablePair direction : turns.keySet()) {
                int row = currentMoveRow + direction.getFirst() * step;
                int column = currentMoveColumn + direction.getSecond() * step;
                if (row >= 0 && row < m && column >= 0 && column < n && cells[row][column] == turn && turns.get(direction) == step - 1) {
                    turns.put(direction, turns.get(direction) + 1);
                }
            }
            step++;
        }

        amountOfTurns++;
        if (isDraw()) {
            return Result.DRAW;
        }

        if (isExtra()) {
            return Result.EXTRA;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    protected boolean isDraw() {
        return amountOfTurns == m * n;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private static int amountOfDigits(int r) {
        int count = 0;
        do {
            count++;
            r /= 10;
        } while(r != 0);
        return count;
    }

    @Override
    public String toString() {
        int maxAmountOfDigits = amountOfDigits(max(m, n) - 1);
        final StringBuilder sb = new StringBuilder(" ".repeat(maxAmountOfDigits + 1));
        for (int c = 0; c < n; c++) {
            sb.append(c).append(" ".repeat(maxAmountOfDigits - amountOfDigits(c) + 1));
        }
        for (int r = 0; r < m; r++) {
            sb.append("\n").append(r).append(" ".repeat(maxAmountOfDigits - amountOfDigits(r) + 1));
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c])).append(" ".repeat(maxAmountOfDigits));
            }
        }
        return sb.toString();
    }
}
