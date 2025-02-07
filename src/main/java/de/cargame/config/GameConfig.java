package de.cargame.config;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * The GameConfig class is responsible for loading and managing configuration
 * data required for the game. It reads configuration files and provides an
 * interface to retrieve key-value pairs representing various settings.
 * <p>
 * The configuration data is stored in a map, allowing quick lookups of specific
 * configuration keys. The keys are typically represented as enums of type
 * {@code ConfigKey}.
 * <p>
 * The implementation is not necessarily thread-safe due to the potential concurrent
 * access to the underlying configuration map. Ensure external synchronization if the
 * object is accessed in multi-threaded environments.
 */
@Slf4j
public class GameConfig {


    private final Map<String, String> configMap = new HashMap<>();
    private final String GAME_CONFIG_FILE_NAME = "config";

    public GameConfig() {
        parseConfigFile(GAME_CONFIG_FILE_NAME);
    }

    /**
     * Retrieves the value associated with the specified configuration key.
     * This method looks up the configuration map for the provided key's string
     * representation and returns the corresponding value wrapped in an {@code Optional}.
     *
     * @param configKey the configuration key to retrieve the value for
     * @return an {@code Optional} containing the value associated with the given key,
     * or an empty {@code Optional} if the key is not found
     */
    public Optional<String> getValueKey(ConfigKey configKey) {
        return Optional.of(configMap.get(configKey.toString()));
    }


    private void parseConfigFile(String fileName) {
        log.debug(" Start loading config");
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("#")) { // Skip empty lines and comments
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        log.debug("Loading value for key: {} with value: {}", parts[0].trim(), parts[1].trim());

                        configMap.put(parts[0].trim(), parts[1].trim());
                    } else {
                        log.trace("Skipping invalid config line: {}", line);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to read configuration file: {}", fileName, e);
        }
        log.info("Finished loading config {}", LocalDateTime.now());
    }
}
