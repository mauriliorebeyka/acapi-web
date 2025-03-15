package com.rebeyka.acapi.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebeyka.acapi.builders.GameSetup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameLobby {

	private String gameId;
	private String gameName;
	@Setter(value = AccessLevel.NONE)
	private Date lastUpdated;
	@JsonIgnore
	private GameSetup gameSetup;
	private List<String> playersNames = new ArrayList<>();
	
	public void touch() {
		lastUpdated = new Date(System.currentTimeMillis());
	}
}
