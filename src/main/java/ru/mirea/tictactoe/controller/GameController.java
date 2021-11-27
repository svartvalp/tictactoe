package ru.mirea.tictactoe.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.tictactoe.entity.Game;
import ru.mirea.tictactoe.service.GameService;

@AllArgsConstructor
@RestController
public class GameController {
    private final GameService service;

    @GetMapping("/game")
    public Game getUserGame(@RequestParam(name = "user_id") String userId) {
        return service.getUserGame(userId);
    }

    @PostMapping("/game")
    public Game createUserGame(@RequestParam(name = "user_id") String userId) {
        return service.createGame(userId);
    }

    @PostMapping("/game/join")
    public Game joinGame(@RequestParam(name = "one_player_id") String onePlayerId, @RequestParam(name = "user_id") String userId) {
        return service.joinGame(onePlayerId, userId);
    }


}
