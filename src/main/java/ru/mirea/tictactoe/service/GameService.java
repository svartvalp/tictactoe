package ru.mirea.tictactoe.service;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.mirea.tictactoe.entity.Game;
import ru.mirea.tictactoe.entity.Square;
import ru.mirea.tictactoe.exception.CustomException;
import ru.mirea.tictactoe.repository.GameRepository;

import java.util.Objects;

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
        if (anotherGame != null && !anotherGame.isEnded()) {
            return anotherGame;
        }
        if (anotherGame != null && anotherGame.isEnded()) {
            repository.delete(anotherGame);
        }
        Game game = new Game();
        game.setOnePlayerId(userId);
        game.setMove(1);
        game.setSquare(new Square(new String[3][3]));
        repository.save(game);
        return game;
    }

    public Game joinGame(long gameId, String userId) {
        var game = repository.getById(gameId);
        if (game.getTwoPlayerId() != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "game already started");
        }
        if (Objects.equals(game.getOnePlayerId(), userId)) {
            return game;
        }
        game.setTwoPlayerId(userId);
        repository.save(game);
        return game;
    }

    public Game makeMove(String userId, int x, int y) {
        var game = this.getUserGame(userId);
        var playerPosition = 1;
        if (userId.equals(game.getTwoPlayerId())) {
            playerPosition = 2;
        }
        if (game.getMove() % 2 != playerPosition % 2) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "not your turn");
        }
        if (game.getSquare().getMatrix()[x][y] != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "not empty position");
        }
        if (playerPosition == 1) {
            game.getSquare().getMatrix()[x][y] = "X";
        } else {
            game.getSquare().getMatrix()[x][y] = "0";
        }

        var winner = this.checkWin(game);
        if ("X".equals(winner)) {
            game.setWinner(1);
            game.setEnded(true);
        }
        if ("0".equals(winner)) {
            game.setWinner(2);
            game.setEnded(true);
        }

        var hasEmptySlot = false;
        for (int i = 0; i < game.getSquare().getMatrix().length; i++) {
            for (int j = 0; j < game.getSquare().getMatrix()[i].length; j++) {
                if (game.getSquare().getMatrix()[i][j] == null) {
                    hasEmptySlot = true;
                    break;
                }
            }
        }

        if (!hasEmptySlot) {
            game.setEnded(true);
        }

        game.setMove(game.getMove() + 1);
        repository.save(game);
        return game;
    }

    private String checkWin(Game game) {
        var matrix = game.getSquare().getMatrix();
        var matrixLen = matrix.length;
        for (String[] strings : matrix) {
            if (strings[0] != null && Objects.equals(strings[0], strings[1]) && Objects.equals(strings[0], strings[2])) {
                return strings[1];
            }
        }
        for (int i = 0; i < matrixLen; i++) {
            if (matrix[0][i] != null && Objects.equals(matrix[0][i], matrix[1][i]) && Objects.equals(matrix[0][i], matrix[2][i])) {
                return matrix[0][i];
            }
        }
        if (matrix[0][0] != null && matrix[0][0].equals(matrix[1][1]) && matrix[0][0].equals(matrix[2][2])) {
            return matrix[0][0];
        }
        if (matrix[0][2] != null && matrix[0][2].equals(matrix[1][1]) && matrix[0][2].equals(matrix[2][0])) {
            return matrix[0][2];
        }
        return null;
    }


}
