package game;

import static java.util.Objects.isNull;

public class RoundOfGame {
    private final boolean log;
    private final Player player1, player2;

    public RoundOfGame(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }
    int playRound(Board board) {
        while (true) {
            int result1 = move(board, player1, 1);
            if (result1 == -2) {
                result1 = extraTurn(board, player1, 1);
            }
            if (result1 != -1) {
                return result1;
            }
            int result2 = move(board, player2, 2);
            if (result2 == -2) {
                result2 = extraTurn(board, player2, 2);
            }
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int extraTurn(Board board, Player player, int no) {
        final int result = move(board, player, no);
        if (result != -2) {
            return result;
        }
        return extraTurn(board, player, no);
    }

    private int move(final Board board, final Player player, final int no) {
        Move move;
        try {
            move = player.move(board.getPosition(), board.getCell());
            if (isNull(move)) {
                return 3 - no;
            }
        } catch (RuntimeException e) {
            System.out.println("Invalid player...");
            return 3 - no;
        }
        final Result result = board.makeMove(move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else if (result == Result.EXTRA) {
            log("Extra");
            return -2;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
