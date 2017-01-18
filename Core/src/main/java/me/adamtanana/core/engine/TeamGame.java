package me.adamtanana.core.engine;

import me.adamtanana.core.engine.team.Team;

public class TeamGame extends Game {

    public TeamGame(GameType gameType, Team[] team) {
        super(gameType);

        super.teams.clear();
        for(Team t : team) addTeam(t);
    }
}
