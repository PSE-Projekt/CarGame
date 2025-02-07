package de.cargame.config;

/**
 * Defines configuration keys used across the application to represent various
 * configuration properties. These keys can be utilized to fetch appropriate
 * configurations for UI settings, gameplay parameters, AI vehicle attributes,
 * and other game elements from configuration files.
 * <p>
 * Each key in this enum corresponds to a specific configuration parameter that can
 * be loaded or accessed using classes such as {@code GameConfig} and
 * {@code GameConfigService}.
 * <p>
 * Usage in conjunction with {@code GameConfig}:
 * Configurations associated with these keys are fetched from a predefined configuration file.
 * <p>
 * Groupings (indicated by comments):
 * - UI: Settings related to screen dimensions and visual aspects.
 * - GENERAL: Main gameplay settings like game speed and scoring mechanics.
 * - AI CAR: Specifications for AI vehicles' dimensions, speed, and spawn timing.
 * - FAST CAR: Attributes specific to the "fast car" type, including size, speed, and inertia.
 * - AGILE CAR: Attributes specific to the "agile car" type, including size, speed, and inertia.
 * - BUILDING: Configuration related to building elements, including dimensions and spawn area.
 * - ROAD MARK: Settings for road markings, like their dimensions and timing.
 * - OBSTACLE: Configuration for obstacles, covering dimensions and spawn intervals.
 * - REWARD: Settings for reward items, such as dimensions and spawn intervals.
 * <p>
 * Each key can have an associated value in the configuration file that is dynamically
 * loaded at runtime. The values can define dimensions, speeds, spawn timing, and other
 * game mechanics crucial for functioning and balancing the game.
 */
public enum ConfigKey {


    //----------------------UI------------------------------

    SCREEN_WIDTH,
    SCREEN_HEIGHT,

    //----------------------UI------------------------------
    //----------------------GENERAL-------------------------

    MAX_LIVES,
    GAME_SPEED,
    GAME_SPEED_FAST_FORWARD,
    CRASH_COOLDOWN_TIME,
    SCORE_INCREASE_NORMAL_SPEED,
    SCORE_INCREASE_FAST_FORWARD_SPEED,

    /**
     * Value:
     * 8 -> ~120 FPS
     * 16 -> ~60 FPS
     * 32 -> ~30 FPS
     */
    FPS,

    //----------------------GENERAL--------------------------
    //----------------------AI CAR GENERAL-------------------

    AI_CAR_WIDTH_SINGLEPLAYER,
    AI_CAR_HEIGHT_SINGLEPLAYER,
    AI_CAR_WIDTH_MULTIPLAYER,
    AI_CAR_HEIGHT_MULTIPLAYER,
    AI_CAR_SPEED,
    AI_CAR_SPAWN_TIME_MIN,
    AI_CAR_SPAWN_TIME_MAX,

    //----------------------AI CAR GENERAL-------------------
    //----------------------FAST CAR-------------------------

    FAST_CAR_WIDTH_SINGLEPLAYER,
    FAST_CAR_HEIGHT_SINGLEPLAYER,
    FAST_CAR_WIDTH_MULTIPLAYER,
    FAST_CAR_HEIGHT_MULTIPLAYER,
    FAST_CAR_SPEED,
    FAST_CAR_INERTIA,

    //----------------------FAST CAR-------------------------
    //----------------------AGILE CAR------------------------

    AGILE_CAR_WIDTH_SINGLEPLAYER,
    AGILE_CAR_HEIGHT_SINGLEPLAYER,
    AGILE_CAR_WIDTH_MULTIPLAYER,
    AGILE_CAR_HEIGHT_MULTIPLAYER,
    AGILE_CAR_SPEED,
    AGILE_CAR_INERTIA,

    //----------------------AGILE CAR------------------------
    //----------------------BUILDING------------------------
    BUILDING_WIDTH_SINGLEPLAYER,
    BUILDING_HEIGHT_SINGLEPLAYER,
    BUILDING_WIDTH_MULTIPLAYER,
    BUILDING_HEIGHT_MULTIPLAYER,
    /**
     * Pixelcount how wide the spawn area for buildings is
     */
    BUILDING_SPAWN_AREA_WIDTH_SINGLEPLAYER,
    BUILDING_SPAWN_AREA_WIDTH_MULTIPLAYER,
    BUILDING_SPAWN_TIME_MIN,
    BUILDING_SPAWN_TIME_MAX,

    //----------------------BUILDING------------------------
    //----------------------ROAD MARK-----------------------

    ROAD_MARK_WIDTH_SINGLEPLAYER,
    ROAD_MARK_HEIGHT_SINGLEPLAYER,
    ROAD_MARK_WIDTH_MULTIPLAYER,
    ROAD_MARK_HEIGHT_MULTIPLAYER,
    ROAD_MARK_SPAWN_TIME_MIN,
    ROAD_MARK_SPAWN_TIME_MAX,

    //----------------------ROAD MARK-----------------------
    //----------------------OBSTACLE------------------------

    OBSTACLE_WIDTH_SINGLEPLAYER,
    OBSTACLE_HEIGHT_SINGLEPLAYER,
    OBSTACLE_WIDTH_MULTIPLAYER,
    OBSTACLE_HEIGHT_MULTIPLAYER,
    OBSTACLE_SPAWN_TIME_MIN,
    OBSTACLE_SPAWN_TIME_MAX,


    //----------------------OBSTACLE-----------------------
    //----------------------REWARD-------------------------

    REWARD_WIDTH_SINGLEPLAYER,
    REWARD_HEIGHT_SINGLEPLAYER,
    REWARD_WIDTH_MULTIPLAYER,
    REWARD_HEIGHT_MULTIPLAYER,
    REWARD_SPAWN_TIME_MIN,
    REWARD_SPAWN_TIME_MAX,

    //----------------------REWARD-------------------------

}