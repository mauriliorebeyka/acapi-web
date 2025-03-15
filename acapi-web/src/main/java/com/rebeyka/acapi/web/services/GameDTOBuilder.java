package com.rebeyka.acapi.web.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.web.dto.PlayerDTO;
import com.rebeyka.acapi.web.dto.GameDTO;

@Component
public class GameDTOBuilder {

	public GameDTO fromGame(Game game) {
		GameDTO gameDTO = new GameDTO();

		gameDTO.setGameId(game.getId());
		gameDTO.setLog(game.getLog());
		gameDTO.setRound(game.getGameFlow().getRound());
		gameDTO.setFirstPlayer(game.getGameFlow().getFirstPlayer().getId());
		gameDTO.setActivePlayers(game.getPlayers().stream().
				filter(p -> game.getGameFlow().isPlayerActive(p)).map(p -> p.getId()).toList());
		for (Player player : game.getPlayers()) {
			PlayerDTO playerDTO = new PlayerDTO();
			playerDTO.setPlayerId(player.getId());
			playerDTO.setPlayIds(player.getPlays().stream().map(Play::getId).toList());
			playerDTO.setAttributes(player.getAttributes().entrySet().stream()
					.collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue().get())));
			gameDTO.getPlayers().add(playerDTO);
		}

		return gameDTO;
	}
}
