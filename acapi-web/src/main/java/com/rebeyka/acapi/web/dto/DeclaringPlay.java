package com.rebeyka.acapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclaringPlay {
	
	private String gameId;
	private String playerName;
	private String playId;
}
