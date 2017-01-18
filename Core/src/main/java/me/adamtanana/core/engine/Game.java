package me.adamtanana.core.engine;

import lombok.Getter;
import me.adamtanana.core.engine.player.GamePlayer;
import me.adamtanana.core.engine.team.Team;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game implements IGame {

    public List<GamePlayer> gamePlayers;
    public List<Team> teams;

    private GameType gameType;

    public Game(GameType gameType) {
        this.gameType = gameType;

        teams = new ArrayList<>();
        gamePlayers = new ArrayList<>();

        addTeam(new Team());
        PacketPlayOutEntityHeadRotation h;
        PacketPlayOutEntity.PacketPlayOutEntityLook h2 = new PacketPlayOutEntity.PacketPlayOutEntityLook();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void addTeam(Team team) {
        teams.add(team);
    }
}
