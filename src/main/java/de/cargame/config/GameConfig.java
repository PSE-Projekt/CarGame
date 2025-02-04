package de.cargame.config;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class GameConfig {


    private final Map<String, String> configMap = new HashMap<>();
    private final String GAME_CONFIG_FILE_NAME = "config";

    public GameConfig() {
        parseConfigFile(GAME_CONFIG_FILE_NAME);
    }

    public Optional<String> getValueKey(ConfigKey configKey){
        return Optional.of(configMap.get(configKey.toString()));

    }


    private void parseConfigFile(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("#")) { // Skip empty lines and comments
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        System.out.println("Loading value for key: " + parts[0].trim() + " with value: " + parts[1].trim());

                        configMap.put(parts[0].trim(), parts[1].trim());
                    } else {
                        log.warn("Skipping invalid config line: " + line);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to read configuration file: " + fileName, e);
        }
        System.out.println("Finished loading config " + LocalDateTime.now());
    }
}
