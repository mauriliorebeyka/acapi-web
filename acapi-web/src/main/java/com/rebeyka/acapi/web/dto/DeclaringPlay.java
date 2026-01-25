package com.rebeyka.acapi.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclaringPlay {
	
	private String gameId;
	private String playerName;
	private String playId;
	private List<String> targets;
}
