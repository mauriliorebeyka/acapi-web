package com.rebeyka.acapi.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EngagedPlayer {

	private String playerId;
	private List<String> playIds = new ArrayList<>();
	private Map<String, String> attributes = new HashMap<>();
	
}
