package ru.mirea.tictactoe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "game", schema = "public")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
    @SequenceGenerator(name = "game_gen", schema = "public", sequenceName = "game_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "one_player_id")
    String onePlayerId;

    @Column(name = "two_player_id")
    String twoPlayerId;

    @Type(type="jsonb")
    @Column(name = "square", columnDefinition = "jsonb")
    Square square;

    @Column(name = "move")
    int move;
    @Column(name = "ended")
    boolean ended;
    @Column(name = "winner")
    Integer winner;
}
