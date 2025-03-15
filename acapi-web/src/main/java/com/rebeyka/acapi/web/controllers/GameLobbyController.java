package com.rebeyka.acapi.web.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebeyka.acapi.web.assemblers.GameLobbyAssembler;
import com.rebeyka.acapi.web.dto.GameLobby;
import com.rebeyka.acapi.web.dto.JoiningPlayer;
import com.rebeyka.acapi.web.services.GameLobbyService;

@RestController
@RequestMapping("/lobbies")
public class GameLobbyController {

	@Autowired
	private GameLobbyService gameLobbyService;

	@Autowired
	private GameLobbyAssembler gameLobbyAssembler;

	@PostMapping
	public ResponseEntity<EntityModel<GameLobby>> createLobby(@RequestBody GameLobby gameName) {
		GameLobby gameLobby = gameLobbyService.addLobby(gameName.getGameName());
		return ResponseEntity.status(HttpStatus.CREATED).body(gameLobbyAssembler.toModel(gameLobby));
	}

	@PostMapping("/{gameId}/registerPlayer")
	public ResponseEntity<EntityModel<GameLobby>> registerPlayer(@PathVariable(name = "gameId") String gameId,
			@RequestBody JoiningPlayer joiningPlayer) {
		GameLobby gameLobby = gameLobbyService.joinPlayer(gameId, joiningPlayer.getPlayerName());
		return ResponseEntity.status(HttpStatus.CREATED).body(gameLobbyAssembler.toModel(gameLobby));
	}

	@DeleteMapping("/{gameId}/{playerId}")
	public ResponseEntity<EntityModel<GameLobby>> removePlayer(@PathVariable(name = "gameId") String gameId,
			@PathVariable(name = "playerId") String playerId) {
		GameLobby gameLobby = gameLobbyService.removePlayer(gameId, playerId);
		return ResponseEntity.status(HttpStatus.GONE).body(gameLobbyAssembler.toModel(gameLobby));
	}

	@GetMapping("/{gameId}")
	public ResponseEntity<EntityModel<GameLobby>> getGameLobby(@PathVariable(name = "gameId") String gameId) {
		GameLobby gameLobby = gameLobbyService.getLobby(gameId);
		return ResponseEntity.ok(gameLobbyAssembler.toModel(gameLobby));
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<GameLobby>>> list() {
		List<GameLobby> lobbies = gameLobbyService.listLobbies();
		CollectionModel<EntityModel<GameLobby>> lobbiesModel = CollectionModel
				.of(lobbies.stream().map(gameLobbyAssembler::toModel).toList());
		lobbiesModel.add(linkTo(methodOn(GameLobbyController.class).list()).withSelfRel());
		return ResponseEntity.ok(lobbiesModel);
	}

}
