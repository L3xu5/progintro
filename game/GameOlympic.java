package game;

import java.util.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class GameOlympic {
    private final boolean log;
    private final Map<Player, Integer> players = new HashMap<>();

    public GameOlympic(final boolean log, List<Player> players) {
        this.log = log;
        for (int i = 0; i < players.size(); i++) {
            this.players.put(players.get(i), i);
        }
    }

    public String play(Board board) {
        StringBuilder resultOfTournament = new StringBuilder("\n");
        for (int i = players.size(); i > 1; i >>= 1) {
            Iterator<Player> iterator = players.keySet().iterator();
            List<Player> lostThisRound = new ArrayList<>();
            while (iterator.hasNext()) {
                int result = 0;
                Player player1 = iterator.next();
                if (!iterator.hasNext()) {
                    break;
                }
                Player player2 = iterator.next();
                while (result == 0) {
                    result = new RoundOfGame(log, player1, player2).playRound(board.getClone());
                }
                if (result == 1) {
                    lostThisRound.add(player2);
                } else {
                    lostThisRound.add(player1);
                }
            }
            for (Player player : lostThisRound) {
                resultOfTournament.append(players.get(player)).append(" ");
                players.remove(player);
            }
            resultOfTournament.append("\n");
        }
        return resultOfTournament.append(players.values().toArray()[0]).toString();
    }
}
