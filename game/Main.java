package game;
import java.util.Arrays;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final GameOlympic game = new GameOlympic(false, Arrays.asList(new HumanPlayer(), new RandomPlayer(), new RandomPlayer(), new RandomPlayer(), new RandomPlayer(),new RandomPlayer(), new RandomPlayer(),new RandomPlayer(), new RandomPlayer(),new RandomPlayer(), new RandomPlayer(),new RandomPlayer(), new RandomPlayer(),new RandomPlayer(), new RandomPlayer(),new RandomPlayer()));
        String result;
        result = game.play(new TicTacToeBoardCircle());
        System.out.println("Game result: " + result);
    }
}
