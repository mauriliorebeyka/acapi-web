package com.rebeyka.acapi.web.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebeyka.acapi.builders.GameSetup;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.exceptions.WrongPlayerCountException;
import com.rebeyka.acapi.web.dto.GameDTO;
import com.rebeyka.acapi.web.dto.GameLobby;

@Service
public class GameInProgressService {

	@Autowired
	private GameLobbyService gameLobbyService;
	
	@Autowired
	private GameDTOBuilder gameDtoBuilder;
	
	private Map<String, Game> gamesInProgress = new HashMap<>();
	
	public GameDTO getGame(String gameId) {
		return gameDtoBuilder.fromGame(gamesInProgress.get(gameId));
	}
	
	public GameDTO startGame(String gameId) throws WrongPlayerCountException {
		GameLobby startingGame = gameLobbyService.getLobby(gameId);
		GameSetup gameSetup = gameLobbyService.getLobby(gameId).getGameSetup();
		startingGame.getPlayersNames().stream().forEach(gameSetup::addPlayer);
		gameSetup.setGameId(gameId);
		Game game = gameSetup.newGame();
		gamesInProgress.put(gameId, game);
		return gameDtoBuilder.fromGame(game);
	}
	
	public GameDTO declarePlay(String gameId, String playerId, String playId) {
		Game game = gamesInProgress.get(gameId);
		Player player = game.findPlayer(playerId);
		Play play = game.findPlay(player, playId);
		game.declarePlay(player, play);
		return gameDtoBuilder.fromGame(game);
	}
	
	public GameDTO execute(String gameId) {
		Game game = gamesInProgress.get(gameId);
		game.executeNext();
		return gameDtoBuilder.fromGame(game);
	}
	
	public GameDTO executeAll(String gameId) {
		Game game = gamesInProgress.get(gameId);
		game.executeAll();
		return gameDtoBuilder.fromGame(game);
	}
}
