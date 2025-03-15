package com.rebeyka.acapi.web.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.rebeyka.acapi.web.controllers.GameLobbyController;
import com.rebeyka.acapi.web.dto.GameDescription;

public class GameDescriptionAssembler
		implements RepresentationModelAssembler<GameDescription, EntityModel<GameDescription>> {

	@Override
	public EntityModel<GameDescription> toModel(GameDescription entity) {
		return EntityModel.of(entity, linkTo(methodOn(GameLobbyController.class).createLobby(null)).withRel("createLobby"));
	}

}
