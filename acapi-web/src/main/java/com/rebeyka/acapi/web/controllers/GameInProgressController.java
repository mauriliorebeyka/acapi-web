package com.rebeyka.acapi.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebeyka.acapi.web.assemblers.GameDTOAssembler;
import com.rebeyka.acapi.web.dto.DeclaringPlay;
import com.rebeyka.acapi.web.dto.GameDTO;
import com.rebeyka.acapi.web.services.GameInProgressService;

@RestController
@RequestMapping("/game/{gameId}")
public class GameInProgressController {

	@Autowired
	private GameInProgressService gameInProgressService;

	@Autowired
	private GameDTOAssembler gameDtoAssembler;

	@GetMapping
	public ResponseEntity<EntityModel<GameDTO>> gameStatus(@PathVariable(name = "gameId") String gameId) {
		return ResponseEntity.ok(gameDtoAssembler.toModel(gameInProgressService.getGame(gameId)));
	}

	@PostMapping("/start")
	public ResponseEntity<EntityModel<GameDTO>> startGame(@PathVariable(name = "gameId") String gameId) {
		return ResponseEntity.ok(gameDtoAssembler.toModel(gameInProgressService.startGame(gameId)));
	}

	@PostMapping("/declarePlay")
	public ResponseEntity<EntityModel<GameDTO>> declarePlay(@PathVariable(name = "gameId") String gameId,
			@RequestBody DeclaringPlay declaringPlay) {
		String playerName = declaringPlay.getPlayerName();
		String playId = declaringPlay.getPlayId();
		return ResponseEntity.accepted()
				.body(gameDtoAssembler.toModel(gameInProgressService.declarePlay(gameId, playerName, playId)));
	}

	@PostMapping("/execute")
	public ResponseEntity<EntityModel<GameDTO>> execute(@PathVariable(name = "gameId") String gameId) {
		return ResponseEntity.ok(gameDtoAssembler.toModel(gameInProgressService.execute(gameId)));
	}

	@PostMapping("/executeAll")
	public ResponseEntity<EntityModel<GameDTO>> executeAll(@PathVariable(name = "gameId") String gameId) {
		return ResponseEntity.ok(gameDtoAssembler.toModel(gameInProgressService.executeAll(gameId)));
	}

}
