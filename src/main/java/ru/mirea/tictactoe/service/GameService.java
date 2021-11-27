package ru.mirea.tictactoe.service;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.mirea.tictactoe.entity.Game;
import ru.mirea.tictactoe.entity.Square;
import ru.mirea.tictactoe.exception.CustomException;
import ru.mirea.tictactoe.repository.GameRepository;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository repository;

    public Game getUserGame(String userId) {
        var game = repository.getByOnePlayerIdOrTwoPlayerId(userId, userId);
        if (game == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "not found user game");
        }
        return game;
    }

    public Game createGame(String userId) {
        var anotherGame = repository.getByOnePlayerIdOrTwoPlayerId(userId, userId);
        if (anotherGame != null) {
            return anotherGame;
        }
        Game game = new Game();
        game.setOnePlayerId(userId);
        game.setMove(1);
        game.setSquare(new Square(new String[3][3]));
        repository.save(game);
        return game;
    }

    public Game joinGame(String onePlayerId, String userId) {
        var game = this.getUserGame(onePlayerId);
        game.setTwoPlayerId(userId);
        repository.save(game);
        return game;
    }
}
