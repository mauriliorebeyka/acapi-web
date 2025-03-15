package com.rebeyka.acapi.web.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebeyka.acapi.web.dto.GameDescription;
import com.rebeyka.acapi.web.services.GameLookupService;

@RestController
public class RootController {

	@Autowired
	private GameLookupService gameLookupService;

	@GetMapping("/")
	public ResponseEntity<CollectionModel<GameDescription>> getGames() {
		return ResponseEntity.ok(CollectionModel.of(gameLookupService.getAllGames(),
				linkTo(methodOn(RootController.class).getGames()).withSelfRel(),
				linkTo(methodOn(GameLobbyController.class).createLobby(null)).withRel("createLobby")));
	}
}
