package game;

import static java.util.Objects.isNull;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final RoundOfGame roundOfGame;
    public Game(final boolean log, final Player player1, final Player player2) {
        roundOfGame = new RoundOfGame(log, player1, player2);
    }
    public String play(Board board) {
        return Integer.toString(roundOfGame.playRound(board));
    }
}
