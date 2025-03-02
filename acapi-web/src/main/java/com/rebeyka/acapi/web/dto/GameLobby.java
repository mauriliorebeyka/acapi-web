package com.rebeyka.acapi.web.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameLobby {

	private String gameId;
	private String gameName;
	private List<String> playersNames = new ArrayList<>();
	
}
