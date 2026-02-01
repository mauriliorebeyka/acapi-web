package com.rebeyka.acapi.web.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.AbstractCheck;
import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.Trigger;
import com.rebeyka.acapi.web.controllers.GameInProgressController;
import com.rebeyka.acapi.web.dto.GameDTO;

@Component
public class GameDTOAssembler implements RepresentationModelAssembler<GameDTO, EntityModel<GameDTO>> {

	@Override
	public EntityModel<GameDTO> toModel(GameDTO entity) {
		return EntityModel.of(entity, getLinks(entity));
	}

	private List<Link> getLinks(GameDTO entity) {
		List<Link> links = new ArrayList<>();

		String gameId = entity.getGameView().getAttributeView().stream().filter(a -> "ID".equals(a.getAttributeName())).findFirst().get().getAttributeValue().toString();
		links.add(linkTo(methodOn(GameInProgressController.class).gameStatus(gameId)).withSelfRel());
		links.add(linkTo(methodOn(GameInProgressController.class).declarePlay(gameId, null))
				.withRel("declarePlay"));

		links.add(linkTo(methodOn(GameInProgressController.class).execute(gameId)).withRel("execute"));
		links.add(
				linkTo(methodOn(GameInProgressController.class).executeAll(gameId)).withRel("executeAll"));

		return links;
	}
	
	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder().mixIn(Playable.class, PlayableMixin.class).mixIn(Play.class, PlayMixin.class);
	}
	
	public static class PlayableMixin {
		@JsonIgnore
		List<Play> plays;
		@JsonIgnore
		Game game;
		@JsonIgnore
		Player owner;
	}
	
	public static class PlayMixin {
		@JsonIgnore
		private Game game;
		@JsonIgnore
		private Cost cost;
		@JsonIgnore
		private AbstractCheck<?,Playable,Playable> condition;
		@JsonIgnore
		private List<Supplier<Actionable>> actionables;
		@JsonIgnore
		private Trigger triggeredBy;
		@JsonIgnore
		private List<Supplier<Actionable>> actionableSuppliers;
	}
}
