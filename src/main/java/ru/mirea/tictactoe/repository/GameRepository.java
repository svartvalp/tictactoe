package ru.mirea.tictactoe.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.tictactoe.entity.Game;


public interface GameRepository extends JpaRepository<Game, Long> {
    Game getByOnePlayerIdOrTwoPlayerId(String onePlayerId, String twoPlayerId);
}
