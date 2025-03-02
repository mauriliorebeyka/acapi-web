package com.rebeyka.acapi.web.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rebeyka.acapi.actionables.WinningConditionByAttributeRank;
import com.rebeyka.acapi.builders.GameSetup;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.web.dto.DeclaringPlay;
import com.rebeyka.acapi.web.dto.GameInProgress;
import com.rebeyka.acapi.web.dto.GameLobby;
import com.rebeyka.acapi.web.dto.JoiningPlayer;
import com.rebeyka.acapi.web.services.GameInProgressService;

import rebeyka.acapi_sample_dicewar.DiceWar;

@RestController
public class GameController {

	@Autowired
	private GameInProgressService gameInProgressService;
	
	private Map<String,GameLobby> openLobbies;
	
	private Map<String,Game> gamesInProgress;
	
	public GameController() {
		openLobbies = new HashMap<>();
		gamesInProgress = new HashMap<>();
	}
	
	@PostMapping(value = "/createGame", consumes = "application/json") 
	public GameLobby createGame(@RequestBody GameLobby gameType) {
		String gameId = gameType.getGameId();
		if (!openLobbies.containsKey(gameType.getGameId())) {
			gameId = UUID.randomUUID().toString();
			gameType.setGameId(gameId);
			gameType.setGameName("DiceWar");
			openLobbies.put(gameId.toString(), gameType);
		}
		return openLobbies.get(gameId);
		
	}
	
	@PostMapping("/registerPlayer")
	public GameLobby registerPlayer(@RequestBody JoiningPlayer joiningPlayer) {
		GameLobby gameLobby = openLobbies.get(joiningPlayer.getGameId());
		if (gameLobby != null) {
			gameLobby.getPlayersNames().add(joiningPlayer.getPlayerName());
		}
		return gameLobby;
	}
	
	@PostMapping("/startGame")
	public GameInProgress startGame(@RequestBody GameLobby gameLobby) {
		GameLobby startingGame = openLobbies.get(gameLobby.getGameId());
		if (startingGame != null && !startingGame.getPlayersNames().isEmpty()) {
			GameSetup gameSetup = new DiceWar();
			startingGame.getPlayersNames().stream().forEach(gameSetup::addPlayer);
			String gameId = gameLobby.getGameId();
			Game game = gameSetup.newGame();
			gamesInProgress.put(gameId, game);
			return gameInProgressService.generateGameInProgress(gameId, game);
		}
		return null;
	}
	
	@PostMapping("/declarePlay")
	public GameInProgress declarePlay(@RequestBody DeclaringPlay declaringPlay ) {
		String gameId = declaringPlay.getGameId();
		String playId = declaringPlay.getPlayId();
		Game game = gamesInProgress.get(gameId);
		Player player = game.findPlayer(declaringPlay.getPlayerName());
		Play play = game.findPlay(player, playId);
		game.declarePlay(player, play);
		return gameInProgressService.generateGameInProgress(gameId, game);
	}
	
	@PostMapping("/execute")
	public GameInProgress execute(@RequestBody GameLobby gameLobby) {
		String gameId = gameLobby.getGameId();
		Game game = gamesInProgress.get(gameId);
		game.executeNext();
		return gameInProgressService.generateGameInProgress(gameId, game);
	}
}
