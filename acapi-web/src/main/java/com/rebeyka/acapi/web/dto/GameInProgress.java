package com.rebeyka.acapi.web.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInProgress {

	private String gameId;
	private int round;
	private String firstPlayer;
	private List<String> activePlayers = new ArrayList<>();
	private List<EngagedPlayer> players = new ArrayList<>();
	private List<String> log = new ArrayList<>();
}
