package de.cargame.config;

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

    AI_CAR_WIDTH,
    AI_CAR_HEIGHT,
    AI_CAR_SPEED,
    AI_CAR_SPAWN_TIME_MIN,
    AI_CAR_SPAWN_TIME_MAX,

    //----------------------AI CAR GENERAL-------------------
    //----------------------FAST CAR-------------------------

    FAST_CAR_WIDTH,
    FAST_CAR_HEIGHT,
    FAST_CAR_SPEED,
    FAST_CAR_INERTIA,

    //----------------------FAST CAR-------------------------
    //----------------------AGILE CAR------------------------

    AGILE_CAR_WIDTH,
    AGILE_CAR_HEIGHT,
    AGILE_CAR_SPEED,
    AGILE_CAR_INERTIA,

    //----------------------AGILE CAR------------------------
    //----------------------BUILDING------------------------
    BUILDING_WIDTH,
    BUILDING_HEIGHT,
    /**
     * Pixelcount how wide the spawn area for buildings is
     */
    BUILDING_SPAWN_AREA_WIDTH,
    BUILDING_SPAWN_TIME_MIN,
    BUILDING_SPAWN_TIME_MAX,

    //----------------------BUILDING------------------------
    //----------------------ROAD MARK-----------------------

    ROAD_MARK_WIDTH,
    ROAD_MARK_HEIGHT,
    ROAD_MARK_SPAWN_TIME_MIN,
    ROAD_MARK_SPAWN_TIME_MAX,

    //----------------------ROAD MARK-----------------------
    //----------------------OBSTACLE------------------------

    OBSTACLE_WIDTH,
    OBSTACLE_HEIGHT,
    OBSTACLE_SPAWN_TIME_MIN,
    OBSTACLE_SPAWN_TIME_MAX,


    //----------------------OBSTACLE-----------------------
    //----------------------REWARD-------------------------

    REWARD_WIDTH,
    REWARD_HEIGHT,
    REWARD_SPAWN_TIME_MIN,
    REWARD_SPAWN_TIME_MAX,

    //----------------------REWARD-------------------------

}