package com.rebeyka.acapi.web.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.rebeyka.acapi.web.dto.GameLobby;
import com.rebeyka.acapi.web.exceptions.GameSetupNotFoundException;

@Service
public class GameLobbyService {

	@Autowired
	private GameLookupService gameLookupService;

	private Map<String, GameLobby> openLobbies = new HashMap<>();

	public GameLobby addLobby(String gameName) {
		GameLobby newLobby = new GameLobby();
		UUID id = UUID.randomUUID();
		newLobby.setGameName(gameName);
		newLobby.setGameId(id.toString());
		newLobby.setGameSetup(gameLookupService.lookupByName(gameName));
		newLobby.touch();
		openLobbies.put(id.toString(), newLobby);
		return newLobby;
	}

	public GameLobby getLobby(String lobbyId) {
		GameLobby lobby = openLobbies.get(lobbyId);
		
		if (lobby == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Can't find game with %s".formatted(lobbyId));
		}
		lobby.touch();
		return lobby;
	}
	
	public GameLobby joinPlayer(String lobbyId, String playerName) {
		GameLobby lobby = getLobby(lobbyId);
		
		if (lobby.getPlayersNames().contains(playerName)) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "Player already there");
		}
		
		lobby.getPlayersNames().add(playerName);
		
		return lobby;
	}
	
	public GameLobby removePlayer(String lobbyId, String playerName) {
		GameLobby lobby = getLobby(lobbyId);
		
		if (!lobby.getPlayersNames().contains(playerName)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Player doesn't exist");
		}
		
		lobby.getPlayersNames().remove(playerName);
		
		return lobby;
	}
	
	public List<GameLobby> listLobbies() {
		return openLobbies.values().stream().toList();
	}

}
