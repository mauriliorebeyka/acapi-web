package com.rebeyka.acapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoiningPlayer {

	private String playerName;
	private String gameId;
}
