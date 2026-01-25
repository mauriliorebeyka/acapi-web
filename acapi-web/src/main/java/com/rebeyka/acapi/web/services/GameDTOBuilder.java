package com.rebeyka.acapi.web.services;

import org.springframework.stereotype.Component;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.view.GameView;
import com.rebeyka.acapi.view.GameViewBuilder;
import com.rebeyka.acapi.web.dto.GameDTO;

@Component
public class GameDTOBuilder {

	public GameDTO fromGame(Game game) {
		Player player = game.getPlayers().get(0);
		GameView gameView = new GameViewBuilder(game).buildView(player);
		GameDTO gameDTO = new GameDTO();
		gameDTO.setGameView(gameView);
		return gameDTO;
	}

}
