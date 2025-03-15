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
import com.rebeyka.acapi.web.dto.GameDTO;

@Component
public class GameDTOAssembler implements RepresentationModelAssembler<GameDTO, EntityModel<GameDTO>>{

	@Override
	public EntityModel<GameDTO> toModel(GameDTO entity) {
		return EntityModel.of(entity,getLinks(entity));
	}

	private List<Link> getLinks(GameDTO entity) {
		List<Link> links = new ArrayList<>();
		
		links.add(linkTo(methodOn(GameInProgressController.class).gameStatus(entity.getGameId())).withSelfRel());
		
		return links;
	}
}
