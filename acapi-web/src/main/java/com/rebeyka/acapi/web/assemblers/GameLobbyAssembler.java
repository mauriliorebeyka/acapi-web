package com.rebeyka.acapi.web.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.rebeyka.acapi.web.controllers.GameInProgressController;
import com.rebeyka.acapi.web.controllers.GameLobbyController;
import com.rebeyka.acapi.web.dto.GameLobby;

@Component
public class GameLobbyAssembler implements RepresentationModelAssembler<GameLobby, EntityModel<GameLobby>> {

	@Override
	public EntityModel<GameLobby> toModel(GameLobby gameLobby) {
		EntityModel<GameLobby> model = EntityModel.of(gameLobby, createLinks(gameLobby));
		return model;
	}

	private List<Link> createLinks(GameLobby gameLobby) {
		String gameId = gameLobby.getGameId();
		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(GameLobbyController.class).getGameLobby(gameId)).withSelfRel());
		links.add(linkTo(methodOn(GameLobbyController.class).list()).withRel("lobbies"));
		links.add(linkTo(methodOn(GameLobbyController.class).createLobby(null)).withRel("createNewLobby"));
		links.add(linkTo(methodOn(GameInProgressController.class).startGame(gameId)).withRel("start"));
		links.add(linkTo(methodOn(GameLobbyController.class).registerPlayer(gameId, null)).withRel("registerPlayer"));
		for (String playerId : gameLobby.getPlayersNames()) {
			links.add(linkTo(methodOn(GameLobbyController.class).removePlayer(gameId, playerId)).withRel("removePlayer"));
		}
		return links;
	}
	
}
