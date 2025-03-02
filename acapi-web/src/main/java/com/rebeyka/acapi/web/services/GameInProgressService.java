package com.rebeyka.acapi.web.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.web.dto.EngagedPlayer;
import com.rebeyka.acapi.web.dto.GameInProgress;

@Service
public class GameInProgressService {

	public GameInProgress generateGameInProgress(String gameId, Game game) {
		GameInProgress gameInProgress = new GameInProgress();

		gameInProgress.setGameId(gameId);
		gameInProgress.setLog(game.getLog());
		gameInProgress.setRound(game.getGameFlow().getRound());
		gameInProgress.setFirstPlayer(game.getGameFlow().getFirstPlayer().getId());
		gameInProgress.setActivePlayers(game.getPlayers().stream().
				filter(p -> game.getGameFlow().isPlayerActive(p)).map(p -> p.getId()).toList());
		for (Player player : game.getPlayers()) {
			EngagedPlayer engagedPlayer = new EngagedPlayer();
			engagedPlayer.setPlayerId(player.getId());
			engagedPlayer.setPlayIds(player.getPlays().stream().map(Play::getId).toList());
			engagedPlayer.setAttributes(player.getAttributes().entrySet().stream()
					.collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue().get())));
			gameInProgress.getPlayers().add(engagedPlayer);
		}

		return gameInProgress;
	}
}
