package com.rebeyka.acapi.web.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.rebeyka.acapi.builders.GameSetup;
import com.rebeyka.acapi.web.dto.GameDescription;
import com.rebeyka.acapi.web.exceptions.GameSetupNotFoundException;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

@Service
public class GameLookupService {

	private static Logger LOG = LogManager.getLogger();

	private ApplicationContext context;
	
	private Map<String, Class<? extends GameSetup>> setups;

	public GameLookupService() {
		try (ScanResult scan = new ClassGraph().enableClassInfo().scan()) {
			setups = scan.getSubclasses(GameSetup.class).stream().collect(Collectors.toMap(k -> k.getSimpleName(), this::loadClass));
		}
	}

	private Class<? extends GameSetup> loadClass(ClassInfo c) {
		try {
			Class<?> loadedClass = Class.forName(c.getName());
			return loadedClass.asSubclass(GameSetup.class);
		} catch (ClassNotFoundException e) {
			LOG.fatal("Failed to load Game Setup", e);
			SpringApplication.exit(context, () -> 1);
		}
		return null;
	}

	public List<GameDescription> getAllGames() {
		return setups.entrySet().stream().map(this::toGameDescription).toList();
	}
	
	private GameDescription toGameDescription(Map.Entry<String, Class<? extends GameSetup>> entry) {
		String description = "no valid description";
		try {
			description = entry.getValue().getDeclaredConstructor().newInstance().getDescription();
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalArgumentException e) {
			LOG.warn("Failed to get description of {}",entry.getClass().getName());
		}
		return new GameDescription(entry.getKey(), description);
	}
	
	public GameSetup lookupByName(String name) throws GameSetupNotFoundException {
		if (!setups.containsKey(name) || setups.get(name) == null) {
			throw new GameSetupNotFoundException(name);
		}
		
		try {
			Class<? extends GameSetup> clazz = setups.get(name);
			return (GameSetup) clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new GameSetupNotFoundException(name, e);
		}
	}
}
