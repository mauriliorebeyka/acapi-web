package com.rebeyka.acapi.web.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.web.dto.GameDTO;
import com.rebeyka.acapi.web.dto.PlayerDTO;

@Component
public class GameDTOBuilder {

	public GameDTO fromGame(Game game) {
		GameDTO gameDTO = new GameDTO();

		gameDTO.setGameId(game.getId());
		gameDTO.setLog(game.getLog());
		gameDTO.setRound(game.getGameFlow().getRound());
		gameDTO.setFirstPlayer(game.getGameFlow().getFirstPlayer().getId());
		gameDTO.setActivePlayers(game.getPlayers().stream().filter(p -> game.getGameFlow().isPlayerActive(p))
				.map(p -> p.getId()).toList());
		for (Player player : game.getPlayers()) {
			PlayerDTO playerDTO = new PlayerDTO();
			playerDTO.setPlayerId(player.getId());
			playerDTO.setPlayIds(player.getPlays().stream().map(Play::getName).toList());
			playerDTO.setAttributes(player.getAttributeNames().stream()
					.collect(Collectors.toMap(k -> k, v -> player.getAttribute(v).get())));
			gameDTO.getPlayers().add(playerDTO);
		}

		return gameDTO;
	}

}
